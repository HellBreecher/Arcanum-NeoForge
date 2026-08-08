package com.hellbreecher.arcanum.common.platform;

import net.minecraft.core.Registry;

import java.util.Objects;

public final class RegistryPlatform {
    @FunctionalInterface
    public interface Factory {
        <T> RegistryRegistrar<T> create(Registry<T> registry, String namespace);
    }

    private static Factory factory;

    private RegistryPlatform() {
    }

    public static void install(Factory factory) {
        if (RegistryPlatform.factory != null) {
            throw new IllegalStateException("Arcanum registry platform is already installed");
        }
        RegistryPlatform.factory = Objects.requireNonNull(factory, "factory");
    }

    public static <T> RegistryRegistrar<T> create(Registry<T> registry, String namespace) {
        if (factory == null) {
            throw new IllegalStateException("Arcanum registry platform must be installed before content classes load");
        }
        return factory.create(registry, namespace);
    }
}
