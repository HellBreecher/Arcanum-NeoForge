package com.hellbreecher.arcanum.common.network;

import com.hellbreecher.arcanum.common.lib.Reference;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CastAuthorMantlePayload() implements CustomPacketPayload {
    public static final Type<CastAuthorMantlePayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Reference.MODID, "cast_author_mantle"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CastAuthorMantlePayload> STREAM_CODEC = StreamCodec.unit(new CastAuthorMantlePayload());

    @Override
    public Type<CastAuthorMantlePayload> type() {
        return TYPE;
    }
}
