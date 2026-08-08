package com.hellbreecher.arcanum.common.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public interface DynamicCraftingRemainder {
    ItemStackTemplate arcanumCraftingRemainder(ItemStack stack);
}
