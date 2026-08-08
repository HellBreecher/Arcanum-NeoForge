package com.hellbreecher.arcanum.common.config;

public final class ArcanumConfig {
    private static Values values = Defaults.INSTANCE;

    private ArcanumConfig() {
    }

    public static void setValues(Values values) {
        ArcanumConfig.values = values;
    }

    public static boolean enableCraftedEnchantments() {
        return values.enableCraftedEnchantments();
    }

    public static boolean infernalAutosmeltEnabled() {
        return values.infernalAutosmeltEnabled();
    }

    public static int infernalAutosmeltDropMultiplier() {
        return values.infernalAutosmeltDropMultiplier();
    }

    public static int infernalAutosmeltXpMultiplier() {
        return values.infernalAutosmeltXpMultiplier();
    }

    public static boolean infernalToolsIgnoreHarvestLevel() {
        return values.infernalToolsIgnoreHarvestLevel();
    }

    public static boolean infernalArmorEnableFlight() {
        return values.infernalArmorEnableFlight();
    }

    public static boolean infernalArmorInvulnerable() {
        return values.infernalArmorInvulnerable();
    }

    public static boolean infernalArmorSetFood() {
        return values.infernalArmorSetFood();
    }

    public static boolean infernalArmorFullHeal() {
        return values.infernalArmorFullHeal();
    }

    public static boolean infernalArmorJumpBoost() {
        return values.infernalArmorJumpBoost();
    }

    public static int infernalArmorJumpBoostAmplifier() {
        return values.infernalArmorJumpBoostAmplifier();
    }

    public static double infernalArmorSprintSpeedMultiplier() {
        return values.infernalArmorSprintSpeedMultiplier();
    }

    public static boolean infernalDiamondArmorFireResist() {
        return values.infernalDiamondArmorFireResist();
    }

    public static boolean infernalDiamondArmorJumpBoost() {
        return values.infernalDiamondArmorJumpBoost();
    }

    public static int infernalDiamondArmorJumpBoostAmplifier() {
        return values.infernalDiamondArmorJumpBoostAmplifier();
    }

    public static double infernalDiamondArmorSprintSpeedMultiplier() {
        return values.infernalDiamondArmorSprintSpeedMultiplier();
    }

    public static boolean recipesEnableUpgrades() {
        return values.recipesEnableUpgrades();
    }

    public static boolean recipesEnableFermenter() {
        return values.recipesEnableFermenter();
    }

    public static boolean recipesEnableSmelting() {
        return values.recipesEnableSmelting();
    }

    public static boolean recipesEnableFurnaceBlocks() {
        return values.recipesEnableFurnaceBlocks();
    }

    public static boolean recipesEnableGenerators() {
        return values.recipesEnableGenerators();
    }

    public interface Values {
        boolean enableCraftedEnchantments();

        boolean infernalAutosmeltEnabled();

        int infernalAutosmeltDropMultiplier();

        int infernalAutosmeltXpMultiplier();

        boolean infernalToolsIgnoreHarvestLevel();

        boolean infernalArmorEnableFlight();

        boolean infernalArmorInvulnerable();

        boolean infernalArmorSetFood();

        boolean infernalArmorFullHeal();

        boolean infernalArmorJumpBoost();

        int infernalArmorJumpBoostAmplifier();

        double infernalArmorSprintSpeedMultiplier();

        boolean infernalDiamondArmorFireResist();

        boolean infernalDiamondArmorJumpBoost();

        int infernalDiamondArmorJumpBoostAmplifier();

        double infernalDiamondArmorSprintSpeedMultiplier();

        boolean recipesEnableUpgrades();

        boolean recipesEnableFermenter();

        boolean recipesEnableSmelting();

        boolean recipesEnableFurnaceBlocks();

        boolean recipesEnableGenerators();
    }

    private enum Defaults implements Values {
        INSTANCE;

        @Override
        public boolean enableCraftedEnchantments() {
            return true;
        }

        @Override
        public boolean infernalAutosmeltEnabled() {
            return true;
        }

        @Override
        public int infernalAutosmeltDropMultiplier() {
            return 10;
        }

        @Override
        public int infernalAutosmeltXpMultiplier() {
            return 10;
        }

        @Override
        public boolean infernalToolsIgnoreHarvestLevel() {
            return true;
        }

        @Override
        public boolean infernalArmorEnableFlight() {
            return true;
        }

        @Override
        public boolean infernalArmorInvulnerable() {
            return true;
        }

        @Override
        public boolean infernalArmorSetFood() {
            return true;
        }

        @Override
        public boolean infernalArmorFullHeal() {
            return true;
        }

        @Override
        public boolean infernalArmorJumpBoost() {
            return true;
        }

        @Override
        public int infernalArmorJumpBoostAmplifier() {
            return 6;
        }

        @Override
        public double infernalArmorSprintSpeedMultiplier() {
            return 1.0D;
        }

        @Override
        public boolean infernalDiamondArmorFireResist() {
            return true;
        }

        @Override
        public boolean infernalDiamondArmorJumpBoost() {
            return true;
        }

        @Override
        public int infernalDiamondArmorJumpBoostAmplifier() {
            return 1;
        }

        @Override
        public double infernalDiamondArmorSprintSpeedMultiplier() {
            return 0.5D;
        }

        @Override
        public boolean recipesEnableUpgrades() {
            return true;
        }

        @Override
        public boolean recipesEnableFermenter() {
            return true;
        }

        @Override
        public boolean recipesEnableSmelting() {
            return true;
        }

        @Override
        public boolean recipesEnableFurnaceBlocks() {
            return true;
        }

        @Override
        public boolean recipesEnableGenerators() {
            return true;
        }
    }
}
