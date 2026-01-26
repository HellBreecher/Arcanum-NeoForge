package com.hellbreecher.arcanum.common.items.weapons;

import com.hellbreecher.arcanum.core.Config;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.projectile.ProjectileUtil;

import java.util.List;
import java.util.Optional;

public class InfernalWandItem extends Item {


    public InfernalWandItem(Identifier id) {
        super(new Properties()
				.component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
				.setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        double reach = 5.0D;
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(reach));

        BlockHitResult blockHit = level.clip(new ClipContext(
                start,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                CollisionContext.of(player)
        ));

        AABB range = player.getBoundingBox().expandTowards(look.scale(reach)).inflate(1.0D);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                level,
                player,
                start,
                end,
                range,
                entity -> entity instanceof ItemEntity,
                ProjectileUtil.computeMargin(player)
        );

        boolean handled = false;
        if (entityHit != null && isCloser(start, entityHit, blockHit)) {
            handled = smeltItemEntity(level, (ItemEntity) entityHit.getEntity());
        } else if (blockHit.getType() == HitResult.Type.BLOCK) {
            handled = smeltBlock(level, player, blockHit.getBlockPos());
        }

        if (!handled) {
            shootFireball(level, player, look);
        }

        return InteractionResult.SUCCESS;
    }

    private static boolean isCloser(Vec3 start, EntityHitResult entityHit, BlockHitResult blockHit) {
        if (blockHit.getType() != HitResult.Type.BLOCK) {
            return true;
        }
        return entityHit.getLocation().distanceToSqr(start) < blockHit.getLocation().distanceToSqr(start);
    }

    private static boolean smeltItemEntity(Level level, ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        ItemStack smelted = smeltStack(level, stack);
        if (smelted == stack) {
            return false;
        }
        itemEntity.setItem(smelted);
        return true;
    }

    private static boolean smeltBlock(Level level, Player player, BlockPos pos) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        BlockState state = level.getBlockState(pos);
        if (state.isAir()) {
            return false;
        }

        ItemStack blockStack = new ItemStack(state.getBlock());
        if (blockStack.isEmpty()) {
            return false;
        }

        ItemStack smelted = smeltStack(level, blockStack);
        if (smelted == blockStack) {
            return false;
        }

        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
        ItemStack tool = player.getItemInHand(InteractionHand.MAIN_HAND);
        List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, blockEntity, player, tool);
        for (ItemStack drop : drops) {
            ItemStack smeltedDrop = smeltStack(level, drop);
            Block.popResource(level, pos, smeltedDrop);
        }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        return true;
    }

    private static ItemStack smeltStack(Level level, ItemStack stack) {
        if (stack.isEmpty() || !(level instanceof ServerLevel serverLevel)) {
            return stack;
        }

        SingleRecipeInput input = new SingleRecipeInput(stack);
        Optional<RecipeHolder<SmeltingRecipe>> recipe = serverLevel.recipeAccess()
                .getRecipeFor(RecipeType.SMELTING, input, serverLevel);
        if (recipe.isEmpty()) {
            return stack;
        }

        ItemStack result = recipe.get().value().assemble(input, serverLevel.registryAccess());
        if (result.isEmpty()) {
            return stack;
        }

        result.setCount(result.getCount() * stack.getCount());
        return result;
    }

    private static void shootFireball(Level level, Player player, Vec3 look) {
        Vec3 accel = look.normalize();
        SmallFireball fireball = new SmallFireball(level, player, accel);
        fireball.setPos(
                player.getX() + accel.x * 1.5D,
                player.getEyeY() - 0.1D + accel.y * 1.5D,
                player.getZ() + accel.z * 1.5D
        );
        level.addFreshEntity(fireball);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Player player) {
        Level level = player.level();
        if (!Config.ENABLE_CRAFTED_ENCHANTMENTS.get()) {
            return;
        }
        if (level.isClientSide() || stack.isEnchanted()) {
            return;
        }

        enchant(stack, level, Enchantments.SHARPNESS, 10);
        enchant(stack, level, Enchantments.SMITE, 10);
        enchant(stack, level, Enchantments.BANE_OF_ARTHROPODS, 10);
        enchant(stack, level, Enchantments.KNOCKBACK, 10);
        enchant(stack, level, Enchantments.FIRE_ASPECT, 10);
        enchant(stack, level, Enchantments.LOOTING, 10);
    }

    private static void enchant(ItemStack stack, Level level, net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> enchantment, int levelValue) {
        stack.enchant(
                level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment),
                levelValue
        );
    }

}



