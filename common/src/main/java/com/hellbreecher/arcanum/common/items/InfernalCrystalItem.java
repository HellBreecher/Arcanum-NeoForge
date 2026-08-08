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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InfernalCrystalItem extends Item {
    public InfernalCrystalItem(Identifier id) {
        super(new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, id))
                .stacksTo(64));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ManaManager.addStorage(player, ManaManager.CRYSTAL_STORAGE_BONUS);
        stack.shrink(1);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 0.8F);
        player.sendOverlayMessage(Component.literal("Mana storage +" + ManaManager.CRYSTAL_STORAGE_BONUS));
        return InteractionResult.CONSUME;
    }
}
