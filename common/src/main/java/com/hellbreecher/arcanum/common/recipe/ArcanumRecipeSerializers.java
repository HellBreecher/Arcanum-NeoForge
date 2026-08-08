package com.hellbreecher.arcanum.common.recipe;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class ArcanumRecipeSerializers {
    public static final RegistryRegistrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistryPlatform.create(BuiltInRegistries.RECIPE_SERIALIZER, Reference.MODID);

    public static final RegistryEntry<RecipeSerializer<UpgradeCopyRecipe>> UPGRADE_COPY =
            RECIPE_SERIALIZERS.register("upgrade_copy", () -> UpgradeCopyRecipe.Serializer.INSTANCE);
    public static final RegistryEntry<RecipeSerializer<FermentingRecipe>> FERMENTING =
            RECIPE_SERIALIZERS.register("fermenting", () -> FermentingRecipe.Serializer.INSTANCE);

    private ArcanumRecipeSerializers() {}

    public static void bootstrap() { }
}
