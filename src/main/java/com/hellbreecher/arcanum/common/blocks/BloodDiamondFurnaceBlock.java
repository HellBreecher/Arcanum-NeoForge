package com.hellbreecher.arcanum.common.blocks;

import com.hellbreecher.arcanum.common.blocks.tileentities.BloodDiamondFurnaceBlockEntity;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BloodDiamondFurnaceBlock extends AbstractFurnaceBlock {
    public static final MapCodec<BloodDiamondFurnaceBlock> CODEC = simpleCodec(BloodDiamondFurnaceBlock::new);

    public BloodDiamondFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BloodDiamondFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ArcanumBlockEntities.BLOOD_DIAMOND_FURNACE.get(), BloodDiamondFurnaceBlockEntity::serverTick);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof BloodDiamondFurnaceBlockEntity) {
            player.openMenu((BloodDiamondFurnaceBlockEntity) blockentity);
        }
    }
}
