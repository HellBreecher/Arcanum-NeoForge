package com.hellbreecher.arcanum.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.FuelValues;

import com.hellbreecher.arcanum.core.ArcanumItems;

public class InfernalDiamondItem extends FlintAndSteelItem implements com.hellbreecher.arcanum.common.item.ArcanumFuel, com.hellbreecher.arcanum.common.item.DynamicCraftingRemainder {

    public InfernalDiamondItem(Identifier id) {
        super(new Properties()
                .stacksTo(1)
                .fireResistant()
                .setId(ResourceKey.create(Registries.ITEM, id))
        );
    }

    public ItemStackTemplate getCraftingRemainder(ItemInstance stack) {
        if (!(stack instanceof ItemStack itemStack) || itemStack.isEmpty()) {
            return null;
        }
        return ItemStackTemplate.fromNonEmptyStack(itemStack.copy());
    }

    public ItemStack getContainerItem(ItemStack stack) {
        return stack.copy();
    }

    public int getBurnTime(ItemStack stack, RecipeType<?> recipeType, FuelValues fuelValues) {
        return arcanumBurnTime(stack);
    }

    public int arcanumBurnTime(ItemStack stack) { return 1; }

    public ItemStackTemplate arcanumCraftingRemainder(ItemStack stack) { return ItemStackTemplate.fromNonEmptyStack(stack.copy()); }

    public static void onItemCrafted(Container container) {
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (!stack.isEmpty() && stack.is(ArcanumItems.infernaldiamond.get())) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    container.setItem(slot, ItemStack.EMPTY);
                }
            }
        }
    }
}
