package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.armor.BaseArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalDiamondArmorItem;
import com.hellbreecher.arcanum.common.lib.ArcanumArmorMaterials;
import com.hellbreecher.arcanum.common.lib.Reference;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumArmor {

    public static final RegistryRegistrar<Item> ITEMS = RegistryPlatform.create(BuiltInRegistries.ITEM, Reference.MODID);

    public static final RegistryEntry<Item> greensapphirehelmet = ITEMS.register(
            "greensapphirehelmet",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.HELMET, ArcanumItems.greensapphire.get(), id)
    );
    public static final RegistryEntry<Item> greensapphirechestplate = ITEMS.register(
            "greensapphirechestplate",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.CHESTPLATE, ArcanumItems.greensapphire.get(), id)
    );
    public static final RegistryEntry<Item> greensapphireleggings = ITEMS.register(
            "greensapphireleggings",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.LEGGINGS, ArcanumItems.greensapphire.get(), id)
    );
    public static final RegistryEntry<Item> greensapphireboots = ITEMS.register(
            "greensapphireboots",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.BOOTS, ArcanumItems.greensapphire.get(), id)
    );

    public static final RegistryEntry<Item> blooddiamondhelmet = ITEMS.register(
            "blooddiamondhelmet",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.HELMET, ArcanumItems.blooddiamond.get(), id)
    );
    public static final RegistryEntry<Item> blooddiamondchestplate = ITEMS.register(
            "blooddiamondchestplate",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.CHESTPLATE, ArcanumItems.blooddiamond.get(), id)
    );
    public static final RegistryEntry<Item> blooddiamondleggings = ITEMS.register(
            "blooddiamondleggings",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.LEGGINGS, ArcanumItems.blooddiamond.get(), id)
    );
    public static final RegistryEntry<Item> blooddiamondboots = ITEMS.register(
            "blooddiamondboots",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.BOOTS, ArcanumItems.blooddiamond.get(), id)
    );

    public static final RegistryEntry<Item> voiddiamondhelmet = ITEMS.register(
            "voiddiamondhelmet",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.HELMET, ArcanumItems.voiddiamond.get(), id)
    );
    public static final RegistryEntry<Item> voiddiamondchestplate = ITEMS.register(
            "voiddiamondchestplate",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.CHESTPLATE, ArcanumItems.voiddiamond.get(), id)
    );
    public static final RegistryEntry<Item> voiddiamondleggings = ITEMS.register(
            "voiddiamondleggings",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.LEGGINGS, ArcanumItems.voiddiamond.get(), id)
    );
    public static final RegistryEntry<Item> voiddiamondboots = ITEMS.register(
            "voiddiamondboots",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.BOOTS, ArcanumItems.voiddiamond.get(), id)
    );

    public static final RegistryEntry<Item> infernaldiamondhelmet = ITEMS.register(
            "infernaldiamondhelmet",
            id -> new InfernalDiamondArmorItem(id, ArmorType.HELMET)
    );
    public static final RegistryEntry<Item> infernaldiamondchestplate = ITEMS.register(
            "infernaldiamondchestplate",
            id -> new InfernalDiamondArmorItem(id, ArmorType.CHESTPLATE)
    );
    public static final RegistryEntry<Item> infernaldiamondleggings = ITEMS.register(
            "infernaldiamondleggings",
            id -> new InfernalDiamondArmorItem(id, ArmorType.LEGGINGS)
    );
    public static final RegistryEntry<Item> infernaldiamondboots = ITEMS.register(
            "infernaldiamondboots",
            id -> new InfernalDiamondArmorItem(id, ArmorType.BOOTS)
    );

    public static final RegistryEntry<Item> infernalhelmet = ITEMS.register(
            "infernalhelmet",
            id -> new InfernalArmorItem(id, ArmorType.HELMET)
    );
    public static final RegistryEntry<Item> infernalchestplate = ITEMS.register(
            "infernalchestplate",
            id -> new InfernalArmorItem(id, ArmorType.CHESTPLATE)
    );
    public static final RegistryEntry<Item> infernalleggings = ITEMS.register(
            "infernalleggings",
            id -> new InfernalArmorItem(id, ArmorType.LEGGINGS)
    );
    public static final RegistryEntry<Item> infernalboots = ITEMS.register(
            "infernalboots",
            id -> new InfernalArmorItem(id, ArmorType.BOOTS)
    );

    public static void bootstrap() { }
}
