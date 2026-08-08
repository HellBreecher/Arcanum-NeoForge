package com.hellbreecher.arcanum.common.energy;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Loader-neutral long energy store. Loader APIs expose this through thin transactional adapters. */
public class LongEnergyHandler implements ArcanumEnergyStorage {
    protected long energy;
    protected final long capacity;
    protected final int maxInsert;
    protected final int maxExtract;

    public LongEnergyHandler(long capacity, int maxInsert, int maxExtract) {
        this(capacity, maxInsert, maxExtract, 0L);
    }

    public LongEnergyHandler(long capacity, int maxInsert, int maxExtract, long energy) {
        if (capacity < 0 || maxInsert < 0 || maxExtract < 0 || energy < 0) {
            throw new IllegalArgumentException("Energy values must be non-negative");
        }
        this.capacity = capacity;
        this.maxInsert = maxInsert;
        this.maxExtract = maxExtract;
        this.energy = Math.min(energy, capacity);
    }

    public void serialize(ValueOutput output) { output.putLong("energy", energy); }

    public void deserialize(ValueInput input) { set(Math.clamp(input.getLongOr("energy", 0L), 0L, capacity)); }

    public void set(long amount) {
        if (amount < 0) throw new IllegalArgumentException("Energy must be non-negative");
        long previous = energy;
        energy = Math.min(amount, capacity);
        if (energy != previous) onEnergyChanged(previous);
    }

    public void setAmount(long amount) { set(amount); }

    protected void onEnergyChanged(long previousAmount) { }

    public long amount() { return energy; }

    public long capacity() { return capacity; }

    public int insert(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Energy must be non-negative");
        int inserted = (int) Math.min(capacity - energy, Math.min((long) amount, maxInsert));
        if (inserted > 0) set(energy + inserted);
        return inserted;
    }

    public int extract(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Energy must be non-negative");
        int extracted = (int) Math.min(energy, Math.min((long) amount, maxExtract));
        if (extracted > 0) set(energy - extracted);
        return extracted;
    }
}
