package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.platform.MenuTypePlatform;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import java.util.function.BiFunction;

final class NeoForgeMenuTypeFactory implements MenuTypePlatform.Factory {
    @Override
    public <T extends AbstractContainerMenu> MenuType<T> create(BiFunction<Integer, Inventory, T> factory) {
        return IMenuTypeExtension.create((id, inventory, data) -> factory.apply(id, inventory));
    }
}
