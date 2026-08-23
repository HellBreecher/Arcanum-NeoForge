package com.hellbreecher.arcanum.fabric;

import com.hellbreecher.arcanum.common.blocks.tileentities.*;
import com.hellbreecher.arcanum.common.handler.magic.SpellFlightManager;
import com.hellbreecher.arcanum.common.handler.magic.BindingRitualManager;
import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import com.hellbreecher.arcanum.common.items.armor.InfernalDiamondArmorItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalAxeItem;
import com.hellbreecher.arcanum.common.items.tools.InfernalPickaxeItem;
import com.hellbreecher.arcanum.common.network.*;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.hellbreecher.arcanum.core.ArcanumCreativeTabs;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import team.reborn.energy.api.EnergyStorage;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

final class FabricArcanumHooks {
    private FabricArcanumHooks() { }

    static void register() {
        registerNetworking();
        registerGameplayEvents();
        registerEnergy();
        registerFuel();
        registerWorldgen();
        ResourceKey<CreativeModeTab> tab = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("arcanum", "arcanum_tab"));
        CreativeModeTabEvents.modifyOutputEvent(tab).register(output -> ArcanumCreativeTabs.addAll(output::accept));
    }

    private static void registerNetworking() {
        PayloadTypeRegistry.serverboundPlay().register(SelectSpellPayload.TYPE, SelectSpellPayload.STREAM_CODEC);
        PayloadTypeRegistry.serverboundPlay().register(CastAuthorMantlePayload.TYPE, CastAuthorMantlePayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SelectSpellPayload.TYPE,
                (payload, context) -> context.server().execute(() -> ArcanumServerNetworking.handle(payload, context.player())));
        ServerPlayNetworking.registerGlobalReceiver(CastAuthorMantlePayload.TYPE,
                (payload, context) -> context.server().execute(() -> ArcanumServerNetworking.handle(payload, context.player())));
    }

    private static void registerGameplayEvents() {
        PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) ->
                InfernalAxeItem.onBlockBreak(level, pos, state, player)
                        && InfernalPickaxeItem.onBlockBreak(level, pos, state, player));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerList().getPlayers().forEach(player -> {
                InfernalArmorItem.onEquipped(player);
                InfernalDiamondArmorItem.onArmorEquipped(player);
                InfernalArmorItem.onPlayerTick(player);
                InfernalDiamondArmorItem.onPlayerTick(player);
                ManaManager.onPlayerTick(player);
                SpellFlightManager.onPlayerTick(player);
            });
            BindingRitualManager.tick(server);
        });
    }

    private static void registerEnergy() {
        EnergyStorage.SIDED.registerForBlockEntity((be, direction) -> new FabricEnergyAdapter(be.getEnergyStorage()), ArcanumBlockEntities.SAPPHIRE_GENERATOR.get());
        EnergyStorage.SIDED.registerForBlockEntity((be, direction) -> new FabricEnergyAdapter(be.getEnergyStorage()), ArcanumBlockEntities.BLOOD_DIAMOND_GENERATOR.get());
        EnergyStorage.SIDED.registerForBlockEntity((be, direction) -> new FabricEnergyAdapter(be.getEnergyStorage()), ArcanumBlockEntities.VOID_DIAMOND_GENERATOR.get());
        EnergyStorage.SIDED.registerForBlockEntity((be, direction) -> new FabricEnergyAdapter(be.getEnergyStorage()), ArcanumBlockEntities.INFERNAL_GENERATOR.get());
    }

    private static void registerFuel() {
        FuelValueEvents.BUILD.register((builder, context) -> {
            builder.add(ArcanumItems.greensapphirecoal.get(), 6400);
            builder.add(ArcanumBlocks.greensapphirecoal_block_item.get(), 57600);
            builder.add(ArcanumItems.infernaldiamond.get(), 1);
        });
    }

    private static void registerWorldgen() {
        String[] ores = {"greensapphire_ore", "blooddiamond_ore", "voiddiamond_ore", "vanillarandom_ore", "modrandom_ore", "bone_ore", "flesh_ore", "sulfur_ore"};
        for (String name : ores) {
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, placed(name));
        }
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_DECORATION, placed("infernal_crystal_patch"));
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Decoration.UNDERGROUND_DECORATION, placed("infernal_crystal_patch"));
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath("arcanum", name));
    }
}
