package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.core.Arcanum;
import com.hellbreecher.arcanum.core.ArcanumArmor;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.hellbreecher.arcanum.core.ArcanumFood;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumTools;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import com.hellbreecher.arcanum.common.conditions.ConfigBooleanCondition;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public final class RecipeDataGen extends RecipeProvider {
    private RecipeDataGen(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        TagKey<Item> woodenRods = itemTag("forge:rods/wooden");
        TagKey<Item> quartzGems = itemTag("forge:gems/quartz");
        RecipeOutput upgradeOutput = output.withConditions(new ConfigBooleanCondition("recipes.enableUpgrades"));
        RecipeOutput fermenterOutput = output.withConditions(new ConfigBooleanCondition("recipes.enableFermenter"));
        RecipeOutput smeltingOutput = output.withConditions(new ConfigBooleanCondition("recipes.enableSmelting"));
        RecipeOutput furnaceBlocksOutput = output.withConditions(new ConfigBooleanCondition("recipes.enableFurnaceBlocks"));
        RecipeOutput generatorOutput = output.withConditions(new ConfigBooleanCondition("recipes.enableGenerators"));

        // Ingots and blocks
        shapeless(items, RecipeCategory.MISC, ArcanumItems.greensapphire.get(), 9)
                .requires(ArcanumBlocks.greensapphire_block.get())
                .unlockedBy("has_greensapphire_block", has(ArcanumBlocks.greensapphire_block.get()))
                .save(output, recipeId("crafting_greensapphire"));
        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.greensapphire_block.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ArcanumItems.greensapphire.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphireblock"));

        shapeless(items, RecipeCategory.MISC, ArcanumItems.blooddiamond.get(), 9)
                .requires(ArcanumBlocks.blooddiamond_block.get())
                .unlockedBy("has_blooddiamond_block", has(ArcanumBlocks.blooddiamond_block.get()))
                .save(output, recipeId("crafting_blooddiamond"));
        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.blooddiamond_block.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ArcanumItems.blooddiamond.get())
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(output, recipeId("crafting_blooddiamondblock"));

        shapeless(items, RecipeCategory.MISC, ArcanumItems.voiddiamond.get(), 9)
                .requires(ArcanumBlocks.voiddiamond_block.get())
                .unlockedBy("has_voiddiamond_block", has(ArcanumBlocks.voiddiamond_block.get()))
                .save(output, recipeId("crafting_voiddiamond"));
        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.voiddiamond_block.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ArcanumItems.voiddiamond.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(output, recipeId("crafting_voiddiamondblock"));

        // Infernal diamond
        shaped(items, RecipeCategory.MISC, ArcanumItems.infernaldiamond.get())
                .pattern("XYX")
                .pattern("YZY")
                .pattern("XYX")
                .define('X', ArcanumBlocks.voiddiamond_block.get())
                .define('Y', ArcanumBlocks.blooddiamond_block.get())
                .define('Z', Items.BLAZE_ROD)
                .unlockedBy("has_voiddiamond_block", has(ArcanumBlocks.voiddiamond_block.get()))
                .save(output, recipeId("crafting_infernaldiamond"));

        // Sticks and misc
        shaped(items, RecipeCategory.MISC, ArcanumItems.quartzstick.get(), 4)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', Items.QUARTZ)
                .define('Y', woodenRods)
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .save(output, recipeId("crafting_quartzstick"));

        shaped(items, RecipeCategory.MISC, ArcanumItems.blooddiamondstick.get(), 4)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(output, recipeId("crafting_blooddiamondstick"));

        shaped(items, RecipeCategory.MISC, ArcanumItems.hammer.get())
                .pattern(" X ")
                .pattern(" YX")
                .pattern("Y  ")
                .define('X', Items.IRON_INGOT)
                .define('Y', woodenRods)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output, recipeId("crafting_hammer"));

        shaped(items, RecipeCategory.TOOLS, ArcanumTools.arcwrench.get())
                .pattern(" X ")
                .pattern(" YX")
                .pattern("Y  ")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_arcwrench"));

        // Greensapphire tools
        shaped(items, RecipeCategory.TOOLS, ArcanumTools.greensapphirepickaxe.get())
                .pattern("XXX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', woodenRods)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphirepickaxe"));

        shaped(items, RecipeCategory.TOOLS, ArcanumTools.greensapphireaxe.get())
                .pattern("XX ")
                .pattern("XY ")
                .pattern(" Y ")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', woodenRods)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphireaxe"));

        shaped(items, RecipeCategory.TOOLS, ArcanumTools.greensapphirehoe.get())
                .pattern("XX ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', woodenRods)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphirehoe"));

        shaped(items, RecipeCategory.TOOLS, ArcanumTools.greensapphireshovel.get())
                .pattern("X")
                .pattern("Y")
                .pattern("Y")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', woodenRods)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphireshovel"));

        shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.greensapphiresword.get())
                .pattern("X")
                .pattern("X")
                .pattern("Y")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', woodenRods)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphiresword"));

        shaped(items, RecipeCategory.TOOLS, ArcanumTools.greensapphireshears.get())
                .pattern("X ")
                .pattern(" Y")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', ArcanumItems.greensapphire.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphireshears"));

        // Greensapphire armor
        shaped(items, RecipeCategory.COMBAT, ArcanumArmor.greensapphirehelmet.get())
                .pattern("XXX")
                .pattern("X X")
                .define('X', ArcanumItems.greensapphire.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphirehelmet"));
        shaped(items, RecipeCategory.COMBAT, ArcanumArmor.greensapphirechestplate.get())
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ArcanumItems.greensapphire.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphirechestplate"));
        shaped(items, RecipeCategory.COMBAT, ArcanumArmor.greensapphireleggings.get())
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .define('X', ArcanumItems.greensapphire.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphirelegs"));
        shaped(items, RecipeCategory.COMBAT, ArcanumArmor.greensapphireboots.get())
                .pattern("X X")
                .pattern("X X")
                .define('X', ArcanumItems.greensapphire.get())
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphireboots"));

        // Sapphire beating stick
        shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.sapphirebeatingstick.get())
                .pattern("XYX")
                .pattern("XYX")
                .pattern("XYX")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', woodenRods)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_sapphirebeatingstick"));


        // Blood diamond tools
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.blooddiamondpickaxe.get())
                .pattern("XZX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .define('Z', Items.DIAMOND_PICKAXE)
                .base(Items.DIAMOND_PICKAXE)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondpickaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.blooddiamondaxe.get())
                .pattern("XZ ")
                .pattern("XY ")
                .pattern(" Y ")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .define('Z', Items.DIAMOND_AXE)
                .base(Items.DIAMOND_AXE)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.blooddiamondhoe.get())
                .pattern("XZ ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .define('Z', Items.DIAMOND_HOE)
                .base(Items.DIAMOND_HOE)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondhoe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.blooddiamondshovel.get())
                .pattern("X")
                .pattern("Z")
                .pattern("Y")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .define('Z', Items.DIAMOND_SHOVEL)
                .base(Items.DIAMOND_SHOVEL)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondshovel"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.blooddiamondsword.get())
                .pattern("X")
                .pattern("Z")
                .pattern("Y")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .define('Z', Items.DIAMOND_SWORD)
                .base(Items.DIAMOND_SWORD)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondsword"));

        shaped(items, RecipeCategory.TOOLS, ArcanumTools.blooddiamondshears.get())
                .pattern("X ")
                .pattern(" Y")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', quartzGems)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(output, recipeId("crafting_blooddiamondshears"));

        // Blood diamond armor
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.blooddiamondhelmet.get())
                .pattern("XYX")
                .pattern("XZX")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', quartzGems)
                .define('Z', Items.DIAMOND_HELMET)
                .base(Items.DIAMOND_HELMET)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondhelmet"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.blooddiamondchestplate.get())
                .pattern("Y Y")
                .pattern("XZX")
                .pattern("XXX")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', quartzGems)
                .define('Z', Items.DIAMOND_CHESTPLATE)
                .base(Items.DIAMOND_CHESTPLATE)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondchestplate"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.blooddiamondleggings.get())
                .pattern("YXY")
                .pattern("XZX")
                .pattern("X X")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', quartzGems)
                .define('Z', Items.DIAMOND_LEGGINGS)
                .base(Items.DIAMOND_LEGGINGS)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondlegs"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.blooddiamondboots.get())
                .pattern("YZY")
                .pattern("X X")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', quartzGems)
                .define('Z', Items.DIAMOND_BOOTS)
                .base(Items.DIAMOND_BOOTS)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_blooddiamondboots"));

        // Blood diamond beating stick
        shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.blooddiamondbeatingstick.get())
                .pattern("XYX")
                .pattern("XYX")
                .pattern("XYX")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('Y', ArcanumItems.quartzstick.get())
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(output, recipeId("crafting_blooddiamondbeatingstick"));

        // Void diamond tools
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.voiddiamondpickaxe.get())
                .pattern("XZX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamondstick.get())
                .define('Z', ArcanumTools.blooddiamondpickaxe.get())
                .base(ArcanumTools.blooddiamondpickaxe.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondpickaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.voiddiamondaxe.get())
                .pattern("XZ ")
                .pattern("XY ")
                .pattern(" Y ")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamondstick.get())
                .define('Z', ArcanumTools.blooddiamondaxe.get())
                .base(ArcanumTools.blooddiamondaxe.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.voiddiamondhoe.get())
                .pattern("XZ ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamondstick.get())
                .define('Z', ArcanumTools.blooddiamondhoe.get())
                .base(ArcanumTools.blooddiamondhoe.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondhoe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.voiddiamondshovel.get())
                .pattern("X")
                .pattern("Z")
                .pattern("Y")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamondstick.get())
                .define('Z', ArcanumTools.blooddiamondshovel.get())
                .base(ArcanumTools.blooddiamondshovel.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondshovel"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.voiddiamondsword.get())
                .pattern("X")
                .pattern("Z")
                .pattern("Y")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamondstick.get())
                .define('Z', ArcanumWeapons.blooddiamondsword.get())
                .base(ArcanumWeapons.blooddiamondsword.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondsword"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.voiddiamondshears.get())
                .pattern("XZ")
                .pattern(" Y")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamond.get())
                .define('Z', ArcanumTools.blooddiamondshears.get())
                .base(ArcanumTools.blooddiamondshears.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondshears"));

        // Void diamond armor
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.voiddiamondhelmet.get())
                .pattern("XYX")
                .pattern("XZX")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamond.get())
                .define('Z', ArcanumArmor.blooddiamondhelmet.get())
                .base(ArcanumArmor.blooddiamondhelmet.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondhelmet"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.voiddiamondchestplate.get())
                .pattern("Y Y")
                .pattern("XZX")
                .pattern("XXX")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamond.get())
                .define('Z', ArcanumArmor.blooddiamondchestplate.get())
                .base(ArcanumArmor.blooddiamondchestplate.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondchest"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.voiddiamondleggings.get())
                .pattern("YXY")
                .pattern("XZX")
                .pattern("X X")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamond.get())
                .define('Z', ArcanumArmor.blooddiamondleggings.get())
                .base(ArcanumArmor.blooddiamondleggings.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondlegs"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.voiddiamondboots.get())
                .pattern("YZY")
                .pattern("X X")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamond.get())
                .define('Z', ArcanumArmor.blooddiamondboots.get())
                .base(ArcanumArmor.blooddiamondboots.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(upgradeOutput, recipeId("crafting_voiddiamondboots"));

        // Void diamond beating stick
        shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.voiddiamondbeatingstick.get())
                .pattern("XYX")
                .pattern("XZX")
                .pattern("XYX")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('Y', ArcanumItems.blooddiamondstick.get())
                .define('Z', ArcanumWeapons.blooddiamondbeatingstick.get())
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(output, recipeId("crafting_voiddiamondbeatingstick"));
        // Infernal diamond tools
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernaldiamondpickaxe.get())
                .pattern("XZX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernalbeatingstick.get())
                .define('Z', ArcanumTools.voiddiamondpickaxe.get())
                .base(ArcanumTools.voiddiamondpickaxe.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondpickaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernaldiamondaxe.get())
                .pattern("XZ ")
                .pattern("XY ")
                .pattern(" Y ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernalbeatingstick.get())
                .define('Z', ArcanumTools.voiddiamondaxe.get())
                .base(ArcanumTools.voiddiamondaxe.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernaldiamondhoe.get())
                .pattern("XZ ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernalbeatingstick.get())
                .define('Z', ArcanumTools.voiddiamondhoe.get())
                .base(ArcanumTools.voiddiamondhoe.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondhoe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernaldiamondshovel.get())
                .pattern("X")
                .pattern("Z")
                .pattern("Y")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernalbeatingstick.get())
                .define('Z', ArcanumTools.voiddiamondshovel.get())
                .base(ArcanumTools.voiddiamondshovel.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondshovel"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.infernaldiamondsword.get())
                .pattern("X")
                .pattern("Z")
                .pattern("Y")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernalbeatingstick.get())
                .define('Z', ArcanumWeapons.voiddiamondsword.get())
                .base(ArcanumWeapons.voiddiamondsword.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondsword"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernaldiamondshears.get())
                .pattern("XZ")
                .pattern(" Y")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernalbeatingstick.get())
                .define('Z', ArcanumTools.voiddiamondshears.get())
                .base(ArcanumTools.voiddiamondshears.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondshears"));

        // Infernal diamond armor
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernaldiamondhelmet.get())
                .pattern("XXX")
                .pattern("XZX")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Z', ArcanumArmor.voiddiamondhelmet.get())
                .base(ArcanumArmor.voiddiamondhelmet.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondhelmet"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernaldiamondchestplate.get())
                .pattern("X X")
                .pattern("XZX")
                .pattern("XXX")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Z', ArcanumArmor.voiddiamondchestplate.get())
                .base(ArcanumArmor.voiddiamondchestplate.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondchestplate"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernaldiamondleggings.get())
                .pattern("XXX")
                .pattern("XZX")
                .pattern("X X")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Z', ArcanumArmor.voiddiamondleggings.get())
                .base(ArcanumArmor.voiddiamondleggings.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondlegs"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernaldiamondboots.get())
                .pattern("XZX")
                .pattern("X X")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Z', ArcanumArmor.voiddiamondboots.get())
                .base(ArcanumArmor.voiddiamondboots.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernaldiamondboots"));

        // Infernal beating stick
        shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.infernalbeatingstick.get())
                .pattern("XYX")
                .pattern("XZX")
                .pattern("XYX")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumItems.voiddiamond.get())
                .define('Z', ArcanumWeapons.voiddiamondbeatingstick.get())
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(output, recipeId("crafting_infernalbeatingstick"));

        // Infernal tools
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernalpickaxe.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumTools.infernaldiamondpickaxe.get())
                .base(ArcanumTools.infernaldiamondpickaxe.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalpickaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernalaxe.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumTools.infernaldiamondaxe.get())
                .base(ArcanumTools.infernaldiamondaxe.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalaxe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernalhoe.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumTools.infernaldiamondhoe.get())
                .base(ArcanumTools.infernaldiamondhoe.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalhoe"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernalshovel.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumTools.infernaldiamondshovel.get())
                .base(ArcanumTools.infernaldiamondshovel.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalshovel"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumWeapons.infernalsword.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumWeapons.infernaldiamondsword.get())
                .base(ArcanumWeapons.infernaldiamondsword.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalsword"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.TOOLS, ArcanumTools.infernalshears.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumTools.infernaldiamondshears.get())
                .base(ArcanumTools.infernaldiamondshears.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalshears"));

        // Infernal armor
        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernalhelmet.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumArmor.infernaldiamondhelmet.get())
                .base(ArcanumArmor.infernaldiamondhelmet.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalhelmet"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernalchestplate.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumArmor.infernaldiamondchestplate.get())
                .base(ArcanumArmor.infernaldiamondchestplate.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalchest"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernalleggings.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumArmor.infernaldiamondleggings.get())
                .base(ArcanumArmor.infernaldiamondleggings.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernallegs"));

        UpgradeCopyRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ArcanumArmor.infernalboots.get())
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('Y', ArcanumArmor.infernaldiamondboots.get())
                .base(ArcanumArmor.infernaldiamondboots.get())
                .stripEnchantments()
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(upgradeOutput, recipeId("crafting_infernalboots"));
        // Greensapphire coal and torch
        shaped(items, RecipeCategory.MISC, ArcanumItems.greensapphirecoal.get(), 4)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ArcanumItems.greensapphire.get())
                .define('Y', Items.COAL)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(output, recipeId("crafting_greensapphirecoal"));

        shapeless(items, RecipeCategory.MISC, ArcanumItems.greensapphirecoal.get(), 9)
                .requires(ArcanumBlocks.greensapphirecoal_block.get())
                .unlockedBy("has_greensapphirecoal_block", has(ArcanumBlocks.greensapphirecoal_block.get()))
                .save(output, recipeId("crafting_greensapphirecoal2"));

        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.greensapphirecoal_block.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ArcanumItems.greensapphirecoal.get())
                .unlockedBy("has_greensapphirecoal", has(ArcanumItems.greensapphirecoal.get()))
                .save(output, recipeId("crafting_greensapphirecoalblock"));

        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.greensapphiretorch_block_item.get(), 64)
                .pattern("Y")
                .pattern("X")
                .define('Y', ArcanumItems.greensapphirecoal.get())
                .define('X', woodenRods)
                .unlockedBy("has_greensapphirecoal", has(ArcanumItems.greensapphirecoal.get()))
                .save(output, recipeId("crafting_greensapphiretorch"));

        // Food and drink crafting
        shaped(items, RecipeCategory.FOOD, ArcanumFood.crushedapple.get(), 6)
                .pattern("X")
                .pattern("Y")
                .define('X', ArcanumItems.hammer.get())
                .define('Y', Items.APPLE)
                .unlockedBy("has_hammer", has(ArcanumItems.hammer.get()))
                .save(output, recipeId("crafting_crushedapple"));

        shaped(items, RecipeCategory.FOOD, ArcanumFood.applecider.get())
                .pattern("X")
                .pattern("Y")
                .define('X', ArcanumFood.crushedapple.get())
                .define('Y', Items.POTION)
                .unlockedBy("has_crushedapple", has(ArcanumFood.crushedapple.get()))
                .save(output, recipeId("crafting_applecider"));

        shaped(items, RecipeCategory.FOOD, ArcanumFood.unfermentedbeer.get())
                .pattern("XXX")
                .pattern("XYX")
                .pattern("XZX")
                .define('X', Items.WHEAT)
                .define('Y', Items.POTION)
                .define('Z', ArcanumItems.redcup.get())
                .unlockedBy("has_redcup", has(ArcanumItems.redcup.get()))
                .save(output, recipeId("crafting_unfermentedbeer"));

        shapeless(items, RecipeCategory.MISC, ArcanumItems.mountaindewmix.get())
                .requires(Items.GLOWSTONE_DUST)
                .requires(Items.GREEN_DYE)
                .requires(Items.WHEAT)
                .requires(Items.SUGAR)
                .unlockedBy("has_green_dye", has(Items.GREEN_DYE))
                .save(output, recipeId("crafting_mountaindewmix"));

        shaped(items, RecipeCategory.FOOD, ArcanumFood.mountaindew.get())
                .pattern("X")
                .pattern("Y")
                .pattern("Z")
                .define('X', ArcanumItems.mountaindewmix.get())
                .define('Y', Items.POTION)
                .define('Z', ArcanumItems.emptycan.get())
                .unlockedBy("has_mountaindewmix", has(ArcanumItems.mountaindewmix.get()))
                .save(output, recipeId("crafting_mountaindew"));

        shaped(items, RecipeCategory.MISC, ArcanumItems.emptycan.get())
                .pattern("X")
                .pattern("X")
                .define('X', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output, recipeId("crafting_emptycan"));

        shaped(items, RecipeCategory.MISC, ArcanumItems.redcup.get())
                .pattern("Y")
                .pattern("X")
                .define('X', ArcanumItems.emptycan.get())
                .define('Y', Items.RED_DYE)
                .unlockedBy("has_emptycan", has(ArcanumItems.emptycan.get()))
                .save(output, recipeId("crafting_redcup"));

        // Mob drop blocks and misc
        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.bone_block.get())
                .pattern("XX")
                .pattern("XX")
                .define('X', Items.BONE)
                .unlockedBy("has_bone", has(Items.BONE))
                .save(output, recipeId("crafting_boneblock"));

        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.flesh_block.get())
                .pattern("XX")
                .pattern("XX")
                .define('X', Items.ROTTEN_FLESH)
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(output, recipeId("crafting_rottenfleshblock"));

        shaped(items, RecipeCategory.BUILDING_BLOCKS, ArcanumBlocks.sulfur_block.get())
                .pattern("XX")
                .pattern("XX")
                .define('X', Items.GUNPOWDER)
                .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
                .save(output, recipeId("crafting_sulfurblock"));

        shapeless(items, RecipeCategory.MISC, Items.BONE, 4)
                .requires(ArcanumBlocks.bone_block.get())
                .unlockedBy("has_boneblock", has(ArcanumBlocks.bone_block.get()))
                .save(output, recipeId("crafting_bone"));

        shapeless(items, RecipeCategory.MISC, Items.ROTTEN_FLESH, 4)
                .requires(ArcanumBlocks.flesh_block.get())
                .unlockedBy("has_fleshblock", has(ArcanumBlocks.flesh_block.get()))
                .save(output, recipeId("crafting_rottenflesh"));

        shapeless(items, RecipeCategory.MISC, Items.GUNPOWDER, 4)
                .requires(ArcanumBlocks.sulfur_block.get())
                .unlockedBy("has_sulfurblock", has(ArcanumBlocks.sulfur_block.get()))
                .save(output, recipeId("crafting_gunpowder"));

        // Smelting
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ArcanumBlocks.greensapphireore_block.get()),
                        RecipeCategory.MISC,
                        ArcanumItems.greensapphire.get(),
                        1.0F,
                        200
                )
                .unlockedBy("has_greensapphireore", has(ArcanumBlocks.greensapphireore_block.get()))
                .save(smeltingOutput, recipeId("smelting_greensapphire"));

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ArcanumBlocks.blooddiamondore_block.get()),
                        RecipeCategory.MISC,
                        ArcanumItems.blooddiamond.get(),
                        1.0F,
                        200
                )
                .unlockedBy("has_blooddiamondore", has(ArcanumBlocks.blooddiamondore_block.get()))
                .save(smeltingOutput, recipeId("smelting_blooddiamond"));

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ArcanumBlocks.voiddiamondore_block.get()),
                        RecipeCategory.MISC,
                        ArcanumItems.voiddiamond.get(),
                        1.0F,
                        200
                )
                .unlockedBy("has_voiddiamondore", has(ArcanumBlocks.voiddiamondore_block.get()))
                .save(smeltingOutput, recipeId("smelting_voiddiamond"));

        FermentingRecipeBuilder.fermenting(
                        Ingredient.of(ArcanumFood.unfermentedbeer.get()),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.WHEAT),
                        Ingredient.of(Items.HONEY_BOTTLE),
                        RecipeCategory.FOOD,
                        ArcanumFood.fermentedbeer.get(),
                        1200
                )
                .unlockedBy("has_unfermentedbeer", has(ArcanumFood.unfermentedbeer.get()))
                .save(fermenterOutput, recipeId("fermenting_fermentedbeer"));

        FermentingRecipeBuilder.fermenting(
                        Ingredient.of(ArcanumFood.applecider.get()),
                        Ingredient.of(Items.SUGAR),
                        Ingredient.of(Items.RED_DYE),
                        Ingredient.of(ArcanumFood.unfermentedbeer.get()),
                        RecipeCategory.FOOD,
                        ArcanumFood.cortonwine.get(),
                        1200
                )
                .unlockedBy("has_applecider", has(ArcanumFood.applecider.get()))
                .save(fermenterOutput, recipeId("fermenting_cortonwine"));

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(Items.BREAD),
                        RecipeCategory.FOOD,
                        ArcanumFood.toast.get(),
                        1.0F,
                        200
                )
                .unlockedBy("has_bread", has(Items.BREAD))
                .save(smeltingOutput, recipeId("smelting_toast"));

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ArcanumFood.applecider.get()),
                        RecipeCategory.FOOD,
                        ArcanumFood.warmapplecider.get(),
                        1.0F,
                        200
                )
                .unlockedBy("has_applecider", has(ArcanumFood.applecider.get()))
                .save(smeltingOutput, recipeId("smelting_warmapplecider"));

        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.sapphirefurnace_block.get())
                .pattern(" X ")
                .pattern("XFX")
                .pattern(" X ")
                .define('X', ArcanumItems.greensapphire.get())
                .define('F', Items.FURNACE)
                .unlockedBy("has_greensapphire", has(ArcanumItems.greensapphire.get()))
                .save(furnaceBlocksOutput, recipeId("crafting_sapphirefurnace"));

        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.blooddiamondfurnace_block.get())
                .pattern("XXX")
                .pattern("XFX")
                .pattern("XXX")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('F', ArcanumBlocks.sapphirefurnace_block_item)
                .unlockedBy("has_blooddiamond", has(ArcanumItems.blooddiamond.get()))
                .save(furnaceBlocksOutput, recipeId("crafting_blooddiamondfurnace"));

        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.voiddiamondfurnace_block.get())
                .pattern(" X ")
                .pattern("XFX")
                .pattern(" X ")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('F', ArcanumBlocks.blooddiamondfurnace_block_item)
                .unlockedBy("has_voiddiamond", has(ArcanumItems.voiddiamond.get()))
                .save(furnaceBlocksOutput, recipeId("crafting_voiddiamondfurnace"));

        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.infernalfurnace_block.get())
                .pattern("XFX")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('F', ArcanumBlocks.voiddiamondfurnace_block_item)
                .unlockedBy("has_infernaldiamond", has(ArcanumItems.infernaldiamond.get()))
                .save(furnaceBlocksOutput, recipeId("crafting_infernalfurnace"));

        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.sapphiregenerator_block.get())
                .pattern("XRX")
                .pattern("XFX")
                .pattern("XRX")
                .define('X', ArcanumItems.greensapphire.get())
                .define('R', Items.REDSTONE)
                .define('F', ArcanumBlocks.sapphirefurnace_block_item)
                .unlockedBy("has_sapphirefurnace", has(ArcanumBlocks.sapphirefurnace_block_item))
                .save(generatorOutput, recipeId("crafting_sapphiregenerator"));
        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.blooddiamondgenerator_block.get())
                .pattern("XXX")
                .pattern("XFX")
                .pattern("XXX")
                .define('X', ArcanumItems.blooddiamond.get())
                .define('F', ArcanumBlocks.sapphiregenerator_block_item)
                .unlockedBy("has_sapphiregenerator", has(ArcanumBlocks.blooddiamondfurnace_block_item))
                .save(generatorOutput, recipeId("crafting_blooddiamondgenerator"));
        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.voiddiamondgenerator_block.get())
                .pattern(" X ")
                .pattern("XFX")
                .pattern(" X ")
                .define('X', ArcanumItems.voiddiamond.get())
                .define('F', ArcanumBlocks.blooddiamondgenerator_block_item)
                .unlockedBy("has_voiddiamondgenerator", has(ArcanumBlocks.voiddiamondfurnace_block_item))
                .save(generatorOutput, recipeId("crafting_voiddiamondgenerator"));
        shaped(items, RecipeCategory.DECORATIONS, ArcanumBlocks.infernalgenerator_block.get())
                .pattern("XFX")
                .define('X', ArcanumItems.infernaldiamond.get())
                .define('F', ArcanumBlocks.voiddiamondgenerator_block_item)
                .unlockedBy("has_voiddiamondgenerator", has(ArcanumBlocks.infernalfurnace_block_item))
                .save(generatorOutput, recipeId("crafting_infernalgenerator"));
    }
    private static ResourceKey<Recipe<?>> recipeId(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }

    private static TagKey<Item> itemTag(String id) {
        return ItemTags.create(Identifier.parse(id));
    }

    private static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemLike result) {
        return ShapedRecipeBuilder.shaped(items, category, result);
    }

    private static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(items, category, result, count);
    }

    private static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike result) {
        return ShapelessRecipeBuilder.shapeless(items, category, result);
    }

    private static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike result, int count) {
        return ShapelessRecipeBuilder.shapeless(items, category, result, count);
    }

    public static final class Runner extends net.minecraft.data.recipes.packs.VanillaRecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new RecipeDataGen(registries, output);
        }
    }
}
