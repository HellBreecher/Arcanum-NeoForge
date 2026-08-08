package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.tools.ArcWrenchItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalAxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalDiamondAxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalDiamondHoeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalDiamondPickaxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalDiamondShearsItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalDiamondShovelItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalHoeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalPickaxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalShearsItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalShovelItem;
import com.hellbreecher.arcanum.common.lib.ArcanumToolMaterials;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.item.HoeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumTools {
    public static final RegistryRegistrar<Item> ITEMS = RegistryPlatform.create(BuiltInRegistries.ITEM, Reference.MODID);

    public static final RegistryEntry<Item> greensapphirepickaxe = ITEMS.register(
            "greensapphirepickaxe",
            id -> new Item(new Item.Properties()
                    .pickaxe(ArcanumToolMaterials.GreenSapphireTool, 1.0F, -2.8F)
                    .repairable(ArcanumItems.greensapphire.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<Item> blooddiamondpickaxe = ITEMS.register(
            "blooddiamondpickaxe",
            id -> new Item(new Item.Properties()
                    .pickaxe(ArcanumToolMaterials.BloodDiamondTool, 1.0F, -2.8F)
                    .repairable(ArcanumItems.blooddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<Item> voiddiamondpickaxe = ITEMS.register(
            "voiddiamondpickaxe",
            id -> new Item(new Item.Properties()
                    .pickaxe(ArcanumToolMaterials.VoidDiamondTool, 1.0F, -2.8F)
                    .repairable(ArcanumItems.voiddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<Item> infernaldiamondpickaxe = ITEMS.register("infernaldiamondpickaxe", InfernalDiamondPickaxeItem::new);
    public static final RegistryEntry<Item> infernalpickaxe = ITEMS.register("infernalpickaxe", InfernalPickaxeItem::new);

    public static final RegistryEntry<AxeItem> greensapphireaxe = ITEMS.register(
            "greensapphireaxe",
            id -> new AxeItem(ArcanumToolMaterials.GreenSapphireTool, 6.0F, -3.1F, new Item.Properties()
                    .repairable(ArcanumItems.greensapphire.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<AxeItem> blooddiamondaxe = ITEMS.register(
            "blooddiamondaxe",
            id -> new AxeItem(ArcanumToolMaterials.BloodDiamondTool, 6.0F, -3.1F, new Item.Properties()
                    .repairable(ArcanumItems.blooddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<AxeItem> voiddiamondaxe = ITEMS.register(
            "voiddiamondaxe",
            id -> new AxeItem(ArcanumToolMaterials.VoidDiamondTool, 6.0F, -3.1F, new Item.Properties()
                    .repairable(ArcanumItems.voiddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<AxeItem> infernaldiamondaxe = ITEMS.register("infernaldiamondaxe", InfernalDiamondAxeItem::new);
    public static final RegistryEntry<AxeItem> infernalaxe = ITEMS.register("infernalaxe", InfernalAxeItem::new);

    public static final RegistryEntry<ShovelItem> greensapphireshovel = ITEMS.register(
            "greensapphireshovel",
            id -> new ShovelItem(ArcanumToolMaterials.GreenSapphireTool, 1.5F, -3.0F, new Item.Properties()
                    .repairable(ArcanumItems.greensapphire.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<ShovelItem> blooddiamondshovel = ITEMS.register(
            "blooddiamondshovel",
            id -> new ShovelItem(ArcanumToolMaterials.BloodDiamondTool, 1.5F, -3.0F, new Item.Properties()
                    .repairable(ArcanumItems.blooddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<ShovelItem> voiddiamondshovel = ITEMS.register(
            "voiddiamondshovel",
            id -> new ShovelItem(ArcanumToolMaterials.VoidDiamondTool, 1.5F, -3.0F, new Item.Properties()
                    .repairable(ArcanumItems.voiddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<ShovelItem> infernaldiamondshovel = ITEMS.register("infernaldiamondshovel", InfernalDiamondShovelItem::new);
    public static final RegistryEntry<ShovelItem> infernalshovel = ITEMS.register("infernalshovel", InfernalShovelItem::new);

    public static final RegistryEntry<ShearsItem> greensapphireshears = ITEMS.register(
            "greensapphireshears",
            id -> new ShearsItem(new Item.Properties()
                    .durability(1500)
                    .repairable(ArcanumItems.greensapphire.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<ShearsItem> blooddiamondshears = ITEMS.register(
            "blooddiamondshears",
            id -> new ShearsItem(new Item.Properties()
                    .durability(3000)
                    .repairable(ArcanumItems.blooddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<ShearsItem> voiddiamondshears = ITEMS.register(
            "voiddiamondshears",
            id -> new ShearsItem(new Item.Properties()
                    .durability(6000)
                    .repairable(ArcanumItems.voiddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<ShearsItem> infernaldiamondshears = ITEMS.register("infernaldiamondshears", InfernalDiamondShearsItem::new);
    public static final RegistryEntry<ShearsItem> infernalshears = ITEMS.register("infernalshears", InfernalShearsItem::new);

    public static final RegistryEntry<HoeItem> greensapphirehoe = ITEMS.register(
            "greensapphirehoe",
            id -> new HoeItem(ArcanumToolMaterials.GreenSapphireTool, -2.0F, -3.0F, new Item.Properties()
                    .repairable(ArcanumItems.greensapphire.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<HoeItem> blooddiamondhoe = ITEMS.register(
            "blooddiamondhoe",
            id -> new HoeItem(ArcanumToolMaterials.BloodDiamondTool, -2.0F, -3.0F, new Item.Properties()
                    .repairable(ArcanumItems.blooddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<HoeItem> voiddiamondhoe = ITEMS.register(
            "voiddiamondhoe",
            id -> new HoeItem(ArcanumToolMaterials.VoidDiamondTool, -2.0F, -3.0F, new Item.Properties()
                    .repairable(ArcanumItems.voiddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<HoeItem> infernaldiamondhoe = ITEMS.register("infernaldiamondhoe", InfernalDiamondHoeItem::new);
    public static final RegistryEntry<HoeItem> infernalhoe = ITEMS.register("infernalhoe", InfernalHoeItem::new);

    public static final RegistryEntry<Item> arcwrench = ITEMS.register("arcwrench", ArcWrenchItem::new);

    public static void bootstrap() { }
}
