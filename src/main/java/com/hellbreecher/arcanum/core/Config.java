package com.hellbreecher.arcanum.core;

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

    static final ModConfigSpec SPEC = BUILDER.build();
}
