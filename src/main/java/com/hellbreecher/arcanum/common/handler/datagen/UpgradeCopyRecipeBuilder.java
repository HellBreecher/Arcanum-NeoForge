package com.hellbreecher.arcanum.common.handler.datagen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hellbreecher.arcanum.common.recipe.UpgradeCopyRecipe;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;

public class UpgradeCopyRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final RecipeCategory category;
    private final ItemStack resultStack;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private Ingredient baseIngredient;
    private boolean stripEnchantments;
    private boolean showNotification = true;

    private UpgradeCopyRecipeBuilder(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        this.items = items;
        this.category = category;
        this.resultStack = new ItemStack(result, count);
    }

    public static UpgradeCopyRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemLike result) {
        return new UpgradeCopyRecipeBuilder(items, category, result, 1);
    }

    public static UpgradeCopyRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        return new UpgradeCopyRecipeBuilder(items, category, result, count);
    }

    public UpgradeCopyRecipeBuilder base(ItemLike item) {
        this.baseIngredient = Ingredient.of(item);
        return this;
    }

    public UpgradeCopyRecipeBuilder base(TagKey<Item> tag) {
        this.baseIngredient = Ingredient.of(this.items.getOrThrow(tag));
        return this;
    }

    public UpgradeCopyRecipeBuilder stripEnchantments() {
        this.stripEnchantments = true;
        return this;
    }

    public UpgradeCopyRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(this.items.getOrThrow(tag)));
    }

    public UpgradeCopyRecipeBuilder define(Character symbol, ItemLike item) {
        return this.define(symbol, Ingredient.of(item));
    }

    public UpgradeCopyRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public UpgradeCopyRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    public UpgradeCopyRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public UpgradeCopyRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public UpgradeCopyRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public Item getResult() {
        return this.resultStack.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> recipeId) {
        ShapedRecipePattern pattern = ensureValid(recipeId);
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        UpgradeCopyRecipe recipe = new UpgradeCopyRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                pattern,
                this.resultStack,
                this.baseIngredient,
                this.stripEnchantments,
                this.showNotification
        );
        output.accept(
                recipeId,
                recipe,
                advancement.build(resolveLocation(recipeId).withPrefix("recipes/" + this.category.getFolderName() + "/"))
        );
    }

    private ShapedRecipePattern ensureValid(ResourceKey<Recipe<?>> recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resolveLocation(recipeId));
        }
        if (this.baseIngredient == null || this.baseIngredient.isEmpty()) {
            throw new IllegalStateException("No base ingredient defined for recipe " + resolveLocation(recipeId));
        }
        return ShapedRecipePattern.of(this.key, this.rows);
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
