package com.hellbreecher.arcanum.common.handler.magic;

import com.hellbreecher.arcanum.common.items.armor.InfernalArmorItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

public final class SpellFlightManager {
    private static final Map<Player, Boolean> ACTIVE_PLAYERS = new WeakHashMap<>();

    private SpellFlightManager() {
    }

    public static void activate(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        ACTIVE_PLAYERS.put(player, !player.onGround());
        player.getAbilities().mayfly = true;
        player.getAbilities().setFlyingSpeed(0.1F);
        player.onUpdateAbilities();

        if (player.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.25D, player.getZ(), 12, 0.35D, 0.1D, 0.35D, 0.01D);
        }
    }

    public static void onPlayerTick(Player player) {
        if (player.level().isClientSide() || !ACTIVE_PLAYERS.containsKey(player)) {
            return;
        }

        boolean hasLeftGround = ACTIVE_PLAYERS.get(player);
        if (!player.onGround() || player.getAbilities().flying) {
            ACTIVE_PLAYERS.put(player, true);
            return;
        }

        if (hasLeftGround) {
            deactivate(player);
        }
    }

    private static void deactivate(Player player) {
        ACTIVE_PLAYERS.remove(player);
        if (player.isCreative() || player.isSpectator() || InfernalArmorItem.isWearingFullSet(player)) {
            return;
        }

        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.getAbilities().setFlyingSpeed(0.1F);
        player.onUpdateAbilities();
    }
}
