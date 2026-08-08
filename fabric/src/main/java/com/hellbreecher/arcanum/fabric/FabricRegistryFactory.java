package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class FabricRegistryFactory implements RegistryPlatform.Factory {
    @Override
    public <T> RegistryRegistrar<T> create(Registry<T> registry, String namespace) {
        return new FabricRegistrar<>(registry, namespace);
    }

    private static final class FabricRegistrar<T> implements RegistryRegistrar<T> {
        private final Registry<T> registry;
        private final String namespace;
        private final List<RegistryEntry<? extends T>> entries = new ArrayList<>();

        private FabricRegistrar(Registry<T> registry, String namespace) { this.registry = registry; this.namespace = namespace; }
        @Override
        public <I extends T> RegistryEntry<I> register(String path, Function<Identifier, I> factory) {
            Identifier id = Identifier.fromNamespaceAndPath(namespace, path);
            I value = Registry.register(registry, id, factory.apply(id));
            RegistryEntry<I> entry = () -> value;
            entries.add(entry);
            return entry;
        }

        public Collection<RegistryEntry<? extends T>> getEntries() { return List.copyOf(entries); }
    }
}
