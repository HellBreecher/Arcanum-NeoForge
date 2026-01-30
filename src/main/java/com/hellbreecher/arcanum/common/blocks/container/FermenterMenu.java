package com.hellbreecher.arcanum.common.blocks.container;

import com.hellbreecher.arcanum.common.recipe.ArcanumRecipeTypes;
import com.hellbreecher.arcanum.common.recipe.FermentingRecipe;
import com.hellbreecher.arcanum.common.recipe.FermentingRecipeInput;
import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class FermenterMenu extends AbstractContainerMenu {
    private static final int INGREDIENT_SLOT_START = 0;
    private static final int INGREDIENT_SLOT_END = 2;
    private static final int BASE_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;
    private static final int DATA_COUNT = 2;
    private static final int INV_SLOT_START = 5;
    private static final int INV_SLOT_END = 32;
    private static final int USE_ROW_SLOT_START = 32;
    private static final int USE_ROW_SLOT_END = 41;

    private final Container container;
    private final ContainerData data;
    private final Level level;

    public FermenterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(5), new SimpleContainerData(DATA_COUNT));
    }

    public FermenterMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ArcanumMenuTypes.FERMENTER.get(), containerId);
        checkContainerSize(container, 5);
        checkContainerDataCount(data, DATA_COUNT);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();

        this.addSlot(new IngredientSlot(container, 0, 56, 51));
        this.addSlot(new IngredientSlot(container, 1, 79, 58));
        this.addSlot(new IngredientSlot(container, 2, 102, 51));
        this.addSlot(new BaseSlot(container, 3, 17, 17));
        this.addSlot(new OutputSlot(container, 4, 79, 17));
        this.addStandardInventorySlots(playerInventory, 8, 84);
        this.addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            if (index >= INGREDIENT_SLOT_START && index <= OUTPUT_SLOT) {
                if (!this.moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemstack);
            } else if (isIngredientItem(slotStack)) {
                if (!this.moveItemStackTo(slotStack, INGREDIENT_SLOT_START, INGREDIENT_SLOT_END + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (isBaseItem(slotStack)) {
                if (!this.moveItemStackTo(slotStack, BASE_SLOT, BASE_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= INV_SLOT_START && index < INV_SLOT_END) {
                if (!this.moveItemStackTo(slotStack, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= USE_ROW_SLOT_START && index < USE_ROW_SLOT_END && !this.moveItemStackTo(slotStack, INV_SLOT_START, INV_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return itemstack;
    }

    public int getBrewTime() {
        return this.data.get(0);
    }

    public int getBrewTimeTotal() {
        return this.data.get(1);
    }

    private boolean isIngredientItem(ItemStack stack) {
        if (this.level instanceof ServerLevel serverLevel) {
            for (RecipeHolder<?> holder : serverLevel.recipeAccess().getRecipes()) {
                if (holder.value() instanceof FermentingRecipe recipe
                        && (recipe.ingredient1().test(stack)
                        || recipe.ingredient2().test(stack)
                        || recipe.ingredient3().test(stack))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBaseItem(ItemStack stack) {
        if (this.level instanceof ServerLevel serverLevel) {
            for (RecipeHolder<?> holder : serverLevel.recipeAccess().getRecipes()) {
                if (holder.value() instanceof FermentingRecipe recipe && recipe.base().test(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class IngredientSlot extends Slot {
        public IngredientSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }
    }

    private static class BaseSlot extends Slot {
        public BaseSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }

    private static class OutputSlot extends Slot {
        public OutputSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }
}
