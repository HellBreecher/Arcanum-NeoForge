package com.hellbreecher.arcanum.common.registration;

import com.hellbreecher.arcanum.common.blocks.container.FermenterMenu;
import com.hellbreecher.arcanum.common.blocks.container.InfernalFurnaceMenu;
import com.hellbreecher.arcanum.common.blocks.container.IngotFurnaceMenu;
import com.hellbreecher.arcanum.common.blocks.container.SapphireGeneratorMenu;
import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ArcanumMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Reference.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<IngotFurnaceMenu>> VOID_DIAMOND_FURNACE = MENU_TYPES.register("voiddiamondfurnace",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new IngotFurnaceMenu(windowId, inv)));
    public static final DeferredHolder<MenuType<?>, MenuType<InfernalFurnaceMenu>> INFERNAL_FURNACE = MENU_TYPES.register("infernalfurnace",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new InfernalFurnaceMenu(windowId, inv)));
    public static final DeferredHolder<MenuType<?>, MenuType<FermenterMenu>> FERMENTER = MENU_TYPES.register("fermenter",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new FermenterMenu(windowId, inv)));
    public static final DeferredHolder<MenuType<?>, MenuType<SapphireGeneratorMenu>> SAPPHIRE_GENERATOR = MENU_TYPES.register("sapphiregenerator",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new SapphireGeneratorMenu(windowId, inv)));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
