package com.hellbreecher.arcanum.common.recipe;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcanumRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Reference.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<FermentingRecipe>> FERMENTING =
            RECIPE_TYPES.register("fermenting",
                    () -> RecipeType.simple(Identifier.fromNamespaceAndPath(Reference.MODID, "fermenting")));

    private ArcanumRecipeTypes() {}

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}
