package com.hellbreecher.arcanum.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.FuelValues;

public class InfernalDiamondItem extends FlintAndSteelItem {

    public InfernalDiamondItem(Identifier id) {
        super(new Properties()
                .stacksTo(1)
                .fireResistant()
                .setId(ResourceKey.create(Registries.ITEM, id))
        );
    }

    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack newStack = stack.copy();
        return newStack;
    }

    @Override
    public ItemStack getCraftingRemainder(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public int getBurnTime(ItemStack stack, RecipeType<?> recipeType, FuelValues fuelValues) {
        return 1;
    }

    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_FLINT_ACTIONS.contains(itemAbility);
    }
}
