package com.hellbreecher.arcanum.client;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.network.SelectSpellPayload;
import com.hellbreecher.arcanum.core.ArcanumItems;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.lwjgl.glfw.GLFW;

public final class SpellKeybinds {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("arcanum", "spellbook"));
    private static final KeyMapping NEXT_SPELL = new KeyMapping(
            "key.arcanum.next_spell",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            CATEGORY
    );

    private SpellKeybinds() {
    }

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(NEXT_SPELL);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null) {
            return;
        }

        while (NEXT_SPELL.consumeClick()) {
            LocalPlayer player = minecraft.player;
            if (player == null) {
                return;
            }

            ItemStack stack = getHeldSpellbook(player);
            if (stack.isEmpty()) {
                return;
            }

            int nextSpell = SpellbookItem.nextSpell(SpellbookItem.getSelectedSpell(stack));
            SpellbookItem.setSelectedSpell(stack, nextSpell);
            ClientPacketDistributor.sendToServer(new SelectSpellPayload(nextSpell));
            player.sendOverlayMessage(net.minecraft.network.chat.Component.literal("Selected: " + SpellbookItem.getSpellName(nextSpell)));
        }
    }

    private static ItemStack getHeldSpellbook(LocalPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ArcanumItems.spellbook.get())) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
