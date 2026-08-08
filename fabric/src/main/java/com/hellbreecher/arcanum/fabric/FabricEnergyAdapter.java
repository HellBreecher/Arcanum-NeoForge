package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.energy.ArcanumEnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import team.reborn.energy.api.EnergyStorage;

final class FabricEnergyAdapter extends SnapshotParticipant<Long> implements EnergyStorage {
    private final ArcanumEnergyStorage storage;

    FabricEnergyAdapter(ArcanumEnergyStorage storage) { this.storage = storage; }

    public long insert(long maxAmount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return storage.insert((int) Math.min(Integer.MAX_VALUE, maxAmount));
    }

    public long extract(long maxAmount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return storage.extract((int) Math.min(Integer.MAX_VALUE, maxAmount));
    }

    public long getAmount() { return storage.amount(); }
    public long getCapacity() { return storage.capacity(); }
    protected Long createSnapshot() { return storage.amount(); }
    protected void readSnapshot(Long snapshot) { storage.setAmount(snapshot); }
}
