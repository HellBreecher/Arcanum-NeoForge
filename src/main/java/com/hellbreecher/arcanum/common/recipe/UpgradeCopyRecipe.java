package com.hellbreecher.arcanum.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

public class UpgradeCopyRecipe implements CraftingRecipe {
    private final ShapedRecipePattern pattern;
    private final ItemStack result;
    private final String group;
    private final CraftingBookCategory category;
    private final boolean showNotification;
    private final Ingredient baseIngredient;
    private final boolean stripEnchantments;
    @Nullable
    private PlacementInfo placementInfo;

    public UpgradeCopyRecipe(
            String group,
            CraftingBookCategory category,
            ShapedRecipePattern pattern,
            ItemStack result,
            Ingredient baseIngredient,
            boolean stripEnchantments,
            boolean showNotification
    ) {
        this.group = group;
        this.category = category;
        this.pattern = pattern;
        this.result = result;
        this.baseIngredient = baseIngredient;
        this.stripEnchantments = stripEnchantments;
        this.showNotification = showNotification;
    }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return ArcanumRecipeSerializers.UPGRADE_COPY.get();
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.createFromOptionals(this.pattern.ingredients());
        }

        return this.placementInfo;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input) && findBaseStack(input) != null;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack base = findBaseStack(input);
        if (base == null || base.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack output = base.transmuteCopy(this.result.getItem(), this.result.getCount());
        if (this.stripEnchantments) {
            output.remove(DataComponents.ENCHANTMENTS);
            output.remove(DataComponents.STORED_ENCHANTMENTS);
        }

        if (output.isDamageableItem() && output.getDamageValue() >= output.getMaxDamage()) {
            output.setDamageValue(Math.max(0, output.getMaxDamage() - 1));
        }

        return output;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapedCraftingRecipeDisplay(
                        this.pattern.width(),
                        this.pattern.height(),
                        this.pattern.ingredients()
                                .stream()
                                .map(ingredient -> ingredient.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE))
                                .toList(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    private ItemStack findBaseStack(CraftingInput input) {
        ItemStack found = null;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && this.baseIngredient.test(stack)) {
                if (found != null) {
                    return null;
                }
                found = stack;
            }
        }

        return found;
    }

    public static class Serializer implements RecipeSerializer<UpgradeCopyRecipe> {
        private static final MapCodec<UpgradeCopyRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                CraftingBookCategory.CODEC.fieldOf("category")
                                        .orElse(CraftingBookCategory.MISC)
                                        .forGetter(recipe -> recipe.category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.baseIngredient),
                                Codec.BOOL.optionalFieldOf("strip_enchantments", false)
                                        .forGetter(recipe -> recipe.stripEnchantments),
                                Codec.BOOL.optionalFieldOf("show_notification", true)
                                        .forGetter(recipe -> recipe.showNotification)
                        )
                        .apply(builder, UpgradeCopyRecipe::new)
        );
        private static final StreamCodec<RegistryFriendlyByteBuf, UpgradeCopyRecipe> STREAM_CODEC = StreamCodec.of(
                UpgradeCopyRecipe.Serializer::toNetwork,
                UpgradeCopyRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<UpgradeCopyRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, UpgradeCopyRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static UpgradeCopyRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            boolean stripEnchantments = buffer.readBoolean();
            boolean showNotification = buffer.readBoolean();
            return new UpgradeCopyRecipe(group, category, pattern, result, base, stripEnchantments, showNotification);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, UpgradeCopyRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.baseIngredient);
            buffer.writeBoolean(recipe.stripEnchantments);
            buffer.writeBoolean(recipe.showNotification);
        }
    }
}
