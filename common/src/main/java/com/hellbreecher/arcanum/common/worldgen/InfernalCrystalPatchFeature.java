package com.hellbreecher.arcanum.common.worldgen;

import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class InfernalCrystalPatchFeature extends Feature<NoneFeatureConfiguration> {
    private static final int LAVA_SEARCH_ATTEMPTS = 64;
    private static final int HORIZONTAL_RADIUS = 8;
    private static final int VERTICAL_RADIUS = 6;
    private static final int MAX_PLACEMENTS = 2;

    public InfernalCrystalPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource random = context.random();
        int placed = 0;

        for (int i = 0; i < LAVA_SEARCH_ATTEMPTS && placed < MAX_PLACEMENTS; i++) {
            BlockPos lavaPos = context.origin().offset(
                    random.nextInt(HORIZONTAL_RADIUS * 2 + 1) - HORIZONTAL_RADIUS,
                    random.nextInt(VERTICAL_RADIUS * 2 + 1) - VERTICAL_RADIUS,
                    random.nextInt(HORIZONTAL_RADIUS * 2 + 1) - HORIZONTAL_RADIUS
            );
            if (placeNearLava(context.level(), lavaPos, random)) {
                placed++;
            }
        }

        return placed > 0;
    }

    private static boolean placeNearLava(WorldGenLevel level, BlockPos lavaPos, RandomSource random) {
        if (!level.getFluidState(lavaPos).is(FluidTags.LAVA)) {
            return false;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
            if (placeCrystal(level, lavaPos.relative(direction).above())) {
                return true;
            }
        }
        return false;
    }

    private static boolean placeCrystal(WorldGenLevel level, BlockPos crystalPos) {
        BlockPos basePos = crystalPos.below();
        BlockState baseTarget = level.getBlockState(basePos);

        if (!level.getBlockState(crystalPos).isAir() || !canReplaceBase(baseTarget) || !isNearLava(level, basePos)) {
            return false;
        }

        level.setBlock(basePos, ArcanumBlocks.infernalcrystal_block.get().defaultBlockState(), 2);
        level.setBlock(crystalPos, ArcanumBlocks.infernalcrystal_plant.get().defaultBlockState(), 2);
        return true;
    }

    private static boolean canReplaceBase(BlockState state) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.BASE_STONE_NETHER);
    }

    private static boolean isNearLava(WorldGenLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getFluidState(pos.relative(direction)).is(FluidTags.LAVA)) {
                return true;
            }
        }
        return false;
    }
}
