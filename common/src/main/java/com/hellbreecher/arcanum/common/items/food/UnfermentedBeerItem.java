package com.hellbreecher.arcanum.common.items.food;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class UnfermentedBeerItem extends Item {

    public UnfermentedBeerItem(Identifier id) {
        super(new Item.Properties()
        		.durability(10)
                .setId(ResourceKey.create(Registries.ITEM, id))
        		);
    }
}
