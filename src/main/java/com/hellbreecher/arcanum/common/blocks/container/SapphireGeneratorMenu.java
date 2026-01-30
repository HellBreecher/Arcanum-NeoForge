package com.hellbreecher.arcanum.common.blocks.container;

import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SapphireGeneratorMenu extends AbstractContainerMenu {
    private static final int FUEL_SLOT = 0;
    private static final int DATA_COUNT = 4;
    private static final int INV_SLOT_START = 1;
    private static final int INV_SLOT_END = 28;
    private static final int USE_ROW_SLOT_START = 28;
    private static final int USE_ROW_SLOT_END = 37;

    private final Container container;
    private final ContainerData data;
    private final Level level;

    public SapphireGeneratorMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(1), new SimpleContainerData(DATA_COUNT));
    }

    public SapphireGeneratorMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ArcanumMenuTypes.SAPPHIRE_GENERATOR.get(), containerId);
        checkContainerSize(container, 1);
        checkContainerDataCount(data, DATA_COUNT);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();

        this.addSlot(new FuelSlot(container, FUEL_SLOT, 56, 53, this.level));
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
            if (index == FUEL_SLOT) {
                if (!this.moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemstack);
            } else if (isFuel(slotStack)) {
                if (!this.moveItemStackTo(slotStack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
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

    public int getBurnTime() {
        return this.data.get(0);
    }

    public int getBurnTimeTotal() {
        return this.data.get(1);
    }

    public int getEnergyStored() {
        return this.data.get(2);
    }

    public int getEnergyCapacity() {
        return this.data.get(3);
    }

    private boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(RecipeType.SMELTING, this.level.fuelValues()) > 0;
    }

    private static class FuelSlot extends Slot {
        private final Level level;

        public FuelSlot(Container container, int index, int x, int y, Level level) {
            super(container, index, x, y);
            this.level = level;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(RecipeType.SMELTING, this.level.fuelValues()) > 0;
        }
    }
}
