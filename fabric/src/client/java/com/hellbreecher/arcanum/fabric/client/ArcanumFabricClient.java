package com.hellbreecher.arcanum.fabric.client;

import com.hellbreecher.arcanum.client.screen.inventory.*;
import com.hellbreecher.arcanum.common.network.ArcanumClientNetworking;
import com.hellbreecher.arcanum.common.registration.ArcanumEntityTypes;
import com.hellbreecher.arcanum.common.registration.ArcanumMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.Identifier;

public final class ArcanumFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArcanumClientNetworking.install(ClientPlayNetworking::send);
        MenuScreens.register(ArcanumMenuTypes.VOID_DIAMOND_FURNACE.get(), IngotFurnaceScreen::new);
        MenuScreens.register(ArcanumMenuTypes.INFERNAL_FURNACE.get(), InfernalFurnaceScreen::new);
        MenuScreens.register(ArcanumMenuTypes.FERMENTER.get(), FermenterScreen::new);
        MenuScreens.register(ArcanumMenuTypes.SAPPHIRE_GENERATOR.get(), SapphireGeneratorScreen::new);
        EntityRendererRegistry.register(ArcanumEntityTypes.RIFT_SENTENCE_PROJECTILE.get(), NoopRenderer::new);
        FabricSpellKeybinds.register();
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR,
                Identifier.fromNamespaceAndPath("arcanum", "mana"), FabricManaHud::render);
    }
}
