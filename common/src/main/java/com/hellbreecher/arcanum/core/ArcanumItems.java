package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.BaseItem;
import com.hellbreecher.arcanum.common.items.HammerItem;
import com.hellbreecher.arcanum.common.items.InfernalCrystalItem;
import com.hellbreecher.arcanum.common.items.InfernalDiamondItem;
import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.items.ArcaneCodexItem;
import com.hellbreecher.arcanum.common.items.SpellPageItem;
import com.hellbreecher.arcanum.common.items.ForbiddenGrimoireItem;
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
    public static final RegistryEntry<Item> arcane_codex = ITEMS.register("arcane_codex", ArcaneCodexItem::new);
    public static final RegistryEntry<Item> forbidden_grimoire = ITEMS.register("forbidden_grimoire", ForbiddenGrimoireItem::new);
    public static final RegistryEntry<Item> codex_binding = ITEMS.register("codex_binding", BaseItem::new);
    public static final RegistryEntry<Item> grimoire_binding = ITEMS.register("grimoire_binding", BaseItem::new);
    public static final RegistryEntry<Item> spell_page = ITEMS.register("spell_page", BaseItem::new);
    public static final RegistryEntry<Item> blood_spell_page = ITEMS.register("blood_spell_page", BaseItem::new);
    public static final RegistryEntry<Item> renew_page = ITEMS.register("renew_page", id -> new SpellPageItem(id, 2));
    public static final RegistryEntry<Item> ember_ward_page = ITEMS.register("ember_ward_page", id -> new SpellPageItem(id, 3));
    public static final RegistryEntry<Item> aetherwing_page = ITEMS.register("aetherwing_page", id -> new SpellPageItem(id, 4));
    public static final RegistryEntry<Item> blink_page = ITEMS.register("blink_page", id -> new SpellPageItem(id, 5));
    public static final RegistryEntry<Item> infernal_chains_page = ITEMS.register("infernal_chains_page", id -> new SpellPageItem(id, 6));
    public static final RegistryEntry<Item> soulflare_page = ITEMS.register("soulflare_page", id -> new SpellPageItem(id, 7));
    public static final RegistryEntry<Item> ash_step_page = ITEMS.register("ash_step_page", id -> new SpellPageItem(id, 8));
    public static final RegistryEntry<Item> crystal_ward_page = ITEMS.register("crystal_ward_page", id -> new SpellPageItem(id, 9));
    public static final RegistryEntry<Item> dispel_page = ITEMS.register("dispel_page", id -> new SpellPageItem(id, 10));
    public static final RegistryEntry<Item> blood_lance_page = ITEMS.register("blood_lance_page", id -> new SpellPageItem(id, 13));
    public static final RegistryEntry<Item> crimson_feast_page = ITEMS.register("crimson_feast_page", id -> new SpellPageItem(id, 14));
    public static final RegistryEntry<Item> sanguine_ward_page = ITEMS.register("sanguine_ward_page", id -> new SpellPageItem(id, 15));

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
