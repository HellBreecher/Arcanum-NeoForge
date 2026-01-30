package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.armor.BaseArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalDiamondArmorItem;
import com.hellbreecher.arcanum.common.lib.ArcanumArmorMaterials;
import com.hellbreecher.arcanum.common.lib.Reference;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArcanumArmor {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MODID);

    public static final DeferredItem<Item> greensapphirehelmet = ITEMS.register(
            "greensapphirehelmet",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.HELMET, ArcanumItems.greensapphire.get(), id)
    );
    public static final DeferredItem<Item> greensapphirechestplate = ITEMS.register(
            "greensapphirechestplate",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.CHESTPLATE, ArcanumItems.greensapphire.get(), id)
    );
    public static final DeferredItem<Item> greensapphireleggings = ITEMS.register(
            "greensapphireleggings",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.LEGGINGS, ArcanumItems.greensapphire.get(), id)
    );
    public static final DeferredItem<Item> greensapphireboots = ITEMS.register(
            "greensapphireboots",
            id -> new BaseArmorItem(ArcanumArmorMaterials.GREEN_SAPPHIRE, ArmorType.BOOTS, ArcanumItems.greensapphire.get(), id)
    );

    public static final DeferredItem<Item> blooddiamondhelmet = ITEMS.register(
            "blooddiamondhelmet",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.HELMET, ArcanumItems.blooddiamond.get(), id)
    );
    public static final DeferredItem<Item> blooddiamondchestplate = ITEMS.register(
            "blooddiamondchestplate",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.CHESTPLATE, ArcanumItems.blooddiamond.get(), id)
    );
    public static final DeferredItem<Item> blooddiamondleggings = ITEMS.register(
            "blooddiamondleggings",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.LEGGINGS, ArcanumItems.blooddiamond.get(), id)
    );
    public static final DeferredItem<Item> blooddiamondboots = ITEMS.register(
            "blooddiamondboots",
            id -> new BaseArmorItem(ArcanumArmorMaterials.BLOOD_DIAMOND, ArmorType.BOOTS, ArcanumItems.blooddiamond.get(), id)
    );

    public static final DeferredItem<Item> voiddiamondhelmet = ITEMS.register(
            "voiddiamondhelmet",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.HELMET, ArcanumItems.voiddiamond.get(), id)
    );
    public static final DeferredItem<Item> voiddiamondchestplate = ITEMS.register(
            "voiddiamondchestplate",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.CHESTPLATE, ArcanumItems.voiddiamond.get(), id)
    );
    public static final DeferredItem<Item> voiddiamondleggings = ITEMS.register(
            "voiddiamondleggings",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.LEGGINGS, ArcanumItems.voiddiamond.get(), id)
    );
    public static final DeferredItem<Item> voiddiamondboots = ITEMS.register(
            "voiddiamondboots",
            id -> new BaseArmorItem(ArcanumArmorMaterials.VOID_DIAMOND, ArmorType.BOOTS, ArcanumItems.voiddiamond.get(), id)
    );

    public static final DeferredItem<Item> infernaldiamondhelmet = ITEMS.register(
            "infernaldiamondhelmet",
            id -> new InfernalDiamondArmorItem(id, ArmorType.HELMET)
    );
    public static final DeferredItem<Item> infernaldiamondchestplate = ITEMS.register(
            "infernaldiamondchestplate",
            id -> new InfernalDiamondArmorItem(id, ArmorType.CHESTPLATE)
    );
    public static final DeferredItem<Item> infernaldiamondleggings = ITEMS.register(
            "infernaldiamondleggings",
            id -> new InfernalDiamondArmorItem(id, ArmorType.LEGGINGS)
    );
    public static final DeferredItem<Item> infernaldiamondboots = ITEMS.register(
            "infernaldiamondboots",
            id -> new InfernalDiamondArmorItem(id, ArmorType.BOOTS)
    );

    public static final DeferredItem<Item> infernalhelmet = ITEMS.register(
            "infernalhelmet",
            id -> new InfernalArmorItem(id, ArmorType.HELMET)
    );
    public static final DeferredItem<Item> infernalchestplate = ITEMS.register(
            "infernalchestplate",
            id -> new InfernalArmorItem(id, ArmorType.CHESTPLATE)
    );
    public static final DeferredItem<Item> infernalleggings = ITEMS.register(
            "infernalleggings",
            id -> new InfernalArmorItem(id, ArmorType.LEGGINGS)
    );
    public static final DeferredItem<Item> infernalboots = ITEMS.register(
            "infernalboots",
            id -> new InfernalArmorItem(id, ArmorType.BOOTS)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
