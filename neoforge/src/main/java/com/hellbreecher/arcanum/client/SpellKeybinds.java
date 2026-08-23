package com.hellbreecher.arcanum.client;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.network.CastAuthorMantlePayload;
import com.hellbreecher.arcanum.common.network.SelectSpellPayload;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.hellbreecher.arcanum.core.ArcanumWeapons;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import com.hellbreecher.arcanum.common.network.ArcanumClientNetworking;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.glfw.GLFW;

public final class SpellKeybinds {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("arcanum", "spellbook"));
    private static final KeyMapping NEXT_SPELL = new KeyMapping(
            "key.arcanum.next_spell",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            CATEGORY
    );
    private static final KeyMapping CAST_AUTHOR_MANTLE = new KeyMapping(
            "key.arcanum.cast_author_mantle",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            CATEGORY
    );

    private SpellKeybinds() {
    }

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(NEXT_SPELL);
        event.register(CAST_AUTHOR_MANTLE);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gui.screen() != null) {
            return;
        }

        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        while (NEXT_SPELL.consumeClick()) {
            cycleSpell(player);
        }

        while (CAST_AUTHOR_MANTLE.consumeClick()) {
            if (SpellbookItem.isDeveloper(player)) {
                ArcanumClientNetworking.send(new CastAuthorMantlePayload());
            }
        }
    }

    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        if (!(event.getEntity() instanceof LocalPlayer player) || !SpellbookItem.isDeveloper(player)) {
            return;
        }

        if (!isOpenHandCast(event.getHand(), player)) {
            return;
        }

        ArcanumClientNetworking.send(new CastAuthorMantlePayload());
    }

    private static void cycleSpell(LocalPlayer player) {
        ItemStack stack = getSelectableSpellbook(player);
        if (stack.isEmpty()) {
            if (!SpellbookItem.isDeveloper(player)) {
                return;
            }

            int nextSpell = SpellbookItem.nextSpell(player, SpellbookItem.getAuthorMantleSpell(player));
            SpellbookItem.setAuthorMantleSpell(player, nextSpell);
            ArcanumClientNetworking.send(new SelectSpellPayload(nextSpell));
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("Author's Mantle: " + SpellbookItem.getSpellName(nextSpell)));
            return;
        }

        int nextSpell = SpellbookItem.nextSpell(stack, player, SpellbookItem.getSelectedSpell(stack));
        SpellbookItem.setSelectedSpell(stack, nextSpell);
        ArcanumClientNetworking.send(new SelectSpellPayload(nextSpell));
        player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("Selected: " + SpellbookItem.getSpellName(nextSpell)));
    }

    private static boolean isOpenHandCast(InteractionHand hand, LocalPlayer player) {
        if (!player.getItemInHand(hand).isEmpty()) {
            return false;
        }
        return hand == InteractionHand.MAIN_HAND || !player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
    }

    private static ItemStack getSelectableSpellbook(LocalPlayer player) {
        ItemStack heldSpellbook = getHeldSpellbook(player);
        if (!heldSpellbook.isEmpty()) {
            return heldSpellbook;
        }
        if (isHoldingWand(player)) {
            return getInventorySpellbook(player);
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack getHeldSpellbook(LocalPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (SpellbookItem.isSpellBook(stack)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean isHoldingWand(LocalPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).is(ArcanumWeapons.infernalwand.get())) {
                return true;
            }
        }
        return false;
    }

    private static ItemStack getInventorySpellbook(LocalPlayer player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (SpellbookItem.isSpellBook(stack)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
