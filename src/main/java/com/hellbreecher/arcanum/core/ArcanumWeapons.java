package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.weapons.BloodDiamondBeatingStickItem;
import com.hellbreecher.arcanum.common.items.weapons.InfernalBeatingStickItem;
import com.hellbreecher.arcanum.common.items.weapons.InfernalDiamondSwordItem;
import com.hellbreecher.arcanum.common.items.weapons.InfernalSwordItem;
import com.hellbreecher.arcanum.common.items.weapons.InfernalWandItem;
import com.hellbreecher.arcanum.common.items.weapons.SapphireBeatingStickItem;
import com.hellbreecher.arcanum.common.items.weapons.VoidDiamondBeatingStickItem;
import com.hellbreecher.arcanum.common.lib.ArcanumToolMaterials;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArcanumWeapons {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MODID);

    public static final DeferredItem<Item> greensapphiresword = ITEMS.register(
            "greensapphiresword",
            id -> new Item(new Item.Properties()
                    .sword(ArcanumToolMaterials.GreenSapphireTool, 1.0F, -2.4F)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<Item> blooddiamondsword = ITEMS.register(
            "blooddiamondsword",
            id -> new Item(new Item.Properties()
                    .sword(ArcanumToolMaterials.BloodDiamondTool, 1.0F, -2.4F)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<Item> voiddiamondsword = ITEMS.register(
            "voiddiamondsword",
            id -> new Item(new Item.Properties()
                    .sword(ArcanumToolMaterials.VoidDiamondTool, 1.0F, -2.4F)
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final DeferredItem<Item> infernaldiamondsword = ITEMS.register("infernaldiamondsword", InfernalDiamondSwordItem::new);
    public static final DeferredItem<Item> infernalsword = ITEMS.register("infernalsword", InfernalSwordItem::new);

    public static final DeferredItem<Item> sapphirebeatingstick = ITEMS.register("sapphirebeatingstick", SapphireBeatingStickItem::new);
    public static final DeferredItem<Item> blooddiamondbeatingstick = ITEMS.register("blooddiamondbeatingstick", BloodDiamondBeatingStickItem::new);
    public static final DeferredItem<Item> voiddiamondbeatingstick = ITEMS.register("voiddiamondbeatingstick", VoidDiamondBeatingStickItem::new);
    public static final DeferredItem<Item> infernalbeatingstick = ITEMS.register("infernalbeatingstick", InfernalBeatingStickItem::new);
    public static final DeferredItem<Item> infernalwand = ITEMS.register("infernalwand", InfernalWandItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
