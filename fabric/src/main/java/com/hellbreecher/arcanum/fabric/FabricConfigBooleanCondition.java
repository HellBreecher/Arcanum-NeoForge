package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.config.ArcanumConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;

record FabricConfigBooleanCondition(String key) implements ResourceCondition {
    static final MapCodec<FabricConfigBooleanCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.STRING.fieldOf("key").forGetter(FabricConfigBooleanCondition::key))
            .apply(instance, FabricConfigBooleanCondition::new));
    static final ResourceConditionType<FabricConfigBooleanCondition> TYPE = ResourceConditionType.create(
            Identifier.fromNamespaceAndPath("arcanum", "config_boolean"), CODEC);

    static void register() { ResourceConditions.register(TYPE); }

    @Override public ResourceConditionType<?> getType() { return TYPE; }

    @Override
    public boolean test(RegistryOps.RegistryInfoLookup registries) {
        return switch (key) {
            case "recipes.enableUpgrades" -> ArcanumConfig.recipesEnableUpgrades();
            case "recipes.enableFermenter" -> ArcanumConfig.recipesEnableFermenter();
            case "recipes.enableSmelting" -> ArcanumConfig.recipesEnableSmelting();
            case "recipes.enableFurnaceBlocks" -> ArcanumConfig.recipesEnableFurnaceBlocks();
            case "recipes.enableGenerators" -> ArcanumConfig.recipesEnableGenerators();
            default -> false;
        };
    }
}
