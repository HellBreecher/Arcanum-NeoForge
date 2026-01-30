package com.hellbreecher.arcanum.common.blocks;

import com.hellbreecher.arcanum.common.blocks.tileentities.FermenterBlockEntity;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.Containers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FermenterBlock extends AbstractFurnaceBlock {
    public static final MapCodec<FermenterBlock> CODEC = simpleCodec(FermenterBlock::new);

    public FermenterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FermenterBlockEntity(pos, state);
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FermenterBlockEntity) {
                Containers.dropContents(level, pos, (FermenterBlockEntity) blockEntity);
            }
            level.removeBlockEntity(pos);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ArcanumBlockEntities.FERMENTER.get(), FermenterBlockEntity::serverTick);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof FermenterBlockEntity) {
            player.openMenu((FermenterBlockEntity) blockentity);
        }
    }
}
