package com.hellbreecher.arcanum.common.handler.mana;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record AuthorMantleData(int selectedSpell) {
    public static final AuthorMantleData DEFAULT = new AuthorMantleData(0);

    public static final MapCodec<AuthorMantleData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            com.mojang.serialization.Codec.INT.optionalFieldOf("selected_spell", 0).forGetter(AuthorMantleData::selectedSpell)
    ).apply(instance, AuthorMantleData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AuthorMantleData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AuthorMantleData::selectedSpell,
            AuthorMantleData::new
    );

    public AuthorMantleData {
        selectedSpell = Math.floorMod(selectedSpell, 12);
    }
}
