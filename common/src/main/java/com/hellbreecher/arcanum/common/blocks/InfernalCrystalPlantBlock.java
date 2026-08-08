package com.hellbreecher.arcanum.common.blocks;

import com.hellbreecher.arcanum.core.ArcanumBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InfernalCrystalPlantBlock extends BushBlock {
    public static final MapCodec<InfernalCrystalPlantBlock> CODEC = simpleCodec(InfernalCrystalPlantBlock::new);
    private static final VoxelShape SHAPE = box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);

    public InfernalCrystalPlantBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MapCodec<BushBlock> codec() {
        return (MapCodec<BushBlock>) (MapCodec<?>) CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ArcanumBlocks.infernalcrystal_block.get());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
