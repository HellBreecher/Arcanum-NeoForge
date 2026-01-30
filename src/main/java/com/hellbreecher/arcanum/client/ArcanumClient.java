package com.hellbreecher.arcanum.client;

import com.hellbreecher.arcanum.client.screen.inventory.FermenterScreen;
import com.hellbreecher.arcanum.client.screen.inventory.InfernalFurnaceScreen;
import com.hellbreecher.arcanum.client.screen.inventory.IngotFurnaceScreen;
import com.hellbreecher.arcanum.client.screen.inventory.SapphireGeneratorScreen;
import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ArcanumClient {
    public static void init(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(ArcanumClient::onClientSetup);
        modEventBus.addListener(ArcanumClient::registerScreens);
    }

    static void onClientSetup(FMLClientSetupEvent event) {
        Arcanum.LOGGER.info("HELLO FROM CLIENT SETUP");
        Arcanum.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ArcanumMenuTypes.VOID_DIAMOND_FURNACE.get(), IngotFurnaceScreen::new);
        event.register(ArcanumMenuTypes.INFERNAL_FURNACE.get(), InfernalFurnaceScreen::new);
        event.register(ArcanumMenuTypes.FERMENTER.get(), FermenterScreen::new);
        event.register(ArcanumMenuTypes.SAPPHIRE_GENERATOR.get(), SapphireGeneratorScreen::new);
    }
}
