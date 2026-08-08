package com.hellbreecher.arcanum.common.energy;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public interface ArcanumEnergyStorage {
    long amount();
    long capacity();
    int insert(int amount);
    int extract(int amount);
    void setAmount(long amount);
    void serialize(ValueOutput output);
    void deserialize(ValueInput input);
}
