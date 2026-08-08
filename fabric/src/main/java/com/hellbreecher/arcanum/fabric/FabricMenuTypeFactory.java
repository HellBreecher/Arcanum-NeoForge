package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.platform.MenuTypePlatform;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.BiFunction;

final class FabricMenuTypeFactory implements MenuTypePlatform.Factory {
    @Override
    public <T extends AbstractContainerMenu> MenuType<T> create(BiFunction<Integer, Inventory, T> factory) {
        return new ExtendedMenuType<>((id, inventory, ignored) -> factory.apply(id, inventory), StreamCodec.unit(false));
    }
}
