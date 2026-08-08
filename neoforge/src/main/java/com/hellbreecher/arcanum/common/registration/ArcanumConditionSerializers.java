package com.hellbreecher.arcanum.common.registration;

import com.hellbreecher.arcanum.common.conditions.ConfigBooleanCondition;
import com.hellbreecher.arcanum.core.Arcanum;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ArcanumConditionSerializers {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Arcanum.MODID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigBooleanCondition>> CONFIG_BOOLEAN =
            CONDITIONS.register("config_boolean", () -> ConfigBooleanCondition.CODEC);

    private ArcanumConditionSerializers() {
    }

    public static void register(IEventBus eventBus) {
        CONDITIONS.register(eventBus);
    }
}
