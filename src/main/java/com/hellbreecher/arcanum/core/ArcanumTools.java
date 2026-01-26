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
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArcanumTools {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MODID);

    public static final DeferredItem<Item> greensapphirepickaxe = ITEMS.register(
            "greensapphirepickaxe",
            id -> new Item(new Item.Properties()
                    .pickaxe(ArcanumToolMaterials.GreenSapphireTool, 1.0F, -2.8F)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<Item> blooddiamondpickaxe = ITEMS.register(
            "blooddiamondpickaxe",
            id -> new Item(new Item.Properties()
                    .pickaxe(ArcanumToolMaterials.BloodDiamondTool, 1.0F, -2.8F)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<Item> voiddiamondpickaxe = ITEMS.register(
            "voiddiamondpickaxe",
            id -> new Item(new Item.Properties()
                    .pickaxe(ArcanumToolMaterials.VoidDiamondTool, 1.0F, -2.8F)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<Item> infernaldiamondpickaxe = ITEMS.register("infernaldiamondpickaxe", InfernalDiamondPickaxeItem::new);
    public static final DeferredItem<Item> infernalpickaxe = ITEMS.register("infernalpickaxe", InfernalPickaxeItem::new);

    public static final DeferredItem<AxeItem> greensapphireaxe = ITEMS.register(
            "greensapphireaxe",
            id -> new AxeItem(ArcanumToolMaterials.GreenSapphireTool, 6.0F, -3.1F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<AxeItem> blooddiamondaxe = ITEMS.register(
            "blooddiamondaxe",
            id -> new AxeItem(ArcanumToolMaterials.BloodDiamondTool, 6.0F, -3.1F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<AxeItem> voiddiamondaxe = ITEMS.register(
            "voiddiamondaxe",
            id -> new AxeItem(ArcanumToolMaterials.VoidDiamondTool, 6.0F, -3.1F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<AxeItem> infernaldiamondaxe = ITEMS.register("infernaldiamondaxe", InfernalDiamondAxeItem::new);
    public static final DeferredItem<AxeItem> infernalaxe = ITEMS.register("infernalaxe", InfernalAxeItem::new);

    public static final DeferredItem<ShovelItem> greensapphireshovel = ITEMS.register(
            "greensapphireshovel",
            id -> new ShovelItem(ArcanumToolMaterials.GreenSapphireTool, 1.5F, -3.0F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<ShovelItem> blooddiamondshovel = ITEMS.register(
            "blooddiamondshovel",
            id -> new ShovelItem(ArcanumToolMaterials.BloodDiamondTool, 1.5F, -3.0F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<ShovelItem> voiddiamondshovel = ITEMS.register(
            "voiddiamondshovel",
            id -> new ShovelItem(ArcanumToolMaterials.VoidDiamondTool, 1.5F, -3.0F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<ShovelItem> infernaldiamondshovel = ITEMS.register("infernaldiamondshovel", InfernalDiamondShovelItem::new);
    public static final DeferredItem<ShovelItem> infernalshovel = ITEMS.register("infernalshovel", InfernalShovelItem::new);

    public static final DeferredItem<ShearsItem> greensapphireshears = ITEMS.register(
            "greensapphireshears",
            id -> new ShearsItem(new Item.Properties()
                    .durability(1500)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<ShearsItem> blooddiamondshears = ITEMS.register(
            "blooddiamondshears",
            id -> new ShearsItem(new Item.Properties()
                    .durability(3000)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<ShearsItem> voiddiamondshears = ITEMS.register(
            "voiddiamondshears",
            id -> new ShearsItem(new Item.Properties()
                    .durability(6000)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<ShearsItem> infernaldiamondshears = ITEMS.register("infernaldiamondshears", InfernalDiamondShearsItem::new);
    public static final DeferredItem<ShearsItem> infernalshears = ITEMS.register("infernalshears", InfernalShearsItem::new);

    public static final DeferredItem<HoeItem> greensapphirehoe = ITEMS.register(
            "greensapphirehoe",
            id -> new HoeItem(ArcanumToolMaterials.GreenSapphireTool, -2.0F, -3.0F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<HoeItem> blooddiamondhoe = ITEMS.register(
            "blooddiamondhoe",
            id -> new HoeItem(ArcanumToolMaterials.BloodDiamondTool, -2.0F, -3.0F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<HoeItem> voiddiamondhoe = ITEMS.register(
            "voiddiamondhoe",
            id -> new HoeItem(ArcanumToolMaterials.VoidDiamondTool, -2.0F, -3.0F, new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<HoeItem> infernaldiamondhoe = ITEMS.register("infernaldiamondhoe", InfernalDiamondHoeItem::new);
    public static final DeferredItem<HoeItem> infernalhoe = ITEMS.register("infernalhoe", InfernalHoeItem::new);

    public static final DeferredItem<Item> arcwrench = ITEMS.register("arcwrench", ArcWrenchItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
