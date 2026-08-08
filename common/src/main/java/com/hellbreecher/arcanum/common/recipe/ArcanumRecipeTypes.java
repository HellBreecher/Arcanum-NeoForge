package com.hellbreecher.arcanum.common.recipe;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeType;

public final class ArcanumRecipeTypes {
    public static final RegistryRegistrar<RecipeType<?>> RECIPE_TYPES = RegistryPlatform.create(BuiltInRegistries.RECIPE_TYPE, Reference.MODID);

    public static final RegistryEntry<RecipeType<FermentingRecipe>> FERMENTING =
            RECIPE_TYPES.register("fermenting",
                    () -> new RecipeType<>() {
                        public String toString() { return Identifier.fromNamespaceAndPath(Reference.MODID, "fermenting").toString(); }
                    });

    private ArcanumRecipeTypes() {}

    public static void bootstrap() { }
}
