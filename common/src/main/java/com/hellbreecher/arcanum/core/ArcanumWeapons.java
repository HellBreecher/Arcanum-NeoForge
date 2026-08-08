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
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumWeapons {
    public static final RegistryRegistrar<Item> ITEMS = RegistryPlatform.create(BuiltInRegistries.ITEM, Reference.MODID);

    public static final RegistryEntry<Item> greensapphiresword = ITEMS.register(
            "greensapphiresword",
            id -> new Item(new Item.Properties()
                    .sword(ArcanumToolMaterials.GreenSapphireTool, 1.0F, -2.4F)
                    .repairable(ArcanumItems.greensapphire.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<Item> blooddiamondsword = ITEMS.register(
            "blooddiamondsword",
            id -> new Item(new Item.Properties()
                    .sword(ArcanumToolMaterials.BloodDiamondTool, 1.0F, -2.4F)
                    .repairable(ArcanumItems.blooddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<Item> voiddiamondsword = ITEMS.register(
            "voiddiamondsword",
            id -> new Item(new Item.Properties()
                    .sword(ArcanumToolMaterials.VoidDiamondTool, 1.0F, -2.4F)
                    .repairable(ArcanumItems.voiddiamond.get())
                    .setId(ResourceKey.create(Registries.ITEM, id)))
    );
    public static final RegistryEntry<Item> infernaldiamondsword = ITEMS.register("infernaldiamondsword", InfernalDiamondSwordItem::new);
    public static final RegistryEntry<Item> infernalsword = ITEMS.register("infernalsword", InfernalSwordItem::new);

    public static final RegistryEntry<Item> sapphirebeatingstick = ITEMS.register("sapphirebeatingstick", SapphireBeatingStickItem::new);
    public static final RegistryEntry<Item> blooddiamondbeatingstick = ITEMS.register("blooddiamondbeatingstick", BloodDiamondBeatingStickItem::new);
    public static final RegistryEntry<Item> voiddiamondbeatingstick = ITEMS.register("voiddiamondbeatingstick", VoidDiamondBeatingStickItem::new);
    public static final RegistryEntry<Item> infernalbeatingstick = ITEMS.register("infernalbeatingstick", InfernalBeatingStickItem::new);
    public static final RegistryEntry<Item> infernalwand = ITEMS.register("infernalwand", InfernalWandItem::new);

    public static void bootstrap() { }
}
