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
        ArcanumServerNetworking.handle(payload, context.player());
    }

    private static void handleCastAuthorMantle(CastAuthorMantlePayload payload, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        ArcanumServerNetworking.handle(payload, context.player());
    }
}
