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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArcanumFood {
    public static final DeferredRegister.Items FOODS = DeferredRegister.createItems(Reference.MODID);

    //Foods
    public static final DeferredItem<Item> crushedapple = FOODS.register("crushedapple", id -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(2.0F)
                    .build())
            .setId(ResourceKey.create(Registries.ITEM, id))));
    public static final DeferredItem<Item> toast = FOODS.register("toast", id -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(10)
                    .saturationModifier(10.0F)
                    .build())
            .setId(ResourceKey.create(Registries.ITEM, id))));

    //Drinks
    public static final DeferredItem<Item> cortonwine = FOODS.register("cortonwine", CortonWineItem::new);
    public static final DeferredItem<Item> unfermentedbeer = FOODS.register("unfermentedbeer", UnfermentedBeerItem::new);
    public static final DeferredItem<Item> fermentedbeer = FOODS.register("fermentedbeer", FermentedBeerItem::new);
    public static final DeferredItem<Item> applecider = FOODS.register("applecider", AppleCiderItem::new);
    public static final DeferredItem<Item> warmapplecider = FOODS.register("warmapplecider", WarmAppleCiderItem::new);

    //Powder Mix
    public static final DeferredItem<Item> mountaindew = FOODS.register("mountaindew", MountainDewItem::new);

    public static void register(IEventBus eventBus) {
        FOODS.register(eventBus);
    }
}
