package com.hellbreecher.arcanum.common.platform;

import net.minecraft.resources.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Collection;

public interface RegistryRegistrar<T> {
    <I extends T> RegistryEntry<I> register(String path, Function<Identifier, I> factory);

    default <I extends T> RegistryEntry<I> register(String path, Supplier<I> factory) {
        return register(path, ignored -> factory.get());
    }

    Collection<RegistryEntry<? extends T>> getEntries();
}
