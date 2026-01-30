package com.hellbreecher.arcanum.common.blocks.container;

import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.Slot;

public class InfernalFurnaceMenu extends AbstractContainerMenu {
    private static final int INPUT_SLOT = 0;
    private static final int RESULT_SLOT = 1;
    private static final int CONTAINER_RESULT_SLOT = 2;
    private static final int DATA_COUNT = 4;
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int USE_ROW_SLOT_START = 29;
    private static final int USE_ROW_SLOT_END = 38;

    private final Container container;
    private final ContainerData data;
    private final Level level;
    private final RecipePropertySet acceptedInputs;

    public InfernalFurnaceMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(3), new SimpleContainerData(DATA_COUNT));
    }

    public InfernalFurnaceMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ArcanumMenuTypes.INFERNAL_FURNACE.get(), containerId);
        checkContainerSize(container, 3);
        checkContainerDataCount(data, DATA_COUNT);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        this.acceptedInputs = this.level.recipeAccess().propertySet(RecipePropertySet.FURNACE_INPUT);

        this.addSlot(new Slot(container, INPUT_SLOT, 50, 35));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, container, CONTAINER_RESULT_SLOT, 116, 35));
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
            if (index == RESULT_SLOT) {
                if (!this.moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemstack);
            } else if (index == INPUT_SLOT) {
                if (!this.moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (canSmelt(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= INV_SLOT_START && index < INV_SLOT_END) {
                    if (!this.moveItemStackTo(slotStack, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= USE_ROW_SLOT_START && index < USE_ROW_SLOT_END && !this.moveItemStackTo(slotStack, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
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

    public float getCookProgress() {
        int progress = this.data.get(2);
        int total = this.data.get(3);
        return total != 0 && progress != 0 ? (float) progress / (float) total : 0.0F;
    }

    private boolean canSmelt(ItemStack stack) {
        return this.acceptedInputs.test(stack);
    }
}
