package com.hellbreecher.arcanum.common.blocks.tileentities;

import com.hellbreecher.arcanum.common.blocks.container.InfernalFurnaceMenu;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InfernalFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private static final int SPEED_MULTIPLIER = 50;
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2};
    private static final int[] SLOTS_FOR_SIDES = new int[0];

    public InfernalFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ArcanumBlockEntities.INFERNAL_FURNACE.get(), pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.arcanum.infernalfurnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new InfernalFurnaceMenu(id, player, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, InfernalFurnaceBlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < SPEED_MULTIPLIER; i++) {
                maybeInjectFuel(serverLevel, blockEntity);
                AbstractFurnaceBlockEntity.serverTick(serverLevel, pos, state, blockEntity);
                clearInjectedFuel(blockEntity);
            }
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        }
        return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 1) {
            return false;
        }
        return super.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index != 1 && super.canTakeItemThroughFace(index, stack, direction);
    }

    private static void maybeInjectFuel(ServerLevel level, InfernalFurnaceBlockEntity blockEntity) {
        if (!blockEntity.items.get(1).isEmpty()) {
            return;
        }
        if (!canSmelt(level, blockEntity)) {
            return;
        }
        blockEntity.items.set(1, new ItemStack(Items.BLAZE_ROD));
    }

    private static void clearInjectedFuel(InfernalFurnaceBlockEntity blockEntity) {
        ItemStack fuel = blockEntity.items.get(1);
        if (!fuel.isEmpty() && fuel.is(Items.BLAZE_ROD)) {
            blockEntity.items.set(1, ItemStack.EMPTY);
        }
    }

    private static boolean canSmelt(ServerLevel level, InfernalFurnaceBlockEntity blockEntity) {
        ItemStack input = blockEntity.items.get(0);
        if (input.isEmpty()) {
            return false;
        }
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        RecipeHolder<? extends AbstractCookingRecipe> recipe = level.recipeAccess()
                .getRecipeFor(RecipeType.SMELTING, recipeInput, level)
                .orElse(null);
        if (recipe == null) {
            return false;
        }
        ItemStack result = ((AbstractCookingRecipe) recipe.value()).assemble(recipeInput, level.registryAccess());
        if (result.isEmpty()) {
            return false;
        }
        ItemStack output = blockEntity.items.get(2);
        if (output.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameComponents(output, result)) {
            return false;
        }
        int maxStackSize = Math.min(blockEntity.getMaxStackSize(), output.getMaxStackSize());
        return output.getCount() + result.getCount() <= maxStackSize;
    }
}
