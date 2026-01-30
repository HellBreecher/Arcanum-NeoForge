package com.hellbreecher.arcanum.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.FuelValues;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import com.hellbreecher.arcanum.core.ArcanumItems;

public class InfernalDiamondItem extends FlintAndSteelItem {

    public InfernalDiamondItem(Identifier id) {
        super(new Properties()
                .stacksTo(1)
                .fireResistant()
                .setId(ResourceKey.create(Registries.ITEM, id))
        );
    }

    @Override
    public ItemStack getCraftingRemainder(ItemStack stack) {
        return stack.copy();
    }

    public ItemStack getContainerItem(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public int getBurnTime(ItemStack stack, RecipeType<?> recipeType, FuelValues fuelValues) {
        return 1;
    }

    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_FLINT_ACTIONS.contains(itemAbility);
    }

    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        Container container = event.getInventory();
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
