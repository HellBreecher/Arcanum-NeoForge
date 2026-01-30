package com.hellbreecher.arcanum.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class FermentingRecipe implements Recipe<FermentingRecipeInput> {
    private final String group;
    private final Ingredient base;
    private final Ingredient ingredient1;
    private final Ingredient ingredient2;
    private final Ingredient ingredient3;
    private final ItemStack result;
    private final int fermentTime;

    public FermentingRecipe(
            String group,
            Ingredient base,
            Ingredient ingredient1,
            Ingredient ingredient2,
            Ingredient ingredient3,
            ItemStack result,
            int fermentTime
    ) {
        this.group = group;
        this.base = base;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.result = result;
        this.fermentTime = fermentTime;
    }

    public Ingredient base() {
        return this.base;
    }

    public Ingredient ingredient1() {
        return this.ingredient1;
    }

    public Ingredient ingredient2() {
        return this.ingredient2;
    }

    public Ingredient ingredient3() {
        return this.ingredient3;
    }

    public int fermentTime() {
        return this.fermentTime;
    }

    @Override
    public RecipeSerializer<? extends Recipe<FermentingRecipeInput>> getSerializer() {
        return ArcanumRecipeSerializers.FERMENTING.get();
    }

    @Override
    public RecipeType<? extends Recipe<FermentingRecipeInput>> getType() {
        return ArcanumRecipeTypes.FERMENTING.get();
    }

    @Override
    public boolean matches(FermentingRecipeInput input, Level level) {
        if (!this.base.test(input.base())) {
            return false;
        }
        return matchesAnyOrder(input);
    }

    private boolean matchesAnyOrder(FermentingRecipeInput input) {
        ItemStack a = input.ingredient1();
        ItemStack b = input.ingredient2();
        ItemStack c = input.ingredient3();
        return (this.ingredient1.test(a) && this.ingredient2.test(b) && this.ingredient3.test(c))
                || (this.ingredient1.test(a) && this.ingredient2.test(c) && this.ingredient3.test(b))
                || (this.ingredient1.test(b) && this.ingredient2.test(a) && this.ingredient3.test(c))
                || (this.ingredient1.test(b) && this.ingredient2.test(c) && this.ingredient3.test(a))
                || (this.ingredient1.test(c) && this.ingredient2.test(a) && this.ingredient3.test(b))
                || (this.ingredient1.test(c) && this.ingredient2.test(b) && this.ingredient3.test(a));
    }

    @Override
    public ItemStack assemble(FermentingRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_FOOD;
    }

    public static class Serializer implements RecipeSerializer<FermentingRecipe> {
        private static final MapCodec<FermentingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.CODEC.fieldOf("ingredient1").forGetter(recipe -> recipe.ingredient1),
                                Ingredient.CODEC.fieldOf("ingredient2").forGetter(recipe -> recipe.ingredient2),
                                Ingredient.CODEC.fieldOf("ingredient3").forGetter(recipe -> recipe.ingredient3),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Codec.INT.optionalFieldOf("ferment_time", 1200).forGetter(recipe -> recipe.fermentTime)
                        )
                        .apply(builder, FermentingRecipe::new)
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> STREAM_CODEC = StreamCodec.of(
                FermentingRecipe.Serializer::toNetwork,
                FermentingRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<FermentingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static FermentingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ingredient1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ingredient2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ingredient3 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            int fermentTime = buffer.readVarInt();
            return new FermentingRecipe(group, base, ingredient1, ingredient2, ingredient3, result, fermentTime);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, FermentingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient1);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient2);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient3);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeVarInt(recipe.fermentTime);
        }
    }
}
