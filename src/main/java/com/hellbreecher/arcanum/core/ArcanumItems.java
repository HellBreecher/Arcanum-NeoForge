package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.BaseItem;
import com.hellbreecher.arcanum.common.items.HammerItem;
import com.hellbreecher.arcanum.common.items.InfernalDiamondItem;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArcanumItems {
    // Create a Deferred Register to hold Items which will all be registered under the "arcanum" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MODID);

    //Ingots
    public static final DeferredItem<Item> greensapphire = ITEMS.register("greensapphire", BaseItem::new);
   public static final DeferredItem<Item> blooddiamond = ITEMS.register("blooddiamond", BaseItem::new);
    public static final DeferredItem<Item> voiddiamond = ITEMS.register("voiddiamond", BaseItem::new);

    //Magical Ingots
    public static final DeferredItem<Item> infernaldiamond = ITEMS.register("infernaldiamond", InfernalDiamondItem::new);

    //Misc
    public static final DeferredItem<Item> quartzstick = ITEMS.register("quartzstick", BaseItem::new);
    public static final DeferredItem<Item> blooddiamondstick = ITEMS.register("blooddiamondstick", BaseItem::new);
    public static final DeferredItem<Item> emptycan = ITEMS.register("emptycan", BaseItem::new);
    public static final DeferredItem<Item> redcup = ITEMS.register("redcup", BaseItem::new);
    public static final DeferredItem<Item> mountaindewmix = ITEMS.register("mountaindewmix", BaseItem::new);

    //Crafting Tools
    public static final DeferredItem<Item> hammer = ITEMS.register("hammer", HammerItem::new);

    //Fuels
    public static final DeferredItem<Item> greensapphirecoal = ITEMS.register("greensapphirecoal", BaseItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
