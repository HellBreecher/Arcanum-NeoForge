package com.hellbreecher.arcanum.common.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public final class ItemBehaviors {
    private ItemBehaviors() { }

    public static int burnTime(ItemStack stack, net.minecraft.world.level.block.entity.FuelValues fuels) {
        if (stack.getItem() instanceof ArcanumFuel fuel) return fuel.arcanumBurnTime(stack);
        return fuels.burnDuration(stack);
    }

    public static ItemStackTemplate craftingRemainder(ItemStack stack) {
        if (stack.getItem() instanceof DynamicCraftingRemainder dynamic) return dynamic.arcanumCraftingRemainder(stack);
        return stack.getItem().getCraftingRemainder();
    }
}
