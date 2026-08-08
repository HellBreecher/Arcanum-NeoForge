package com.hellbreecher.arcanum.common.blocks.tileentities;

import com.hellbreecher.arcanum.common.blocks.container.IngotFurnaceMenu;
import com.hellbreecher.arcanum.common.registration.ArcanumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BloodDiamondFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    private static final int SPEED_MULTIPLIER = 10;

    public BloodDiamondFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ArcanumBlockEntities.BLOOD_DIAMOND_FURNACE.get(), pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.arcanum.blooddiamondfurnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new IngotFurnaceMenu(id, player, this, this.dataAccess);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BloodDiamondFurnaceBlockEntity blockEntity) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            for (int i = 0; i < SPEED_MULTIPLIER; i++) {
                AbstractFurnaceBlockEntity.serverTick(serverLevel, pos, state, blockEntity);
            }
        }
    }
}
