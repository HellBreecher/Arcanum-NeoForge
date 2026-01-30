package com.hellbreecher.arcanum.common.conditions;

import com.hellbreecher.arcanum.core.Config;
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
            case "recipes.enableUpgrades" -> Config.RECIPES_ENABLE_UPGRADES.get();
            case "recipes.enableFermenter" -> Config.RECIPES_ENABLE_FERMENTER.get();
            case "recipes.enableSmelting" -> Config.RECIPES_ENABLE_SMELTING.get();
            case "recipes.enableFurnaceBlocks" -> Config.RECIPES_ENABLE_FURNACE_BLOCKS.get();
            case "recipes.enableGenerators" -> Config.RECIPES_ENABLE_GENERATORS.get();
            default -> false;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
