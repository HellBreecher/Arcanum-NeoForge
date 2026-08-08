package com.hellbreecher.arcanum.common.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;

public class ArcWrenchItem extends Item {
	
    public ArcWrenchItem(Identifier id) {
        super(new Item.Properties()
                .stacksTo(1)
                .setId(ResourceKey.create(Registries.ITEM, id)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        Direction clickedFace = context.getClickedFace();
        BlockState rotated = rotateByClick(state, clickedFace);
        if (rotated == state) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            level.setBlock(pos, rotated, 3);
        }

        if (player != null) {
            player.swing(context.getHand());
        }

        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    private static BlockState rotateByClick(BlockState state, Direction clickedFace) {
        if (state.hasProperty(BlockStateProperties.FACING)) {
            Direction current = state.getValue(BlockStateProperties.FACING);
            if (clickedFace.getAxis().isVertical()) {
                Direction next = (current == Direction.UP) ? Direction.DOWN : Direction.UP;
                return state.setValue(BlockStateProperties.FACING, next);
            }
            return state.setValue(BlockStateProperties.FACING, current.getClockWise());
        }
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction current = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            if (clickedFace.getAxis().isVertical()) {
                return state.setValue(BlockStateProperties.HORIZONTAL_FACING, current.getOpposite());
            }
            return state.setValue(BlockStateProperties.HORIZONTAL_FACING, current.getClockWise());
        }
        return state;
    }
    
}
