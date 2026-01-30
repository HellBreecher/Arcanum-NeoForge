package com.hellbreecher.arcanum.common.energy;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class LongEnergyHandler implements EnergyHandler, ValueIOSerializable {
    protected long energy;
    protected long capacity;
    protected int maxInsert;
    protected int maxExtract;

    private final EnergyJournal energyJournal = new EnergyJournal();

    public LongEnergyHandler(long capacity, int maxInsert, int maxExtract) {
        this(capacity, maxInsert, maxExtract, 0L);
    }

    public LongEnergyHandler(long capacity, int maxInsert, int maxExtract, long energy) {
        checkNonNegativeLong(capacity);
        TransferPreconditions.checkNonNegative(maxInsert);
        TransferPreconditions.checkNonNegative(maxExtract);
        checkNonNegativeLong(energy);

        this.capacity = capacity;
        this.maxInsert = maxInsert;
        this.maxExtract = maxExtract;
        this.energy = energy;
    }

    @Override
    public void serialize(ValueOutput output) {
        output.putLong("energy", energy);
    }

    @Override
    public void deserialize(ValueInput input) {
        energy = Math.max(0L, input.getLongOr("energy", 0L));
    }

    public void set(long amount) {
        checkNonNegativeLong(amount);

        if (this.energy != amount) {
            long previousAmount = this.energy;
            this.energy = amount;
            onEnergyChanged(previousAmount);
        }
    }

    protected void onEnergyChanged(long previousAmount) {
    }

    @Override
    public long getAmountAsLong() {
        return this.energy;
    }

    @Override
    public long getCapacityAsLong() {
        return this.capacity;
    }

    @Override
    public int insert(int amount, TransactionContext transaction) {
        checkNonNegativeLong(amount);

        long room = capacity - energy;
        if (room <= 0L) {
            return 0;
        }
        int inserted = (int) Math.min(room, Math.min((long) amount, (long) maxInsert));
        if (inserted > 0) {
            energyJournal.updateSnapshots(transaction);
            energy += inserted;
            return inserted;
        }
        return 0;
    }

    @Override
    public int extract(int amount, TransactionContext transaction) {
        TransferPreconditions.checkNonNegative(amount);

        if (energy <= 0L) {
            return 0;
        }
        int extracted = (int) Math.min(energy, Math.min((long) amount, (long) maxExtract));
        if (extracted > 0) {
            energyJournal.updateSnapshots(transaction);
            energy -= extracted;
            return extracted;
        }
        return 0;
    }

    private class EnergyJournal extends SnapshotJournal<Long> {
        @Override
        protected Long createSnapshot() {
            return energy;
        }

        @Override
        protected void revertToSnapshot(Long snapshot) {
            energy = snapshot;
        }

        @Override
        protected void onRootCommit(Long originalState) {
            long previousAmount = originalState;
            if (energy != previousAmount) {
                onEnergyChanged(previousAmount);
            }
        }
    }

    private static void checkNonNegativeLong(long value) {
        if (value < 0L) {
            throw new IllegalArgumentException("Expected value to be non-negative: " + value);
        }
    }
}
