package com.hellbreecher.arcanum.common.handler.mana;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ManaData(int mana, int maxMana) {
    public static final int BASE_MAX_MANA = 100;
    public static final ManaData DEFAULT = new ManaData(BASE_MAX_MANA, BASE_MAX_MANA);

    public static final MapCodec<ManaData> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            com.mojang.serialization.Codec.INT.optionalFieldOf("mana", BASE_MAX_MANA).forGetter(ManaData::mana),
            com.mojang.serialization.Codec.INT.optionalFieldOf("max_mana", BASE_MAX_MANA).forGetter(ManaData::maxMana)
    ).apply(instance, ManaData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ManaData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ManaData::mana,
            ByteBufCodecs.VAR_INT,
            ManaData::maxMana,
            ManaData::new
    );

    public ManaData {
        maxMana = Math.max(BASE_MAX_MANA, maxMana);
        mana = Math.clamp(mana, 0, maxMana);
    }

    public ManaData addMana(int amount) {
        return new ManaData(this.mana + amount, this.maxMana);
    }

    public ManaData spendMana(int amount) {
        return new ManaData(this.mana - amount, this.maxMana);
    }

    public ManaData addStorage(int amount) {
        int newMax = this.maxMana + amount;
        return new ManaData(this.mana + amount, newMax);
    }

    public ManaData withMinimumMaxMana(int minimumMaxMana) {
        int newMax = Math.max(this.maxMana, minimumMaxMana);
        return new ManaData(this.mana, newMax);
    }
}
