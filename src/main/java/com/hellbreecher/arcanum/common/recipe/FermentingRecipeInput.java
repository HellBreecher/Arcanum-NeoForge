package com.hellbreecher.arcanum.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record FermentingRecipeInput(
        ItemStack base,
        ItemStack ingredient1,
        ItemStack ingredient2,
        ItemStack ingredient3
) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> base;
            case 1 -> ingredient1;
            case 2 -> ingredient2;
            case 3 -> ingredient3;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 4;
    }
}
