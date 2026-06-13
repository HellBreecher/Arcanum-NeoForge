package com.hellbreecher.arcanum.common.network;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ArcanumNetwork {
    private ArcanumNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .playToServer(SelectSpellPayload.TYPE, SelectSpellPayload.STREAM_CODEC, ArcanumNetwork::handleSelectSpell);
    }

    private static void handleSelectSpell(SelectSpellPayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        Player player = context.player();
        int spell = SpellbookItem.normalizeSpell(payload.spell());

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ArcanumItems.spellbook.get())) {
                SpellbookItem.setSelectedSpell(stack, spell);
                return;
            }
        }
    }
}
