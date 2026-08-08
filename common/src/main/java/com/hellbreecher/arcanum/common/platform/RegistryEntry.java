package com.hellbreecher.arcanum.common.platform;

import java.util.function.Supplier;

/** A loader-neutral reference to content registered under a stable identifier. */
@FunctionalInterface
public interface RegistryEntry<T> extends Supplier<T> {
}
