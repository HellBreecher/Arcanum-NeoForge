package com.hellbreecher.arcanum.core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.hellbreecher.arcanum.common.handler.datagen.ArcanumDataGen;
import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalDiamondArmorItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalAxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalPickaxeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Arcanum.MODID)
public class Arcanum {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "arcanum";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Arcanum(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ArcanumDataGen::gatherClient);
        modEventBus.addListener(ArcanumDataGen::gatherServer);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ArcanumBlocks.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ArcanumItems.register(modEventBus);
        ArcanumFood.register(modEventBus);
        ArcanumTools.register(modEventBus);
        ArcanumWeapons.register(modEventBus);
        ArcanumArmor.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ArcanumCreativeTabs.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Arcanum) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(InfernalAxeItem::onBlockBreak);
        NeoForge.EVENT_BUS.addListener(InfernalPickaxeItem::onBlockBreak);
        NeoForge.EVENT_BUS.addListener(InfernalArmorItem::onEquipped);
        NeoForge.EVENT_BUS.addListener(InfernalDiamondArmorItem::onArmorEquipped);
        NeoForge.EVENT_BUS.addListener(InfernalArmorItem::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(InfernalDiamondArmorItem::onPlayerTick);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Starting Arcanum Common Setup");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Starting Arcanum Server Setup");
    }

}
