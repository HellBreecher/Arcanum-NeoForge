package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.lib.Reference;
import com.hellbreecher.arcanum.common.worldgen.InfernalCrystalPatchFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class ArcanumFeatures {
    public static final RegistryRegistrar<Feature<?>> FEATURES = RegistryPlatform.create(BuiltInRegistries.FEATURE, Reference.MODID);

    public static final RegistryEntry<Feature<NoneFeatureConfiguration>> INFERNAL_CRYSTAL_PATCH = FEATURES.register(
            "infernal_crystal_patch",
            () -> new InfernalCrystalPatchFeature(NoneFeatureConfiguration.CODEC)
    );

    private ArcanumFeatures() {
    }

    public static void bootstrap() { }
}
