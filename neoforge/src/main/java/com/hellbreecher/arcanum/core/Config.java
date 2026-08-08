package com.hellbreecher.arcanum.core;

import com.hellbreecher.arcanum.common.config.ArcanumConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_CRAFTED_ENCHANTMENTS = BUILDER
            .comment("Apply the built-in enchantments when items are crafted.")
            .define("general.enableCraftedEnchantments", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_AUTOSMELT_ENABLED = BUILDER
            .comment("Enable autosmelting for infernal axe/pickaxe.")
            .define("infernal.autosmelt.enabled", true);

    public static final ModConfigSpec.IntValue INFERNAL_AUTOSMELT_DROP_MULTIPLIER = BUILDER
            .comment("Multiplier for autosmelting drop counts.")
            .defineInRange("infernal.autosmelt.dropMultiplier", 10, 1, 100);

    public static final ModConfigSpec.IntValue INFERNAL_AUTOSMELT_XP_MULTIPLIER = BUILDER
            .comment("Multiplier for autosmelting experience drops.")
            .defineInRange("infernal.autosmelt.xpMultiplier", 10, 1, 100);

    public static final ModConfigSpec.BooleanValue INFERNAL_TOOLS_IGNORE_HARVEST_LEVEL = BUILDER
            .comment("Infernal tools can mine any block that matches their tool type.")
            .define("infernal.tools.ignoreHarvestLevel", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_ARMOR_ENABLE_FLIGHT = BUILDER
            .comment("Infernal armor grants flight when wearing the full set.")
            .define("infernal.armor.enableFlight", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_ARMOR_INVULNERABLE = BUILDER
            .comment("Infernal armor grants invulnerability when wearing the full set.")
            .define("infernal.armor.invulnerable", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_ARMOR_SET_FOOD = BUILDER
            .comment("Infernal armor refills food when wearing the full set.")
            .define("infernal.armor.setFoodLevel", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_ARMOR_FULL_HEAL = BUILDER
            .comment("Infernal armor heals to max health when wearing the full set.")
            .define("infernal.armor.fullHeal", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_ARMOR_JUMP_BOOST = BUILDER
            .comment("Infernal armor applies jump boost when wearing the full set.")
            .define("infernal.armor.jumpBoost.enabled", true);

    public static final ModConfigSpec.IntValue INFERNAL_ARMOR_JUMP_BOOST_AMPLIFIER = BUILDER
            .comment("Jump boost amplifier for infernal armor.")
            .defineInRange("infernal.armor.jumpBoost.amplifier", 6, 0, 10);

    public static final ModConfigSpec.DoubleValue INFERNAL_ARMOR_SPRINT_SPEED_MULTIPLIER = BUILDER
            .comment("Sprint speed bonus multiplier for infernal armor.")
            .defineInRange("infernal.armor.sprintSpeedMultiplier", 1.0D, 0.0D, 5.0D);

    public static final ModConfigSpec.BooleanValue INFERNAL_DIAMOND_ARMOR_FIRE_RESIST = BUILDER
            .comment("Infernal diamond armor grants fire resistance when wearing the full set.")
            .define("infernalDiamond.armor.fireResistance", true);

    public static final ModConfigSpec.BooleanValue INFERNAL_DIAMOND_ARMOR_JUMP_BOOST = BUILDER
            .comment("Infernal diamond armor applies jump boost when wearing the full set.")
            .define("infernalDiamond.armor.jumpBoost.enabled", true);

    public static final ModConfigSpec.IntValue INFERNAL_DIAMOND_ARMOR_JUMP_BOOST_AMPLIFIER = BUILDER
            .comment("Jump boost amplifier for infernal diamond armor.")
            .defineInRange("infernalDiamond.armor.jumpBoost.amplifier", 1, 0, 10);

    public static final ModConfigSpec.DoubleValue INFERNAL_DIAMOND_ARMOR_SPRINT_SPEED_MULTIPLIER = BUILDER
            .comment("Sprint speed bonus multiplier for infernal diamond armor.")
            .defineInRange("infernalDiamond.armor.sprintSpeedMultiplier", 0.5D, 0.0D, 5.0D);

    public static final ModConfigSpec.BooleanValue RECIPES_ENABLE_UPGRADES = BUILDER
            .comment("Enable upgrade-tier crafting recipes (blood/void/infernal tiers).")
            .define("recipes.enableUpgrades", true);

    public static final ModConfigSpec.BooleanValue RECIPES_ENABLE_FERMENTER = BUILDER
            .comment("Enable fermenter recipes.")
            .define("recipes.enableFermenter", true);

    public static final ModConfigSpec.BooleanValue RECIPES_ENABLE_SMELTING = BUILDER
            .comment("Enable smelting recipes.")
            .define("recipes.enableSmelting", true);

    public static final ModConfigSpec.BooleanValue RECIPES_ENABLE_FURNACE_BLOCKS = BUILDER
            .comment("Enable furnace block crafting recipes.")
            .define("recipes.enableFurnaceBlocks", true);

    public static final ModConfigSpec.BooleanValue RECIPES_ENABLE_GENERATORS = BUILDER
            .comment("Enable generator crafting recipes.")
            .define("recipes.enableGenerators", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static void registerCommonFacade() {
        ArcanumConfig.setValues(new NeoForgeValues());
    }

    private static final class NeoForgeValues implements ArcanumConfig.Values {
        @Override
        public boolean enableCraftedEnchantments() {
            return ENABLE_CRAFTED_ENCHANTMENTS.get();
        }

        @Override
        public boolean infernalAutosmeltEnabled() {
            return INFERNAL_AUTOSMELT_ENABLED.get();
        }

        @Override
        public int infernalAutosmeltDropMultiplier() {
            return INFERNAL_AUTOSMELT_DROP_MULTIPLIER.get();
        }

        @Override
        public int infernalAutosmeltXpMultiplier() {
            return INFERNAL_AUTOSMELT_XP_MULTIPLIER.get();
        }

        @Override
        public boolean infernalToolsIgnoreHarvestLevel() {
            return INFERNAL_TOOLS_IGNORE_HARVEST_LEVEL.get();
        }

        @Override
        public boolean infernalArmorEnableFlight() {
            return INFERNAL_ARMOR_ENABLE_FLIGHT.get();
        }

        @Override
        public boolean infernalArmorInvulnerable() {
            return INFERNAL_ARMOR_INVULNERABLE.get();
        }

        @Override
        public boolean infernalArmorSetFood() {
            return INFERNAL_ARMOR_SET_FOOD.get();
        }

        @Override
        public boolean infernalArmorFullHeal() {
            return INFERNAL_ARMOR_FULL_HEAL.get();
        }

        @Override
        public boolean infernalArmorJumpBoost() {
            return INFERNAL_ARMOR_JUMP_BOOST.get();
        }

        @Override
        public int infernalArmorJumpBoostAmplifier() {
            return INFERNAL_ARMOR_JUMP_BOOST_AMPLIFIER.get();
        }

        @Override
        public double infernalArmorSprintSpeedMultiplier() {
            return INFERNAL_ARMOR_SPRINT_SPEED_MULTIPLIER.get();
        }

        @Override
        public boolean infernalDiamondArmorFireResist() {
            return INFERNAL_DIAMOND_ARMOR_FIRE_RESIST.get();
        }

        @Override
        public boolean infernalDiamondArmorJumpBoost() {
            return INFERNAL_DIAMOND_ARMOR_JUMP_BOOST.get();
        }

        @Override
        public int infernalDiamondArmorJumpBoostAmplifier() {
            return INFERNAL_DIAMOND_ARMOR_JUMP_BOOST_AMPLIFIER.get();
        }

        @Override
        public double infernalDiamondArmorSprintSpeedMultiplier() {
            return INFERNAL_DIAMOND_ARMOR_SPRINT_SPEED_MULTIPLIER.get();
        }

        @Override
        public boolean recipesEnableUpgrades() {
            return RECIPES_ENABLE_UPGRADES.get();
        }

        @Override
        public boolean recipesEnableFermenter() {
            return RECIPES_ENABLE_FERMENTER.get();
        }

        @Override
        public boolean recipesEnableSmelting() {
            return RECIPES_ENABLE_SMELTING.get();
        }

        @Override
        public boolean recipesEnableFurnaceBlocks() {
            return RECIPES_ENABLE_FURNACE_BLOCKS.get();
        }

        @Override
        public boolean recipesEnableGenerators() {
            return RECIPES_ENABLE_GENERATORS.get();
        }
    }
}
