package com.hellbreecher.arcanum.common.conditions;

import com.hellbreecher.arcanum.common.config.ArcanumConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigBooleanCondition(String key) implements ICondition {
    public static final MapCodec<ConfigBooleanCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.STRING.fieldOf("key").forGetter(ConfigBooleanCondition::key))
            .apply(instance, ConfigBooleanCondition::new));

    @Override
    public boolean test(IContext context) {
        return switch (key) {
            case "recipes.enableUpgrades" -> ArcanumConfig.recipesEnableUpgrades();
            case "recipes.enableFermenter" -> ArcanumConfig.recipesEnableFermenter();
            case "recipes.enableSmelting" -> ArcanumConfig.recipesEnableSmelting();
            case "recipes.enableFurnaceBlocks" -> ArcanumConfig.recipesEnableFurnaceBlocks();
            case "recipes.enableGenerators" -> ArcanumConfig.recipesEnableGenerators();
            default -> false;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
