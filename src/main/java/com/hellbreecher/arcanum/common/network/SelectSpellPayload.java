package com.hellbreecher.arcanum.common.network;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SelectSpellPayload(int spell) implements CustomPacketPayload {
    public static final Type<SelectSpellPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Reference.MODID, "select_spell"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SelectSpellPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SelectSpellPayload::spell,
            SelectSpellPayload::new
    );

    @Override
    public Type<SelectSpellPayload> type() {
        return TYPE;
    }
}
