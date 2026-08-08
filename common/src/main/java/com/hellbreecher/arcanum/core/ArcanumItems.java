package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.BaseItem;
import com.hellbreecher.arcanum.common.items.HammerItem;
import com.hellbreecher.arcanum.common.items.InfernalCrystalItem;
import com.hellbreecher.arcanum.common.items.InfernalDiamondItem;
import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumItems {
    // Create a Deferred Register to hold Items which will all be registered under the "arcanum" namespace
    public static final RegistryRegistrar<Item> ITEMS = RegistryPlatform.create(BuiltInRegistries.ITEM, Reference.MODID);

    //Ingots
    public static final RegistryEntry<Item> greensapphire = ITEMS.register("greensapphire", BaseItem::new);
   public static final RegistryEntry<Item> blooddiamond = ITEMS.register("blooddiamond", BaseItem::new);
    public static final RegistryEntry<Item> voiddiamond = ITEMS.register("voiddiamond", BaseItem::new);

    //Magical Ingots
    public static final RegistryEntry<Item> infernaldiamond = ITEMS.register("infernaldiamond", InfernalDiamondItem::new);
    public static final RegistryEntry<Item> infernalcrystal = ITEMS.register("infernalcrystal", InfernalCrystalItem::new);
    public static final RegistryEntry<Item> spellbook = ITEMS.register("spellbook", SpellbookItem::new);

    //Misc
    public static final RegistryEntry<Item> quartzstick = ITEMS.register("quartzstick", BaseItem::new);
    public static final RegistryEntry<Item> blooddiamondstick = ITEMS.register("blooddiamondstick", BaseItem::new);
    public static final RegistryEntry<Item> emptycan = ITEMS.register("emptycan", BaseItem::new);
    public static final RegistryEntry<Item> redcup = ITEMS.register("redcup", BaseItem::new);
    public static final RegistryEntry<Item> mountaindewmix = ITEMS.register("mountaindewmix", BaseItem::new);

    //Crafting Tools
    public static final RegistryEntry<Item> hammer = ITEMS.register("hammer", HammerItem::new);

    //Fuels
    public static final RegistryEntry<Item> greensapphirecoal = ITEMS.register("greensapphirecoal", BaseItem::new);

    public static void bootstrap() {
        // Class loading performs registration through the installed loader adapter.
    }

}
