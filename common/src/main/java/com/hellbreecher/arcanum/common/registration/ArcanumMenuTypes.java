package com.hellbreecher.arcanum.common.registration;

import com.hellbreecher.arcanum.common.blocks.container.FermenterMenu;
import com.hellbreecher.arcanum.common.blocks.container.InfernalFurnaceMenu;
import com.hellbreecher.arcanum.common.blocks.container.IngotFurnaceMenu;
import com.hellbreecher.arcanum.common.blocks.container.SapphireGeneratorMenu;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import com.hellbreecher.arcanum.common.platform.MenuTypePlatform;
import com.hellbreecher.arcanum.common.platform.RegistryEntry;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.RegistryRegistrar;

public class ArcanumMenuTypes {
    public static final RegistryRegistrar<MenuType<?>> MENU_TYPES = RegistryPlatform.create(BuiltInRegistries.MENU, Reference.MODID);

    public static final RegistryEntry<MenuType<IngotFurnaceMenu>> VOID_DIAMOND_FURNACE = MENU_TYPES.register("voiddiamondfurnace",
            () -> MenuTypePlatform.create(IngotFurnaceMenu::new));
    public static final RegistryEntry<MenuType<InfernalFurnaceMenu>> INFERNAL_FURNACE = MENU_TYPES.register("infernalfurnace",
            () -> MenuTypePlatform.create(InfernalFurnaceMenu::new));
    public static final RegistryEntry<MenuType<FermenterMenu>> FERMENTER = MENU_TYPES.register("fermenter",
            () -> MenuTypePlatform.create(FermenterMenu::new));
    public static final RegistryEntry<MenuType<SapphireGeneratorMenu>> SAPPHIRE_GENERATOR = MENU_TYPES.register("sapphiregenerator",
            () -> MenuTypePlatform.create(SapphireGeneratorMenu::new));

    public static void bootstrap() { }
}
