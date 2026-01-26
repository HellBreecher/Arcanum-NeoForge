package com.hellbreecher.arcanum.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HammerItem extends Item {
    public HammerItem(Identifier id) {
		super(new Item.Properties().stacksTo(1).durability(25).setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public ItemStack getCraftingRemainder(ItemStack stack) {
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return ItemStack.EMPTY;
        }
        ItemStack result = stack.copy();
        result.setDamageValue(result.getDamageValue() + 1);
        return result;
    }
}
