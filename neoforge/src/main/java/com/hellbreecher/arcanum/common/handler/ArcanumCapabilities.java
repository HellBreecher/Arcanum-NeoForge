package com.hellbreecher.arcanum.common.handler;

import com.hellbreecher.arcanum.common.blocks.tileentities.BloodDiamondGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.InfernalGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.SapphireGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.blocks.tileentities.VoidDiamondGeneratorBlockEntity;
import com.hellbreecher.arcanum.common.energy.ArcanumEnergyStorage;
import com.hellbreecher.arcanum.common.energy.NeoForgeEnergyAdapter;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public final class ArcanumCapabilities {
    private ArcanumCapabilities() {
    }

    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK,
                ArcanumBlockEntities.SAPPHIRE_GENERATOR.get(),
                (SapphireGeneratorBlockEntity blockEntity, Direction direction) -> asNeoForgeEnergy(blockEntity.getEnergyStorage())
        );
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK,
                ArcanumBlockEntities.BLOOD_DIAMOND_GENERATOR.get(),
                (BloodDiamondGeneratorBlockEntity blockEntity, Direction direction) -> asNeoForgeEnergy(blockEntity.getEnergyStorage())
        );
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK,
                ArcanumBlockEntities.VOID_DIAMOND_GENERATOR.get(),
                (VoidDiamondGeneratorBlockEntity blockEntity, Direction direction) -> asNeoForgeEnergy(blockEntity.getEnergyStorage())
        );
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK,
                ArcanumBlockEntities.INFERNAL_GENERATOR.get(),
                (InfernalGeneratorBlockEntity blockEntity, Direction direction) -> asNeoForgeEnergy(blockEntity.getEnergyStorage())
        );
    }

    private static EnergyHandler asNeoForgeEnergy(ArcanumEnergyStorage storage) {
        return new NeoForgeEnergyAdapter(storage);
    }
}
