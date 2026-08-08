package com.hellbreecher.arcanum.common.energy;

import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

/** Transactional NeoForge view over the loader-neutral store. */
public final class NeoForgeEnergyAdapter extends SnapshotJournal<Long> implements EnergyHandler {
    private final ArcanumEnergyStorage storage;

    public NeoForgeEnergyAdapter(ArcanumEnergyStorage storage) { this.storage = storage; }

    public long getAmountAsLong() { return storage.amount(); }
    public long getCapacityAsLong() { return storage.capacity(); }

    public int insert(int amount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return storage.insert(amount);
    }

    public int extract(int amount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return storage.extract(amount);
    }

    protected Long createSnapshot() { return storage.amount(); }

    protected void revertToSnapshot(Long snapshot) {
        storage.setAmount(snapshot);
    }
}
