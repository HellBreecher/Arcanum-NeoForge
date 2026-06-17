package com.hellbreecher.arcanum.common.items;

import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.common.handler.magic.SpellFlightManager;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumTools;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
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
import net.minecraft.world.entity.LivingEntity;
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
import java.util.UUID;

public class SpellbookItem extends WrittenBookItem {
    private static final String SPELL_KEY = "ArcanumSpell";
    private static final UUID DEVELOPER_UUID = UUID.fromString("0f3009c8-0403-42fc-9210-e959cd41988e");
    private static final int SPELL_COUNT = 12;
    private static final int FIREBOLT_COST = 20;
    private static final int EMBER_WARD_COST = 35;
    private static final int RENEW_COST = 30;
    private static final int LUMENFALL_COST = 2;
    private static final int AETHERWING_COST = 250;
    private static final int BLINK_COST = 25;
    private static final int INFERNAL_CHAINS_COST = 30;
    private static final int SOULFLARE_COST = 35;
    private static final int ASH_STEP_COST = 20;
    private static final int CRYSTAL_WARD_COST = 45;
    private static final int DISPEL_COST = 20;
    private static final int DEV_RIFT_COST = 1;
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
        if (!canUseSpell(player, spell)) {
            player.sendOverlayMessage(Component.literal("This spell is developer-only"));
            return InteractionResult.CONSUME;
        }
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

    public static int nextSpell(Player player, int spell) {
        int nextSpell = nextSpell(spell);
        while (!canUseSpell(player, nextSpell)) {
            nextSpell = nextSpell(nextSpell);
        }
        return nextSpell;
    }

    public static boolean cycleSelectedSpell(Player player) {
        Optional<ItemStack> spellbook = findSpellbook(player);
        if (spellbook.isEmpty()) {
            return false;
        }

        ItemStack stack = spellbook.get();
        int nextSpell = nextSpell(player, getSelectedSpell(stack));
        setSelectedSpell(stack, nextSpell);
        player.sendOverlayMessage(Component.literal("Selected: " + getSpellName(nextSpell)));
        return true;
    }

    private static void castSpell(Level level, Player player, int spell) {
        switch (spell) {
            case 0 -> castLumenfall(level, player);
            case 1 -> castFirebolt(level, player);
            case 2 -> castRenew(player);
            case 3 -> castEmberWard(level, player);
            case 4 -> castAetherwing(level, player);
            case 5 -> castBlink(level, player, false);
            case 6 -> castInfernalChains(level, player, false);
            case 7 -> castSoulflare(level, player, false);
            case 8 -> castAshStep(level, player, false);
            case 9 -> castCrystalWard(level, player, false);
            case 10 -> castDispel(level, player, false);
            case 11 -> castDeveloperRift(level, player);
            default -> {
            }
        }
        player.sendOverlayMessage(Component.literal("Cast: " + getSpellName(spell)));
    }

    public static boolean castFocusedSelectedSpell(Level level, Player player) {
        Optional<ItemStack> spellbook = findSpellbook(player);
        if (spellbook.isEmpty()) {
            return false;
        }

        int spell = getSelectedSpell(spellbook.get());
        if (!canUseSpell(player, spell)) {
            player.sendOverlayMessage(Component.literal("This spell is developer-only"));
            return true;
        }
        int cost = spellCost(spell);
        if (!ManaManager.spend(player, cost)) {
            player.sendOverlayMessage(Component.literal("Not enough mana (" + cost + " needed)"));
            return true;
        }

        castFocusedSpell(level, player, spell);
        return true;
    }

