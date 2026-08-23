package com.hellbreecher.arcanum.fabric.client;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.network.ArcanumClientNetworking;
import com.hellbreecher.arcanum.common.network.CastAuthorMantlePayload;
import com.hellbreecher.arcanum.common.network.SelectSpellPayload;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

final class FabricSpellKeybinds {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("arcanum", "spellbook"));
    private static final KeyMapping NEXT = new KeyMapping("key.arcanum.next_spell", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY);
    private static final KeyMapping CAST = new KeyMapping("key.arcanum.cast_author_mantle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY);

    static void register() {
        KeyMappingHelper.registerKeyMapping(NEXT);
        KeyMappingHelper.registerKeyMapping(CAST);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.gui.screen() != null || client.player == null) return;
            while (NEXT.consumeClick()) cycle(client.player);
            while (CAST.consumeClick()) if (SpellbookItem.isDeveloper(client.player)) ArcanumClientNetworking.send(new CastAuthorMantlePayload());
        });
    }

    private static void cycle(LocalPlayer player) {
        ItemStack book = findBook(player);
        if (book.isEmpty()) {
            if (!SpellbookItem.isDeveloper(player)) return;
            int spell = SpellbookItem.nextSpell(player, SpellbookItem.getAuthorMantleSpell(player));
            SpellbookItem.setAuthorMantleSpell(player, spell);
            ArcanumClientNetworking.send(new SelectSpellPayload(spell));
            player.sendOverlayMessage(Component.literal("Author's Mantle: " + SpellbookItem.getSpellName(spell)));
            return;
        }
        int spell = SpellbookItem.nextSpell(book, player, SpellbookItem.getSelectedSpell(book));
        SpellbookItem.setSelectedSpell(book, spell);
        ArcanumClientNetworking.send(new SelectSpellPayload(spell));
        player.sendOverlayMessage(Component.literal("Selected: " + SpellbookItem.getSpellName(spell)));
    }

    private static ItemStack findBook(LocalPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (SpellbookItem.isSpellBook(stack)) return stack;
        }
        boolean wand = false;
        for (InteractionHand hand : InteractionHand.values()) wand |= player.getItemInHand(hand).is(ArcanumWeapons.infernalwand.get());
        if (wand) for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (SpellbookItem.isSpellBook(stack)) return stack;
        }
        return ItemStack.EMPTY;
    }
}
