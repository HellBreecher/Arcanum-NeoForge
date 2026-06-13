package com.hellbreecher.arcanum.common.items;

import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class InfernalCrystalBlockItem extends BlockItem {
    private static final int STORAGE_BONUS = ManaManager.CRYSTAL_STORAGE_BONUS * 9;

    public InfernalCrystalBlockItem(Block block, Identifier id) {
        super(block, new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, id))
                .stacksTo(64));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return consumeForMana(level, player, player.getItemInHand(hand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        return consumeForMana(context.getLevel(), player, context.getItemInHand());
    }

    private static InteractionResult consumeForMana(Level level, Player player, ItemStack stack) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ManaManager.addStorage(player, STORAGE_BONUS);
        stack.shrink(1);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 0.6F);
        player.sendOverlayMessage(Component.literal("Mana storage +" + STORAGE_BONUS));
        return InteractionResult.CONSUME;
    }
}
