package com.hellbreecher.arcanum.common.items.armor;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class BaseArmorItem extends Item {

    public BaseArmorItem(ArmorMaterial material, ArmorType type, Item repairItem, Identifier id) {
        super(new Item.Properties()
                .humanoidArmor(material, type)
                .repairable(repairItem)
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }
}
