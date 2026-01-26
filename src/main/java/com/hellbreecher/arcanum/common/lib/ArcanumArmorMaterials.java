package com.hellbreecher.arcanum.common.lib;

import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public final class ArcanumArmorMaterials {
    public static final TagKey<Item> GREEN_SAPPHIRE_REPAIR = itemTag("armor_materials/greensapphire");
    public static final TagKey<Item> BLOOD_DIAMOND_REPAIR = itemTag("armor_materials/blooddiamond");
    public static final TagKey<Item> VOID_DIAMOND_REPAIR = itemTag("armor_materials/voiddiamond");
    public static final TagKey<Item> INFERNAL_DIAMOND_REPAIR = itemTag("armor_materials/infernaldiamond");
    public static final TagKey<Item> INFERNAL_REPAIR = itemTag("armor_materials/infernal");

    public static final ArmorMaterial GREEN_SAPPHIRE = new ArmorMaterial(
            66,
            defense(4, 5, 8, 5, 16),
            10,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            20.0F,
            0.0F,
            GREEN_SAPPHIRE_REPAIR,
            assetKey("greensapphire")
    );

    public static final ArmorMaterial BLOOD_DIAMOND = new ArmorMaterial(
            99,
            defense(6, 8, 10, 8, 24),
            15,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            40.0F,
            0.0F,
            BLOOD_DIAMOND_REPAIR,
            assetKey("blooddiamond")
    );

    public static final ArmorMaterial VOID_DIAMOND = new ArmorMaterial(
            132,
            defense(10, 12, 16, 12, 30),
            18,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            60.0F,
            0.05F,
            VOID_DIAMOND_REPAIR,
            assetKey("voiddiamond")
    );

    public static final ArmorMaterial INFERNAL_DIAMOND = new ArmorMaterial(
            9000,
            defense(50, 50, 50, 50, 50),
            22,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            80.0F,
            0.1F,
            INFERNAL_DIAMOND_REPAIR,
            assetKey("infernaldiamond")
    );

    public static final ArmorMaterial INFERNAL = new ArmorMaterial(
            10000,
            defense(9000, 9000, 9000, 9000, 9000),
            25,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            9999.0F,
            0.2F,
            INFERNAL_REPAIR,
            assetKey("infernal")
    );

    private ArcanumArmorMaterials() {}

    private static Map<ArmorType, Integer> defense(int boots, int leggings, int chestplate, int helmet, int body) {
        Map<ArmorType, Integer> values = new EnumMap<>(ArmorType.class);
        values.put(ArmorType.BOOTS, boots);
        values.put(ArmorType.LEGGINGS, leggings);
        values.put(ArmorType.CHESTPLATE, chestplate);
        values.put(ArmorType.HELMET, helmet);
        values.put(ArmorType.BODY, body);
        return values;
    }

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }

    private static ResourceKey<EquipmentAsset> assetKey(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Arcanum.MODID, name));
    }
}
