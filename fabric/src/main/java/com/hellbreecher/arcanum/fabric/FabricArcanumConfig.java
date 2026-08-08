package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.config.ArcanumConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

final class FabricArcanumConfig implements ArcanumConfig.Values {
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("arcanum-common.properties");
    private final Properties values = new Properties();

    static void install() {
        FabricArcanumConfig config = new FabricArcanumConfig();
        config.load();
        ArcanumConfig.setValues(config);
    }

    private void load() {
        if (Files.isRegularFile(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH)) { values.load(reader); }
            catch (IOException exception) { throw new IllegalStateException("Unable to read " + PATH, exception); }
        }
        putDefaults();
        try {
            Files.createDirectories(PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(PATH)) { values.store(writer, "Arcanum common configuration"); }
        } catch (IOException exception) { throw new IllegalStateException("Unable to write " + PATH, exception); }
    }

    private void putDefaults() {
        defaultValue("general.enableCraftedEnchantments", true);
        defaultValue("infernal.autosmelt.enabled", true);
        defaultValue("infernal.autosmelt.dropMultiplier", 10);
        defaultValue("infernal.autosmelt.xpMultiplier", 10);
        defaultValue("infernal.tools.ignoreHarvestLevel", true);
        defaultValue("infernal.armor.enableFlight", true);
        defaultValue("infernal.armor.invulnerable", true);
        defaultValue("infernal.armor.setFoodLevel", true);
        defaultValue("infernal.armor.fullHeal", true);
        defaultValue("infernal.armor.jumpBoost.enabled", true);
        defaultValue("infernal.armor.jumpBoost.amplifier", 6);
        defaultValue("infernal.armor.sprintSpeedMultiplier", 1.0D);
        defaultValue("infernalDiamond.armor.fireResistance", true);
        defaultValue("infernalDiamond.armor.jumpBoost.enabled", true);
        defaultValue("infernalDiamond.armor.jumpBoost.amplifier", 1);
        defaultValue("infernalDiamond.armor.sprintSpeedMultiplier", 0.5D);
        defaultValue("recipes.enableUpgrades", true);
        defaultValue("recipes.enableFermenter", true);
        defaultValue("recipes.enableSmelting", true);
        defaultValue("recipes.enableFurnaceBlocks", true);
        defaultValue("recipes.enableGenerators", true);
    }

    private void defaultValue(String key, Object value) { values.putIfAbsent(key, value.toString()); }
    private boolean bool(String key) { return Boolean.parseBoolean(values.getProperty(key)); }
    private int integer(String key, int min, int max) { return Math.clamp(Integer.parseInt(values.getProperty(key)), min, max); }
    private double decimal(String key, double min, double max) { return Math.clamp(Double.parseDouble(values.getProperty(key)), min, max); }

    public boolean enableCraftedEnchantments() { return bool("general.enableCraftedEnchantments"); }
    public boolean infernalAutosmeltEnabled() { return bool("infernal.autosmelt.enabled"); }
    public int infernalAutosmeltDropMultiplier() { return integer("infernal.autosmelt.dropMultiplier", 1, 100); }
    public int infernalAutosmeltXpMultiplier() { return integer("infernal.autosmelt.xpMultiplier", 1, 100); }
    public boolean infernalToolsIgnoreHarvestLevel() { return bool("infernal.tools.ignoreHarvestLevel"); }
    public boolean infernalArmorEnableFlight() { return bool("infernal.armor.enableFlight"); }
    public boolean infernalArmorInvulnerable() { return bool("infernal.armor.invulnerable"); }
    public boolean infernalArmorSetFood() { return bool("infernal.armor.setFoodLevel"); }
    public boolean infernalArmorFullHeal() { return bool("infernal.armor.fullHeal"); }
    public boolean infernalArmorJumpBoost() { return bool("infernal.armor.jumpBoost.enabled"); }
    public int infernalArmorJumpBoostAmplifier() { return integer("infernal.armor.jumpBoost.amplifier", 0, 10); }
    public double infernalArmorSprintSpeedMultiplier() { return decimal("infernal.armor.sprintSpeedMultiplier", 0, 5); }
    public boolean infernalDiamondArmorFireResist() { return bool("infernalDiamond.armor.fireResistance"); }
    public boolean infernalDiamondArmorJumpBoost() { return bool("infernalDiamond.armor.jumpBoost.enabled"); }
    public int infernalDiamondArmorJumpBoostAmplifier() { return integer("infernalDiamond.armor.jumpBoost.amplifier", 0, 10); }
    public double infernalDiamondArmorSprintSpeedMultiplier() { return decimal("infernalDiamond.armor.sprintSpeedMultiplier", 0, 5); }
    public boolean recipesEnableUpgrades() { return bool("recipes.enableUpgrades"); }
    public boolean recipesEnableFermenter() { return bool("recipes.enableFermenter"); }
    public boolean recipesEnableSmelting() { return bool("recipes.enableSmelting"); }
    public boolean recipesEnableFurnaceBlocks() { return bool("recipes.enableFurnaceBlocks"); }
    public boolean recipesEnableGenerators() { return bool("recipes.enableGenerators"); }
}
