package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.lib.Reference;
import com.hellbreecher.arcanum.common.worldgen.InfernalCrystalPatchFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcanumFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Reference.MODID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> INFERNAL_CRYSTAL_PATCH = FEATURES.register(
            "infernal_crystal_patch",
            () -> new InfernalCrystalPatchFeature(NoneFeatureConfiguration.CODEC)
    );

    private ArcanumFeatures() {
    }

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
