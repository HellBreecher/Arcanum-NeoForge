package com.hellbreecher.arcanum.common.platform;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.Objects;
import java.util.function.BiFunction;

public final class MenuTypePlatform {
    @FunctionalInterface
    public interface Factory {
        <T extends AbstractContainerMenu> MenuType<T> create(BiFunction<Integer, Inventory, T> factory);
    }

    private static Factory factory;

    private MenuTypePlatform() { }

    public static void install(Factory factory) { MenuTypePlatform.factory = Objects.requireNonNull(factory); }

    public static <T extends AbstractContainerMenu> MenuType<T> create(BiFunction<Integer, Inventory, T> menuFactory) {
        if (factory == null) throw new IllegalStateException("Menu type platform is not installed");
        return factory.create(menuFactory);
    }
}
