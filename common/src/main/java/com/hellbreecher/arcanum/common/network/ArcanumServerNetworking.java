package com.hellbreecher.arcanum.common.network;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ArcanumServerNetworking {
    private ArcanumServerNetworking() { }

    public static void handle(SelectSpellPayload payload, Player player) {
        int spell = SpellbookItem.normalizeSpell(payload.spell());
        if (!SpellbookItem.canUseSpell(player, spell)) return;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ArcanumItems.spellbook.get())) {
                SpellbookItem.setSelectedSpell(stack, spell);
                return;
            }
        }
        if (!isHoldingWand(player)) {
            if (SpellbookItem.isDeveloper(player)) SpellbookItem.setAuthorMantleSpell(player, spell);
            return;
        }
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(ArcanumItems.spellbook.get())) {
                SpellbookItem.setSelectedSpell(stack, spell);
                return;
            }
        }
        if (SpellbookItem.isDeveloper(player)) SpellbookItem.setAuthorMantleSpell(player, spell);
    }

    public static void handle(CastAuthorMantlePayload payload, Player player) {
        SpellbookItem.castAuthorMantleSpell(player.level(), player);
    }

    private static boolean isHoldingWand(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).is(ArcanumWeapons.infernalwand.get())) return true;
        }
        return false;
    }
}
