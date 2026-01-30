package com.hellbreecher.arcanum.common.blocks.container;

import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.Container;

public class IngotFurnaceMenu extends AbstractFurnaceMenu {
    public IngotFurnaceMenu(int containerId, Inventory playerInventory) {
        super(ArcanumMenuTypes.VOID_DIAMOND_FURNACE.get(), RecipeType.SMELTING, RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, playerInventory);
    }

    public IngotFurnaceMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ArcanumMenuTypes.VOID_DIAMOND_FURNACE.get(), RecipeType.SMELTING, RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, playerInventory, container, data);
    }
}
