package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.items.food.AppleCiderItem;
import com.hellbreecher.arcanum.common.items.food.CortonWineItem;
import com.hellbreecher.arcanum.common.items.food.FermentedBeerItem;
import com.hellbreecher.arcanum.common.items.food.MountainDewItem;
import com.hellbreecher.arcanum.common.items.food.UnfermentedBeerItem;
import com.hellbreecher.arcanum.common.items.food.WarmAppleCiderItem;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumFood {
    public static final RegistryRegistrar<Item> FOODS = RegistryPlatform.create(BuiltInRegistries.ITEM, Reference.MODID);

    //Foods
    public static final RegistryEntry<Item> crushedapple = FOODS.register("crushedapple", id -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(2.0F)
                    .build())
            .setId(ResourceKey.create(Registries.ITEM, id))));
    public static final RegistryEntry<Item> toast = FOODS.register("toast", id -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(10)
                    .saturationModifier(10.0F)
                    .build())
            .setId(ResourceKey.create(Registries.ITEM, id))));

    //Drinks
    public static final RegistryEntry<Item> cortonwine = FOODS.register("cortonwine", CortonWineItem::new);
    public static final RegistryEntry<Item> unfermentedbeer = FOODS.register("unfermentedbeer", UnfermentedBeerItem::new);
    public static final RegistryEntry<Item> fermentedbeer = FOODS.register("fermentedbeer", FermentedBeerItem::new);
    public static final RegistryEntry<Item> applecider = FOODS.register("applecider", AppleCiderItem::new);
    public static final RegistryEntry<Item> warmapplecider = FOODS.register("warmapplecider", WarmAppleCiderItem::new);

    //Powder Mix
    public static final RegistryEntry<Item> mountaindew = FOODS.register("mountaindew", MountainDewItem::new);

    public static void bootstrap() { }
}