    private static Optional<ItemStack> findSpellbook(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(ArcanumItems.spellbook.get())) {
                return Optional.of(stack);
            }
        }
        return Optional.empty();
    }

    private static void castFocusedSpell(Level level, Player player, int spell) {
        switch (spell) {
            case 0 -> castFocusedLumenfall(level, player);
            case 1 -> castFocusedFirebolt(level, player);
            case 2 -> castFocusedRenew(player);
            case 3 -> castFocusedEmberWard(level, player);
            case 4 -> castFocusedAetherwing(level, player);
            case 5 -> castBlink(level, player, true);
            case 6 -> castInfernalChains(level, player, true);
            case 7 -> castSoulflare(level, player, true);
            case 8 -> castAshStep(level, player, true);
            case 9 -> castCrystalWard(level, player, true);
            case 10 -> castDispel(level, player, true);
            case 11 -> castDeveloperRift(level, player);
            default -> {
            }
        }
        player.sendOverlayMessage(Component.literal("Focused: " + getSpellName(spell)));
    }

    private static int spellCost(int spell) {
        return switch (spell) {
            case 0 -> LUMENFALL_COST;
            case 1 -> FIREBOLT_COST;
            case 2 -> RENEW_COST;
            case 3 -> EMBER_WARD_COST;
            case 4 -> AETHERWING_COST;
            case 5 -> BLINK_COST;
            case 6 -> INFERNAL_CHAINS_COST;
            case 7 -> SOULFLARE_COST;
            case 8 -> ASH_STEP_COST;
            case 9 -> CRYSTAL_WARD_COST;
            case 10 -> DISPEL_COST;
            case 11 -> DEV_RIFT_COST;
            default -> 0;
        };
    }

    public static boolean canUseSpell(Player player, int spell) {
        return spell != 11 || player.getUUID().equals(DEVELOPER_UUID);
    }

    private static void castFirebolt(Level level, Player player) {
        if (tryFireboltTransmutation(level, player)) {
            return;
        }

        shootFireball(level, player, player.getLookAngle().normalize(), 1.0F);
    }

    private static void castFocusedFirebolt(Level level, Player player) {
        if (tryFireboltTransmutation(level, player, 64.0D)) {
            return;
        }

        Vec3 look = player.getLookAngle().normalize();
        shootFireball(level, player, look, 0.85F);
        shootFireball(level, player, look.add(0.05D, 0.0D, -0.05D).normalize(), 1.1F);
        shootFireball(level, player, look.add(-0.05D, 0.0D, 0.05D).normalize(), 1.1F);
    }

    private static void shootFireball(Level level, Player player, Vec3 look, float pitch) {
        SmallFireball fireball = new SmallFireball(level, player, look);
        fireball.setPos(
                player.getX() + look.x * 1.5D,
                player.getEyeY() - 0.1D + look.y * 1.5D,
                player.getZ() + look.z * 1.5D
        );
        level.addFreshEntity(fireball);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8F, pitch);
    }

    private static boolean tryFireboltTransmutation(Level level, Player player) {
        return tryFireboltTransmutation(level, player, FIREBOLT_RANGE);
    }

    public static boolean tryFireboltTransmutation(Level level, Player player, double range) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(range));
        BlockHitResult blockHit = level.clip(new ClipContext(
                start,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        double maxDistance = blockHit.getType() == HitResult.Type.BLOCK
                ? start.distanceTo(blockHit.getLocation())
                : range;
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
        ItemTransmutation transmutation = getTransmutedItem(stack);
        if (transmutation == null) {
            return;
        }
        ItemStack result = stack.transmuteCopy(transmutation.result(), stack.getCount() * transmutation.countMultiplier());
        if (transmutation.stripEnchantments()) {
            result.remove(DataComponents.ENCHANTMENTS);
            result.remove(DataComponents.STORED_ENCHANTMENTS);
        }
        if (result.isDamageableItem() && result.getDamageValue() >= result.getMaxDamage()) {
            result.setDamageValue(Math.max(0, result.getMaxDamage() - 1));
        }
        itemEntity.setItem(result);
        itemEntity.clearFire();
        playFireboltTransmutation(level, itemEntity.position());
    }

    private static ItemTransmutation getTransmutedItem(ItemStack stack) {
        if (stack.is(ArcanumBlocks.voiddiamond_block_item.get())) {
            return keepEnchantments(ArcanumItems.infernaldiamond.get());
        }
        if (stack.is(ArcanumBlocks.voiddiamondfurnace_block_item.get())) {
            return keepEnchantments(ArcanumBlocks.infernalfurnace_block_item.get());
        }
        if (stack.is(ArcanumBlocks.voiddiamondgenerator_block_item.get())) {
            return keepEnchantments(ArcanumBlocks.infernalgenerator_block_item.get());
        }
        if (stack.is(ArcanumTools.voiddiamondpickaxe.get())) {
            return keepEnchantments(ArcanumTools.infernaldiamondpickaxe.get());
        }
        if (stack.is(ArcanumTools.voiddiamondaxe.get())) {
            return keepEnchantments(ArcanumTools.infernaldiamondaxe.get());
        }
        if (stack.is(ArcanumTools.voiddiamondhoe.get())) {
            return keepEnchantments(ArcanumTools.infernaldiamondhoe.get());
        }
        if (stack.is(ArcanumTools.voiddiamondshovel.get())) {
            return keepEnchantments(ArcanumTools.infernaldiamondshovel.get());
        }
        if (stack.is(ArcanumTools.voiddiamondshears.get())) {
            return keepEnchantments(ArcanumTools.infernaldiamondshears.get());
        }
        if (stack.is(ArcanumWeapons.voiddiamondsword.get())) {
            return keepEnchantments(ArcanumWeapons.infernaldiamondsword.get());
        }
        if (stack.is(ArcanumArmor.voiddiamondhelmet.get())) {
            return keepEnchantments(ArcanumArmor.infernaldiamondhelmet.get());
        }
        if (stack.is(ArcanumArmor.voiddiamondchestplate.get())) {
            return keepEnchantments(ArcanumArmor.infernaldiamondchestplate.get());
        }
        if (stack.is(ArcanumArmor.voiddiamondleggings.get())) {
            return keepEnchantments(ArcanumArmor.infernaldiamondleggings.get());
        }
        if (stack.is(ArcanumArmor.voiddiamondboots.get())) {
            return keepEnchantments(ArcanumArmor.infernaldiamondboots.get());
        }
        if (stack.is(ArcanumTools.infernaldiamondpickaxe.get())) {
            return stripEnchantments(ArcanumTools.infernalpickaxe.get());
        }
        if (stack.is(ArcanumTools.infernaldiamondaxe.get())) {
            return stripEnchantments(ArcanumTools.infernalaxe.get());
        }
        if (stack.is(ArcanumTools.infernaldiamondhoe.get())) {
            return stripEnchantments(ArcanumTools.infernalhoe.get());
        }
        if (stack.is(ArcanumTools.infernaldiamondshovel.get())) {
            return stripEnchantments(ArcanumTools.infernalshovel.get());
        }
        if (stack.is(ArcanumTools.infernaldiamondshears.get())) {
            return stripEnchantments(ArcanumTools.infernalshears.get());
        }
        if (stack.is(ArcanumWeapons.infernaldiamondsword.get())) {
            return stripEnchantments(ArcanumWeapons.infernalsword.get());
        }
        if (stack.is(ArcanumWeapons.infernalbeatingstick.get())) {
            return keepEnchantments(ArcanumWeapons.infernalwand.get());
        }
        if (stack.is(ArcanumArmor.infernaldiamondhelmet.get())) {
            return stripEnchantments(ArcanumArmor.infernalhelmet.get());
        }
        if (stack.is(ArcanumArmor.infernaldiamondchestplate.get())) {
            return stripEnchantments(ArcanumArmor.infernalchestplate.get());
        }
        if (stack.is(ArcanumArmor.infernaldiamondleggings.get())) {
            return stripEnchantments(ArcanumArmor.infernalleggings.get());
        }
        if (stack.is(ArcanumArmor.infernaldiamondboots.get())) {
            return stripEnchantments(ArcanumArmor.infernalboots.get());
        }
        return null;
    }

    private static ItemTransmutation keepEnchantments(Item result) {
        return new ItemTransmutation(result, 1, false);
    }

    private static ItemTransmutation stripEnchantments(Item result) {
        return new ItemTransmutation(result, 1, true);
    }

    private record ItemTransmutation(Item result, int countMultiplier, boolean stripEnchantments) {
    }

    private static boolean transmuteVoidBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(ArcanumBlocks.voiddiamond_block.get())) {
            level.removeBlock(pos, false);
            Block.popResource(level, pos, new ItemStack(ArcanumItems.infernaldiamond.get(), 1));
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

    private static void castFocusedEmberWard(Level level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 400, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 1));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 0.5F);
    }

    private static void castAetherwing(Level level, Player player) {
        SpellFlightManager.activate(player);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 0.6F, 1.4F);
    }

    private static void castFocusedAetherwing(Level level, Player player) {
        SpellFlightManager.activate(player);
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 0));
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 400, 1));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 0.8F, 1.8F);
    }

    private static void castRenew(Player player) {
        player.heal(6.0F);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 1.2F);
    }

    private static void castFocusedRenew(Player player) {
        player.heal(12.0F);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 1));
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.6F);
    }

    private static void castLumenfall(Level level, Player player) {
        castLumenfall(level, player, LUMENFALL_RANGE, false);
    }

    private static void castFocusedLumenfall(Level level, Player player) {
        castLumenfall(level, player, 96.0D, true);
    }

    private static void castLumenfall(Level level, Player player, double range, boolean focused) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(range));
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
        if (focused) {
            placeMagicLightCluster(level, pos);
        } else {
            placeMagicLight(level, pos);
        }

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

    private static void placeMagicLightCluster(Level level, BlockPos pos) {
        placeMagicLight(level, pos);
        placeMagicLight(level, pos.north());
        placeMagicLight(level, pos.south());
        placeMagicLight(level, pos.east());
        placeMagicLight(level, pos.west());
    }

    private static void castBlink(Level level, Player player, boolean focused) {
        double range = focused ? 24.0D : 12.0D;
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(range));
        BlockHitResult hit = level.clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 destination = hit.getType() == HitResult.Type.BLOCK
                ? Vec3.atBottomCenterOf(hit.getBlockPos().relative(hit.getDirection()))
                : end;
        player.teleportTo(destination.x, destination.y, destination.z);
        if (focused) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0));
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.8F, focused ? 1.4F : 1.0F);
    }

    private static void castInfernalChains(Level level, Player player, boolean focused) {
        double range = focused ? 24.0D : 16.0D;
        double radius = focused ? 5.0D : 2.0D;
        Optional<LivingEntity> target = findTargetLiving(level, player, range);
        Vec3 center = target.map(LivingEntity::position).orElse(player.position().add(player.getLookAngle().normalize().scale(range * 0.5D)));
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius),
                entity -> entity != player && entity.isAlive());
        for (LivingEntity entity : targets) {
            entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, focused ? 160 : 100, focused ? 4 : 2));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, focused ? 160 : 100, focused ? 1 : 0));
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, focused ? 160 : 100, 0));
        }
        level.playSound(null, center.x, center.y, center.z, SoundEvents.CHAIN_PLACE, SoundSource.PLAYERS, 1.0F, focused ? 0.5F : 0.7F);
    }

    private static void castSoulflare(Level level, Player player, boolean focused) {
        double range = focused ? 32.0D : 20.0D;
        double radius = focused ? 4.0D : 1.5D;
        Optional<LivingEntity> target = findTargetLiving(level, player, range);
        if (target.isEmpty()) {
            player.sendOverlayMessage(Component.literal("No soul target"));
            return;
        }

        Vec3 center = target.get().position();
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius),
                entity -> entity != player && entity.isAlive());
        for (LivingEntity entity : targets) {
            entity.setRemainingFireTicks(Math.max(entity.getRemainingFireTicks(), focused ? 160 : 100));
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, focused ? 200 : 120, 0));
        }
        ManaManager.addMana(player, focused ? 30 : 15);
        level.playSound(null, center.x, center.y, center.z, SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 1.0F, focused ? 0.6F : 0.9F);
    }

    private static void castAshStep(Level level, Player player, boolean focused) {
        Vec3 look = player.getLookAngle().normalize();
        double distance = focused ? 10.0D : 6.0D;
        player.setDeltaMovement(player.getDeltaMovement().add(look.x * distance * 0.2D, focused ? 0.25D : 0.1D, look.z * distance * 0.2D));
        player.hurtMarked = true;
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, focused ? 200 : 100, 0));
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, focused ? 160 : 80, focused ? 2 : 1));
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY() + 0.15D, player.getZ(), focused ? 24 : 12, 0.4D, 0.1D, 0.4D, 0.02D);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.9F, focused ? 1.4F : 1.1F);
    }

    private static void castCrystalWard(Level level, Player player, boolean focused) {
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, focused ? 300 : 180, focused ? 1 : 0));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, focused ? 500 : 300, focused ? 2 : 1));
        if (focused) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0));
        }
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY() + 1.0D, player.getZ(), focused ? 36 : 18, 0.8D, 0.8D, 0.8D, 0.02D);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, focused ? 0.8F : 1.1F);
    }

    private static void castDispel(Level level, Player player, boolean focused) {
        player.clearFire();
        player.removeEffect(MobEffects.POISON);
        player.removeEffect(MobEffects.WITHER);
        player.removeEffect(MobEffects.BLINDNESS);
        player.removeEffect(MobEffects.SLOWNESS);
        player.removeEffect(MobEffects.WEAKNESS);
        if (focused) {
            player.removeEffect(MobEffects.HUNGER);
            player.removeEffect(MobEffects.NAUSEA);
            player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 120, 0));
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.8F, focused ? 1.6F : 1.2F);
    }

    private static void castDeveloperRift(Level level, Player player) {
        if (!canUseSpell(player, 11)) {
            player.sendOverlayMessage(Component.literal("This spell is developer-only"));
            return;
        }
        ManaManager.addMana(player, 1000);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 3));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 400, 3));
        player.addEffect(new MobEffectInstance(MobEffects.SPEED, 400, 3));
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, player.getX(), player.getY() + 1.0D, player.getZ(), 80, 2.0D, 1.0D, 2.0D, 0.03D);
            serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY() + 1.0D, player.getZ(), 120, 2.5D, 1.5D, 2.5D, 0.08D);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 1.0F, 0.6F);
    }

    private static Optional<LivingEntity> findTargetLiving(Level level, Player player, double range) {
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();
        Vec3 end = start.add(look.scale(range));
        AABB searchArea = new AABB(start, end).inflate(1.0D);
        return level.getEntitiesOfClass(LivingEntity.class, searchArea, entity -> entity != player && entity.isAlive())
                .stream()
                .filter(entity -> distanceAlongRay(start, look, entity.position()) >= 0.0D)
                .filter(entity -> distanceAlongRay(start, look, entity.position()) <= range)
                .filter(entity -> distanceFromRaySqr(start, look, entity.position()) <= 1.25D)
                .min(Comparator.comparingDouble(entity -> distanceAlongRay(start, look, entity.position())));
    }

    public static String getSpellName(int spell) {
        return switch (spell) {
            case 0 -> "Lumenfall";
            case 1 -> "Firebolt";
            case 2 -> "Renew";
            case 3 -> "Ember Ward";
            case 4 -> "Aetherwing";
            case 5 -> "Blink";
            case 6 -> "Infernal Chains";
            case 7 -> "Soulflare";
            case 8 -> "Ash Step";
            case 9 -> "Crystal Ward";
            case 10 -> "Dispel";
            case 11 -> "Developer Rift";
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
                        page("Infernal Wand\n\nFirebolt can transmute a dropped Infernal Beating Stick into an Infernal Wand.\n\nWith this book in your inventory, the wand focuses the selected spell instead of its base fire magic."),
                        page("Renew\n\nCost: " + RENEW_COST + " mana\n\nRestores health and grants a short pulse of Regeneration."),
                        page("Ember Ward\n\nCost: " + EMBER_WARD_COST + " mana\n\nWraps the caster in protective heat, granting Fire Resistance and brief Resistance."),
                        page("Aetherwing\n\nCost: " + AETHERWING_COST + " mana\n\nGrants creative-style flight until you land."),
                        page("Blink\n\nCost: " + BLINK_COST + " mana\n\nTeleports a short distance toward your target."),
                        page("Infernal Chains\n\nCost: " + INFERNAL_CHAINS_COST + " mana\n\nBinds nearby enemies with Slowness, Weakness, and Glowing."),
                        page("Soulflare\n\nCost: " + SOULFLARE_COST + " mana\n\nIgnites and marks a soul target, restoring mana to the caster."),
                        page("Ash Step\n\nCost: " + ASH_STEP_COST + " mana\n\nDashes forward through flame and grants brief speed and fire resistance."),
                        page("Crystal Ward\n\nCost: " + CRYSTAL_WARD_COST + " mana\n\nSurrounds the caster with protective crystal energy."),
                        page("Dispel\n\nCost: " + DISPEL_COST + " mana\n\nClears fire and common harmful effects."),
                        page("Developer Rift\n\nDeveloper-only.\n\nBends mana around the caster in a short-lived rift of power.")
                ),
                true
        );
    }

    private static Filterable<Component> page(String text) {
        return Filterable.passThrough(Component.literal(text));
    }
}
