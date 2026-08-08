package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class NeoForgeRegistryFactory implements RegistryPlatform.Factory {
    private final IEventBus eventBus;

    NeoForgeRegistryFactory(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public <T> RegistryRegistrar<T> create(Registry<T> registry, String namespace) {
        DeferredRegister<T> deferred = DeferredRegister.create(registry.key(), namespace);
        deferred.register(eventBus);
        return new NeoForgeRegistrar<>(deferred);
    }

    private static final class NeoForgeRegistrar<T> implements RegistryRegistrar<T> {
        private final DeferredRegister<T> deferred;
        private final List<RegistryEntry<? extends T>> entries = new ArrayList<>();

        private NeoForgeRegistrar(DeferredRegister<T> deferred) { this.deferred = deferred; }
        @Override
        public <I extends T> RegistryEntry<I> register(String path, Function<Identifier, I> factory) {
            DeferredHolder<T, I> holder = deferred.register(path, factory);
            RegistryEntry<I> entry = holder::get;
            entries.add(entry);
            return entry;
        }

        public Collection<RegistryEntry<? extends T>> getEntries() { return List.copyOf(entries); }
    }
}
