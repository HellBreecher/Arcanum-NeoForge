package com.hellbreecher.arcanum.common.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Objects;
import java.util.function.Consumer;

/** Loader-neutral client-to-server packet boundary. */
public final class ArcanumClientNetworking {
    private static Consumer<CustomPacketPayload> sender = payload -> {
        throw new IllegalStateException("Arcanum client networking has not been initialized");
    };

    private ArcanumClientNetworking() {
    }

    public static void install(Consumer<CustomPacketPayload> sender) {
        ArcanumClientNetworking.sender = Objects.requireNonNull(sender, "sender");
    }

    public static void send(CustomPacketPayload payload) {
        sender.accept(payload);
    }
}
