package com.hellbreecher.arcanum.core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.hellbreecher.arcanum.common.handler.ArcanumAnvil;
import com.hellbreecher.arcanum.common.handler.ArcanumCapabilities;
import com.hellbreecher.arcanum.common.handler.datagen.ArcanumDataGen;
import com.hellbreecher.arcanum.common.handler.magic.SpellFlightManager;
import com.hellbreecher.arcanum.common.items.InfernalDiamondItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalDiamondArmorItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalAxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalPickaxeItem;
import com.hellbreecher.arcanum.common.items.weapons.InfernalSwordItem;
import com.hellbreecher.arcanum.common.handler.mana.ArcanumAttachments;
import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.common.network.ArcanumNetwork;
import com.hellbreecher.arcanum.common.recipe.ArcanumRecipeSerializers;
import com.hellbreecher.arcanum.common.recipe.ArcanumRecipeTypes;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import com.hellbreecher.arcanum.common.registration.ArcanumConditionSerializers;
import com.hellbreecher.arcanum.common.registration.ArcanumEntityTypes;
import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.hellbreecher.arcanum.common.platform.RegistryPlatform;
import com.hellbreecher.arcanum.common.platform.MenuTypePlatform;
import com.hellbreecher.arcanum.ArcanumCommon;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

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
        RegistryPlatform.install(new NeoForgeRegistryFactory(modEventBus));
        MenuTypePlatform.install(new NeoForgeMenuTypeFactory());
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ArcanumDataGen::gatherClient);
        modEventBus.addListener(ArcanumDataGen::gatherServer);
        modEventBus.addListener(ArcanumCapabilities::register);
        modEventBus.addListener(ArcanumNetwork::register);
        modEventBus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTab() == ArcanumCreativeTabs.ARCANUM_TAB.get()) {
                ArcanumCreativeTabs.addAll(event::accept);
            }
        });

        ArcanumCommon.initialize();
        ArcanumAttachments.register(modEventBus);
        ArcanumConditionSerializers.register(modEventBus);
        initClient(modEventBus, modContainer);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Arcanum) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.level.block.BreakBlockEvent event) -> {
            if (!InfernalAxeItem.onBlockBreak(event.getLevel(), event.getPos(), event.getState(), event.getPlayer())) event.setCanceled(true);
        });
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.level.block.BreakBlockEvent event) -> {
            if (!InfernalPickaxeItem.onBlockBreak(event.getLevel(), event.getPos(), event.getState(), event.getPlayer())) event.setCanceled(true);
        });
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent event) -> {
            if (event.getEntity() instanceof net.minecraft.world.entity.player.Player player) InfernalArmorItem.onEquipped(player);
        });
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent event) -> {
            if (event.getEntity() instanceof net.minecraft.world.entity.player.Player player) InfernalDiamondArmorItem.onArmorEquipped(player);
        });
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) -> InfernalArmorItem.onPlayerTick(event.getEntity()));
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) -> InfernalDiamondArmorItem.onPlayerTick(event.getEntity()));
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent event) -> {
            if (event.getEntity() instanceof net.minecraft.world.entity.player.Player player) event.setAmount(InfernalArmorItem.onIncomingDamage(player, event.getSource(), event.getAmount()));
        });
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre event) ->
                event.setNewDamage(InfernalSwordItem.onLivingDamage(event.getEntity(), event.getSource(), event.getNewDamage())));
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) -> ManaManager.onPlayerTick(event.getEntity()));
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) -> SpellFlightManager.onPlayerTick(event.getEntity()));
        NeoForge.EVENT_BUS.addListener((net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent event) -> InfernalDiamondItem.onItemCrafted(event.getInventory()));
        NeoForge.EVENT_BUS.addListener(ArcanumAnvil::onAnvilUpdate);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        Config.registerCommonFacade();
    }

    private static void initClient(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLEnvironment.getDist() != Dist.CLIENT) {
            return;
        }

        try {
            Class.forName("com.hellbreecher.arcanum.client.ArcanumClient")
                    .getMethod("init", IEventBus.class, ModContainer.class)
                    .invoke(null, modEventBus, modContainer);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to initialize Arcanum client setup", exception);
        }
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
