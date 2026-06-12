package com.hellbreecher.arcanum.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public class HammerItem extends Item {
    public HammerItem(Identifier id) {
		super(new Item.Properties().stacksTo(1).durability(25).setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public ItemStackTemplate getCraftingRemainder(ItemInstance stack) {
        if (!(stack instanceof ItemStack itemStack) || itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
            return null;
        }
        ItemStack result = itemStack.copy();
        result.setDamageValue(result.getDamageValue() + 1);
        return ItemStackTemplate.fromNonEmptyStack(result);
    }
}
