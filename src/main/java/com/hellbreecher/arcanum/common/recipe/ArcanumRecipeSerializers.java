package com.hellbreecher.arcanum.common.recipe;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ArcanumRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Reference.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<UpgradeCopyRecipe>> UPGRADE_COPY =
            RECIPE_SERIALIZERS.register("upgrade_copy", UpgradeCopyRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FermentingRecipe>> FERMENTING =
            RECIPE_SERIALIZERS.register("fermenting", FermentingRecipe.Serializer::new);

    private ArcanumRecipeSerializers() {}

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
