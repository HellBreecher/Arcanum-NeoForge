package com.hellbreecher.arcanum.common.handler.datagen;

import com.google.common.collect.Maps;
import com.hellbreecher.arcanum.common.recipe.FermentingRecipe;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;

public class FermentingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStack resultStack;
    private final Ingredient base;
    private final Ingredient ingredient1;
    private final Ingredient ingredient2;
    private final Ingredient ingredient3;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = Maps.newLinkedHashMap();
    @Nullable
    private String group;

    private FermentingRecipeBuilder(
            RecipeCategory category,
            ItemStack resultStack,
            Ingredient base,
            Ingredient ingredient1,
            Ingredient ingredient2,
            Ingredient ingredient3,
            int cookingTime
    ) {
        this.category = category;
        this.resultStack = resultStack;
        this.base = base;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.cookingTime = cookingTime;
    }

    public static FermentingRecipeBuilder fermenting(
            Ingredient base,
            Ingredient ingredient1,
            Ingredient ingredient2,
            Ingredient ingredient3,
            RecipeCategory category,
            ItemLike result,
            int cookingTime
    ) {
        return new FermentingRecipeBuilder(
                category,
                new ItemStack(result),
                base,
                ingredient1,
                ingredient2,
                ingredient3,
                cookingTime
        );
    }

    @Override
    public FermentingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public FermentingRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.resultStack.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> recipeId) {
        ensureValid(recipeId);
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        FermentingRecipe recipe = new FermentingRecipe(
                Objects.requireNonNullElse(this.group, ""),
                this.base,
                this.ingredient1,
                this.ingredient2,
                this.ingredient3,
                this.resultStack,
                this.cookingTime
        );
        output.accept(
                recipeId,
                recipe,
                advancement.build(resolveLocation(recipeId).withPrefix("recipes/" + this.category.getFolderName() + "/"))
        );
    }

    private void ensureValid(ResourceKey<Recipe<?>> recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resolveLocation(recipeId));
        }
    }

    private static Identifier resolveLocation(ResourceKey<Recipe<?>> recipeId) {
        try {
            Object result = ResourceKey.class.getMethod("location").invoke(recipeId);
            if (result instanceof Identifier identifier) {
                return identifier;
            }
            if (result != null) {
                return Identifier.parse(result.toString());
            }
        } catch (ReflectiveOperationException ignored) {
            // Fall back to parsing when mappings don't expose location().
        }
        String text = recipeId.toString();
        int split = text.lastIndexOf(" / ");
        if (split != -1 && text.endsWith("]")) {
            return Identifier.parse(text.substring(split + 3, text.length() - 1));
        }
        return Identifier.parse(text);
    }
}
