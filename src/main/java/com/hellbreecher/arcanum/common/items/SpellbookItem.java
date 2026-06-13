package com.hellbreecher.arcanum.common.items;

import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.common.handler.magic.SpellFlightManager;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.Filterable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SpellbookItem extends WrittenBookItem {
    private static final String SPELL_KEY = "ArcanumSpell";
    private static final int SPELL_COUNT = 5;
    private static final int FIREBOLT_COST = 20;
    private static final int EMBER_WARD_COST = 35;
    private static final int RENEW_COST = 30;
    private static final int LUMENFALL_COST = 2;
    private static final int AETHERWING_COST = 250;
    private static final double LUMENFALL_RANGE = 64.0D;
    private static final double FIREBOLT_RANGE = 32.0D;

    public SpellbookItem(Identifier id) {
        super(new Properties()
                .stacksTo(1)
                .component(DataComponents.WRITTEN_BOOK_CONTENT, createBookContent())
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ensureBookContent(stack);

        if (player.isShiftKeyDown()) {
            player.openItemGui(stack, hand);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        int spell = getSelectedSpell(stack);
        int cost = spellCost(spell);
        if (!ManaManager.spend(player, cost)) {
            player.sendOverlayMessage(Component.literal("Not enough mana (" + cost + " needed)"));
            return InteractionResult.CONSUME;
        }

        castSpell(level, player, spell);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    private static void ensureBookContent(ItemStack stack) {
        if (!stack.has(DataComponents.WRITTEN_BOOK_CONTENT)) {
            stack.set(DataComponents.WRITTEN_BOOK_CONTENT, createBookContent());
        }
    }

    public static int getSelectedSpell(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int spell = data.copyTag().getIntOr(SPELL_KEY, 0);
        return normalizeSpell(spell);
    }

    public static int normalizeSpell(int spell) {
        return Math.floorMod(spell, SPELL_COUNT);
    }

    public static void setSelectedSpell(ItemStack stack, int spell) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(SPELL_KEY, normalizeSpell(spell)));
    }

    public static int nextSpell(int spell) {
        return normalizeSpell(spell + 1);
    }

    private static void castSpell(Level level, Player player, int spell) {
        switch (spell) {
            case 0 -> castLumenfall(level, player);
            case 1 -> castFirebolt(level, player);
            case 2 -> castRenew(player);
            case 3 -> castEmberWard(level, player);
            case 4 -> castAetherwing(level, player);
            default -> {
            }
        }
        player.sendOverlayMessage(Component.literal("Cast: " + getSpellName(spell)));
    }

    private static int spellCost(int spell) {
        return switch (spell) {
            case 0 -> LUMENFALL_COST;
            case 1 -> FIREBOLT_COST;
            case 2 -> RENEW_COST;
            case 3 -> EMBER_WARD_COST;
            case 4 -> AETHERWING_COST;
            default -> 0;
        };
    }

    private static void castFirebolt(Level level, Player player) {
        if (tryFireboltTransmutation(level, player)) {
            return;
        }

        Vec3 look = player.getLookAngle().normalize();
        SmallFireball fireball = new SmallFireball(level, player, look);
        fireball.setPos(
                player.getX() + look.x * 1.5D,
                player.getEyeY() - 0.1D + look.y * 1.5D,
                player.getZ() + look.z * 1.5D
        );
        level.addFreshEntity(fireball);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
    }

    private static boolean tryFireboltTransmutation(Level level, Player player) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(FIREBOLT_RANGE));
        BlockHitResult blockHit = level.clip(new ClipContext(
                start,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        double maxDistance = blockHit.getType() == HitResult.Type.BLOCK
                ? start.distanceTo(blockHit.getLocation())
                : FIREBOLT_RANGE;
        Optional<ItemEntity> targetItem = findFireboltItemTarget(level, start, look, maxDistance);
        if (targetItem.isPresent()) {
            transmuteVoidDiamondItem(serverLevel, targetItem.get());
            return true;
        }

        if (blockHit.getType() == HitResult.Type.BLOCK && transmuteVoidBlock(serverLevel, blockHit.getBlockPos())) {
            return true;
        }

        return false;
    }

    private static Optional<ItemEntity> findFireboltItemTarget(Level level, Vec3 start, Vec3 look, double maxDistance) {
        AABB searchArea = new AABB(start, start.add(look.scale(maxDistance))).inflate(1.0D);
        return level.getEntitiesOfClass(ItemEntity.class, searchArea, entity -> getTransmutedItem(entity.getItem()) != null)
                .stream()
                .filter(entity -> distanceAlongRay(start, look, entity.position()) >= 0.0D)
                .filter(entity -> distanceAlongRay(start, look, entity.position()) <= maxDistance)
                .filter(entity -> distanceFromRaySqr(start, look, entity.position()) <= 1.0D)
                .min(Comparator.comparingDouble(entity -> distanceAlongRay(start, look, entity.position())));
    }

    private static void transmuteVoidDiamondItem(ServerLevel level, ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        Item result = getTransmutedItem(stack);
        if (result == null) {
            return;
        }
        itemEntity.setItem(new ItemStack(result, getTransmutedItemCount(stack)));
        itemEntity.clearFire();
        playFireboltTransmutation(level, itemEntity.position());
    }

    private static Item getTransmutedItem(ItemStack stack) {
        if (stack.is(ArcanumItems.voiddiamond.get()) || stack.is(ArcanumBlocks.voiddiamond_block_item.get())) {
            return ArcanumItems.infernaldiamond.get();
        }
        if (stack.is(ArcanumBlocks.voiddiamondfurnace_block_item.get())) {
            return ArcanumBlocks.infernalfurnace_block_item.get();
        }
        if (stack.is(ArcanumBlocks.voiddiamondgenerator_block_item.get())) {
            return ArcanumBlocks.infernalgenerator_block_item.get();
        }
        return null;
    }

    private static int getTransmutedItemCount(ItemStack stack) {
        if (stack.is(ArcanumBlocks.voiddiamond_block_item.get())) {
            return stack.getCount() * 9;
        }
        return stack.getCount();
    }

    private static boolean transmuteVoidBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(ArcanumBlocks.voiddiamond_block.get())) {
            level.removeBlock(pos, false);
            Block.popResource(level, pos, new ItemStack(ArcanumItems.infernaldiamond.get(), 9));
            playFireboltTransmutation(level, Vec3.atCenterOf(pos));
            return true;
        }
        if (state.is(ArcanumBlocks.voiddiamondfurnace_block.get())) {
            level.setBlock(pos, copySharedState(state, ArcanumBlocks.infernalfurnace_block.get().defaultBlockState()), 3);
            playFireboltTransmutation(level, Vec3.atCenterOf(pos));
            return true;
        }
        if (state.is(ArcanumBlocks.voiddiamondgenerator_block.get())) {
            level.setBlock(pos, copySharedState(state, ArcanumBlocks.infernalgenerator_block.get().defaultBlockState()), 3);
            playFireboltTransmutation(level, Vec3.atCenterOf(pos));
            return true;
        }
        return false;
    }

    private static BlockState copySharedState(BlockState from, BlockState to) {
        BlockState result = to;
        if (from.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && result.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            result = result.setValue(BlockStateProperties.HORIZONTAL_FACING, from.getValue(BlockStateProperties.HORIZONTAL_FACING));
        }
        if (from.hasProperty(BlockStateProperties.LIT) && result.hasProperty(BlockStateProperties.LIT)) {
            result = result.setValue(BlockStateProperties.LIT, from.getValue(BlockStateProperties.LIT));
        }
        return result;
    }

    private static double distanceAlongRay(Vec3 start, Vec3 look, Vec3 point) {
        return point.subtract(start).dot(look);
    }

    private static double distanceFromRaySqr(Vec3 start, Vec3 look, Vec3 point) {
        double distance = distanceAlongRay(start, look, point);
        Vec3 closest = start.add(look.scale(distance));
        return closest.distanceToSqr(point);
    }

    private static void playFireboltTransmutation(ServerLevel level, Vec3 pos) {
        level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y + 0.25D, pos.z, 16, 0.35D, 0.35D, 0.35D, 0.02D);
        level.sendParticles(ParticleTypes.LAVA, pos.x, pos.y + 0.25D, pos.z, 6, 0.25D, 0.25D, 0.25D, 0.0D);
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 0.7F);
    }

    private static void castEmberWard(Level level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.8F, 0.7F);
    }

    private static void castAetherwing(Level level, Player player) {
        SpellFlightManager.activate(player);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 0.6F, 1.4F);
    }

    private static void castRenew(Player player) {
        player.heal(6.0F);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 1.2F);
    }

    private static void castLumenfall(Level level, Player player) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(LUMENFALL_RANGE));
        BlockHitResult hit = level.clip(new ClipContext(
                start,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 impact = hit.getType() == HitResult.Type.BLOCK ? hit.getLocation() : end;
        sendLumenfallTrail(serverLevel, start, impact);

        BlockPos pos = hit.getType() == HitResult.Type.BLOCK
                ? hit.getBlockPos().relative(hit.getDirection())
                : BlockPos.containing(end);
        placeMagicLight(level, pos);

        level.playSound(null, impact.x, impact.y, impact.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.8F);
    }

    private static void sendLumenfallTrail(ServerLevel level, Vec3 start, Vec3 impact) {
        Vec3 path = impact.subtract(start);
        double length = path.length();
        if (length <= 0.0D) {
            return;
        }

        Vec3 step = path.normalize().scale(0.75D);
        int steps = Math.max(1, (int) (length / 0.75D));
        for (int i = 0; i <= steps; i++) {
            Vec3 point = start.add(step.scale(i));
            level.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1, 0.03D, 0.03D, 0.03D, 0.0D);
        }
    }

    private static void placeMagicLight(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.isAir() && !state.canBeReplaced() && !state.getFluidState().is(Fluids.WATER)) {
            return;
        }

        BlockState light = Blocks.LIGHT.defaultBlockState()
                .setValue(LightBlock.LEVEL, 15)
                .setValue(LightBlock.WATERLOGGED, state.getFluidState().is(Fluids.WATER));
        level.setBlock(pos, light, 3);
    }

    public static String getSpellName(int spell) {
        return switch (spell) {
            case 0 -> "Lumenfall";
            case 1 -> "Firebolt";
            case 2 -> "Renew";
            case 3 -> "Ember Ward";
            case 4 -> "Aetherwing";
            default -> "Unknown";
        };
    }

    private static WrittenBookContent createBookContent() {
        return new WrittenBookContent(
                Filterable.passThrough("Spellbook"),
                "Arcanum",
                0,
                List.of(
                        page("Arcanum Spellbook\n\nRight-click casts the selected spell.\n\nPress R to cycle spells.\n\nSneak + right-click opens this book."),
                        page("Lumenfall\n\nCost: " + LUMENFALL_COST + " mana\n\nFires a lunar spark forward and leaves a bright magic light where it lands."),
                        page("Firebolt\n\nCost: " + FIREBOLT_COST + " mana\n\nLaunches a small ball of infernal flame in the direction you are facing."),
                        page("Renew\n\nCost: " + RENEW_COST + " mana\n\nRestores health and grants a short pulse of Regeneration."),
                        page("Ember Ward\n\nCost: " + EMBER_WARD_COST + " mana\n\nWraps the caster in protective heat, granting Fire Resistance and brief Resistance."),
                        page("Aetherwing\n\nCost: " + AETHERWING_COST + " mana\n\nGrants creative-style flight until you land.")
                ),
                true
        );
    }

    private static Filterable<Component> page(String text) {
        return Filterable.passThrough(Component.literal(text));
    }
}
