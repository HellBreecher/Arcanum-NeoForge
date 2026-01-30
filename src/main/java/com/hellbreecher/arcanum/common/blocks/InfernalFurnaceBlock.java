package com.hellbreecher.arcanum.common.blocks;

import com.hellbreecher.arcanum.common.blocks.tileentities.InfernalFurnaceBlockEntity;
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

public class InfernalFurnaceBlock extends AbstractFurnaceBlock {
    public static final MapCodec<InfernalFurnaceBlock> CODEC = simpleCodec(InfernalFurnaceBlock::new);

    public InfernalFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfernalFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ArcanumBlockEntities.INFERNAL_FURNACE.get(), InfernalFurnaceBlockEntity::serverTick);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof InfernalFurnaceBlockEntity) {
            player.openMenu((InfernalFurnaceBlockEntity) blockentity);
        }
    }
}
