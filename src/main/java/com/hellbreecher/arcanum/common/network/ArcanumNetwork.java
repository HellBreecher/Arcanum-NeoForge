package com.hellbreecher.arcanum.common.network;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ArcanumNetwork {
    private ArcanumNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .playToServer(SelectSpellPayload.TYPE, SelectSpellPayload.STREAM_CODEC, ArcanumNetwork::handleSelectSpell)
                .playToServer(CastAuthorMantlePayload.TYPE, CastAuthorMantlePayload.STREAM_CODEC, ArcanumNetwork::handleCastAuthorMantle);
    }

    private static void handleSelectSpell(SelectSpellPayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        Player player = context.player();
        int spell = SpellbookItem.normalizeSpell(payload.spell());
        if (!SpellbookItem.canUseSpell(player, spell)) {
            return;
        }

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ArcanumItems.spellbook.get())) {
                SpellbookItem.setSelectedSpell(stack, spell);
                return;
            }
        }

        if (!isHoldingWand(player)) {
            if (SpellbookItem.isDeveloper(player)) {
                SpellbookItem.setAuthorMantleSpell(player, spell);
            }
            return;
        }

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(ArcanumItems.spellbook.get())) {
                SpellbookItem.setSelectedSpell(stack, spell);
                return;
            }
        }

        if (SpellbookItem.isDeveloper(player)) {
            SpellbookItem.setAuthorMantleSpell(player, spell);
        }
    }

    private static void handleCastAuthorMantle(CastAuthorMantlePayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        Player player = context.player();
        SpellbookItem.castAuthorMantleSpell(player.level(), player);
    }

    private static boolean isHoldingWand(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).is(ArcanumWeapons.infernalwand.get())) {
                return true;
            }
        }
        return false;
    }
}
