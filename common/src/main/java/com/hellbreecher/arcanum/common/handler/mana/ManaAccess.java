package com.hellbreecher.arcanum.common.handler.mana;

import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public final class ManaAccess {
    public interface Adapter {
        ManaData getMana(Player player);
        void setMana(Player player, ManaData value);
        AuthorMantleData getAuthorMantle(Player player);
        void setAuthorMantle(Player player, AuthorMantleData value);
    }

    private static Adapter adapter;
    private ManaAccess() { }
    public static void install(Adapter adapter) { ManaAccess.adapter = Objects.requireNonNull(adapter); }
    private static Adapter adapter() { if (adapter == null) throw new IllegalStateException("Mana persistence is not initialized"); return adapter; }
    public static ManaData getMana(Player player) { return adapter().getMana(player); }
    public static void setMana(Player player, ManaData value) { adapter().setMana(player, value); }
    public static AuthorMantleData getAuthorMantle(Player player) { return adapter().getAuthorMantle(player); }
    public static void setAuthorMantle(Player player, AuthorMantleData value) { adapter().setAuthorMantle(player, value); }
}
