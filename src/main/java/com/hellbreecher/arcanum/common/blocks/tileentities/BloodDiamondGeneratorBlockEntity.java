package com.hellbreecher.arcanum.common.blocks.tileentities;

import com.hellbreecher.arcanum.common.blocks.BloodDiamondGeneratorBlock;
import com.hellbreecher.arcanum.common.blocks.container.SapphireGeneratorMenu;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class BloodDiamondGeneratorBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public static final int FE_PER_TICK = 250;
    public static final int CAPACITY = 5_000_000;
    private static final int FUEL_SLOT = 0;
    private static final int[] SLOTS_FOR_FACES = new int[]{FUEL_SLOT};

    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private int burnTime;
    private int burnTimeTotal;
    private boolean specialFuelMode;
    private final SimpleEnergyHandler energy = new SimpleEnergyHandler(CAPACITY, FE_PER_TICK, CAPACITY) {
        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
        }
    };
    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> BloodDiamondGeneratorBlockEntity.this.burnTime;
                case 1 -> BloodDiamondGeneratorBlockEntity.this.burnTimeTotal;
                case 2 -> BloodDiamondGeneratorBlockEntity.this.energy.getAmountAsInt();
                case 3 -> BloodDiamondGeneratorBlockEntity.this.energy.getCapacityAsInt();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> BloodDiamondGeneratorBlockEntity.this.burnTime = value;
                case 1 -> BloodDiamondGeneratorBlockEntity.this.burnTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public BloodDiamondGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ArcanumBlockEntities.BLOOD_DIAMOND_GENERATOR.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("Blood Diamond Generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new SapphireGeneratorMenu(id, player, this, this.dataAccess);
    }

    public EnergyHandler getEnergyHandler() {
        return this.energy;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BloodDiamondGeneratorBlockEntity blockEntity) {
        boolean hasEnergyRoom = blockEntity.energy.getAmountAsLong() < blockEntity.energy.getCapacityAsLong();
        boolean wasLit = state.getValue(BloodDiamondGeneratorBlock.LIT);
        ItemStack fuel = blockEntity.items.get(FUEL_SLOT);
        int rawBurnTime = blockEntity.getBurnTime(fuel, level);
        boolean specialFuel = rawBurnTime == 1;

        if (specialFuel) {
            blockEntity.specialFuelMode = true;
            if (hasEnergyRoom) {
                if (blockEntity.burnTime <= 0) {
                    blockEntity.consumeFuel(level, pos);
                }
                blockEntity.burnTime = 2;
                blockEntity.burnTimeTotal = 2;
                blockEntity.generateEnergy();
                setChanged(level, pos, state);
            }
        } else {
            if (blockEntity.specialFuelMode) {
                blockEntity.specialFuelMode = false;
                blockEntity.burnTime = 0;
                blockEntity.burnTimeTotal = 0;
                setChanged(level, pos, state);
            }

            if (blockEntity.burnTime > 0) {
                if (hasEnergyRoom) {
                    blockEntity.burnTime--;
                    blockEntity.generateEnergy();
                    setChanged(level, pos, state);
                }
            } else if (hasEnergyRoom && rawBurnTime > 0) {
                blockEntity.burnTime = rawBurnTime;
                blockEntity.burnTimeTotal = rawBurnTime;
                blockEntity.consumeFuel(level, pos);
                setChanged(level, pos, state);
            }
        }

        boolean lit = hasEnergyRoom && (blockEntity.burnTime > 0 || (blockEntity.specialFuelMode && !fuel.isEmpty()));
        if (wasLit != lit) {
            level.setBlock(pos, state.setValue(BloodDiamondGeneratorBlock.LIT, lit), 3);
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS_FOR_FACES;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return index == FUEL_SLOT && getBurnTime(stack, this.level) > 0;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
        return canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.burnTime = input.getIntOr("BurnTime", 0);
        this.burnTimeTotal = input.getIntOr("BurnTimeTotal", 0);
        this.energy.deserialize(input.childOrEmpty("Energy"));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("BurnTime", this.burnTime);
        output.putInt("BurnTimeTotal", this.burnTimeTotal);
        ContainerHelper.saveAllItems(output, this.items);
        this.energy.serialize(output.child("Energy"));
    }

    private int getBurnTime(ItemStack stack, Level level) {
        if (stack.isEmpty() || level == null) {
            return 0;
        }
        return stack.getBurnTime(RecipeType.SMELTING, level.fuelValues());
    }

    private void generateEnergy() {
        try (Transaction transaction = Transaction.openRoot()) {
            int inserted = this.energy.insert(FE_PER_TICK, transaction);
            if (inserted > 0) {
                transaction.commit();
            }
        }
    }

    private void consumeFuel(Level level, BlockPos pos) {
        ItemStack stack = this.items.get(FUEL_SLOT);
        if (stack.isEmpty()) {
            return;
        }
        ItemStack remainder = stack.getCraftingRemainder();
        stack.shrink(1);
        if (!remainder.isEmpty()) {
            if (stack.isEmpty()) {
                stack = remainder;
            } else {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder);
            }
        }
        this.items.set(FUEL_SLOT, stack);
    }
}
