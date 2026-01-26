package com.hellbreecher.arcanum.common.handler.datagen;

import com.hellbreecher.arcanum.core.Arcanum;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public final class WorldGenDataGen {
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREENSAPPHIRE_ORE = configuredKey("greensapphireore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLOODDIAMOND_ORE = configuredKey("blooddiamondore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOIDDIAMOND_ORE = configuredKey("voiddiamondore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VANILLARANDOM_ORE = configuredKey("vanillarandomore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MODRANDOM_ORE = configuredKey("modrandomore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_ORE = configuredKey("boneore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLESH_ORE = configuredKey("fleshore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_ORE = configuredKey("sulfurore");

    public static final ResourceKey<PlacedFeature> GREENSAPPHIRE_ORE_PLACED = placedKey("greensapphire_ore");
    public static final ResourceKey<PlacedFeature> BLOODDIAMOND_ORE_PLACED = placedKey("blooddiamond_ore");
    public static final ResourceKey<PlacedFeature> VOIDDIAMOND_ORE_PLACED = placedKey("voiddiamond_ore");
    public static final ResourceKey<PlacedFeature> VANILLARANDOM_ORE_PLACED = placedKey("vanillarandom_ore");
    public static final ResourceKey<PlacedFeature> MODRANDOM_ORE_PLACED = placedKey("modrandom_ore");
    public static final ResourceKey<PlacedFeature> BONE_ORE_PLACED = placedKey("bone_ore");
    public static final ResourceKey<PlacedFeature> FLESH_ORE_PLACED = placedKey("flesh_ore");
    public static final ResourceKey<PlacedFeature> SULFUR_ORE_PLACED = placedKey("sulfur_ore");
    public static final ResourceKey<BiomeModifier> ORE_BIOME_MODIFIER = biomeModifierKey("ores");

    private WorldGenDataGen() {}

    public static RegistrySetBuilder createRegistryBuilder() {
        return new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, WorldGenDataGen::bootstrapConfigured)
                .add(Registries.PLACED_FEATURE, WorldGenDataGen::bootstrapPlaced)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, WorldGenDataGen::bootstrapBiomeModifiers);
    }

    private static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, GREENSAPPHIRE_ORE, Feature.ORE,
                oreConfig(context, "greensapphireore", "deepslate_greensapphireore", 8, 0.0F));
        FeatureUtils.register(context, BLOODDIAMOND_ORE, Feature.ORE,
                oreConfig(context, "blooddiamondore", "deepslate_blooddiamondore", 4, 0.5F));
        FeatureUtils.register(context, VOIDDIAMOND_ORE, Feature.ORE,
                oreConfig(context, "voiddiamondore", "deepslate_voiddiamondore", 3, 0.0F));
        FeatureUtils.register(context, VANILLARANDOM_ORE, Feature.ORE,
                oreConfig(context, "vanillarandomore", "deepslate_vanillarandomore", 4, 0.5F));
        FeatureUtils.register(context, MODRANDOM_ORE, Feature.ORE,
                oreConfig(context, "modrandomore", "deepslate_modrandomore", 4, 0.5F));
        FeatureUtils.register(context, BONE_ORE, Feature.ORE,
                oreConfig(context, "boneore", 8, 0.0F));
        FeatureUtils.register(context, FLESH_ORE, Feature.ORE,
                oreConfig(context, "fleshore", 8, 0.0F));
        FeatureUtils.register(context, SULFUR_ORE, Feature.ORE,
                oreConfig(context, "sulfurore", 8, 0.0F));
    }

    private static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(context, GREENSAPPHIRE_ORE_PLACED, configured.getOrThrow(GREENSAPPHIRE_ORE),
                orePlacement(32, -64, 256));
        PlacementUtils.register(context, BLOODDIAMOND_ORE_PLACED, configured.getOrThrow(BLOODDIAMOND_ORE),
                orePlacement(20, -80, 256));
        PlacementUtils.register(context, VOIDDIAMOND_ORE_PLACED, configured.getOrThrow(VOIDDIAMOND_ORE),
                orePlacement(5, -80, 256));
        PlacementUtils.register(context, VANILLARANDOM_ORE_PLACED, configured.getOrThrow(VANILLARANDOM_ORE),
                orePlacement(20, -80, 256));
        PlacementUtils.register(context, MODRANDOM_ORE_PLACED, configured.getOrThrow(MODRANDOM_ORE),
                orePlacement(5, -80, 256));
        PlacementUtils.register(context, BONE_ORE_PLACED, configured.getOrThrow(BONE_ORE),
                orePlacement(15, -80, 256));
        PlacementUtils.register(context, FLESH_ORE_PLACED, configured.getOrThrow(FLESH_ORE),
                orePlacement(15, -80, 256));
        PlacementUtils.register(context, SULFUR_ORE_PLACED, configured.getOrThrow(SULFUR_ORE),
                orePlacement(15, -80, 256));
    }

    private static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderSet<Biome> overworld = biomes.getOrThrow(BiomeTags.IS_OVERWORLD);
        HolderSet<PlacedFeature> features = HolderSet.direct(
                placed.getOrThrow(GREENSAPPHIRE_ORE_PLACED),
                placed.getOrThrow(BLOODDIAMOND_ORE_PLACED),
                placed.getOrThrow(VOIDDIAMOND_ORE_PLACED),
                placed.getOrThrow(VANILLARANDOM_ORE_PLACED),
                placed.getOrThrow(MODRANDOM_ORE_PLACED),
                placed.getOrThrow(BONE_ORE_PLACED),
                placed.getOrThrow(FLESH_ORE_PLACED),
                placed.getOrThrow(SULFUR_ORE_PLACED)
        );
        context.register(
                ORE_BIOME_MODIFIER,
                new BiomeModifiers.AddFeaturesBiomeModifier(overworld, features, GenerationStep.Decoration.UNDERGROUND_ORES)
        );
    }

    private static OreConfiguration oreConfig(BootstrapContext<ConfiguredFeature<?, ?>> context, String stoneId, String deepslateId,
                                              int size, float discardChance) {
        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), blockState(context, stoneId)),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), blockState(context, deepslateId))
        );
        return new OreConfiguration(targets, size, discardChance);
    }

    private static OreConfiguration oreConfig(BootstrapContext<ConfiguredFeature<?, ?>> context, String stoneId,
                                              int size, float discardChance) {
        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), blockState(context, stoneId))
        );
        return new OreConfiguration(targets, size, discardChance);
    }

    private static Block block(BootstrapContext<ConfiguredFeature<?, ?>> context, String id) {
        Identifier key = Identifier.fromNamespaceAndPath(Arcanum.MODID, id);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        return blocks.getOrThrow(ResourceKey.create(Registries.BLOCK, key)).value();
    }

    private static net.minecraft.world.level.block.state.BlockState blockState(BootstrapContext<ConfiguredFeature<?, ?>> context, String id) {
        return block(context, id).defaultBlockState();
    }

    private static List<PlacementModifier> orePlacement(int count, int minY, int maxY) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)),
                BiomeFilter.biome()
        );
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }

    private static ResourceKey<PlacedFeature> placedKey(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }

    private static ResourceKey<BiomeModifier> biomeModifierKey(String path) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Arcanum.MODID, path));
    }
}
