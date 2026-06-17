package com.hellbreecher.arcanum.client;

import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.common.handler.mana.ManaData;
import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public final class ManaHud {
    private static final int BAR_WIDTH = 182;
    private static final int BAR_HEIGHT = 5;
    private static final int BACKGROUND_COLOR = 0xAA160819;
    private static final int BORDER_COLOR = 0xCC3F123A;
    private static final int FILL_COLOR = 0xFFB02BFF;
    private static final int TEXT_COLOR = 0xFFDFA7FF;

    private ManaHud() {
    }

    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.options.hideGui || !ManaManager.hasManaFocus(player)) {
            return;
        }

        ManaData mana = ManaManager.get(player);
        GuiGraphicsExtractor graphics = event.getGuiGraphics();
        int x = (graphics.guiWidth() - BAR_WIDTH) / 2;
        int y = graphics.guiHeight() - 49;
        int fillWidth = Math.round((BAR_WIDTH - 2) * (mana.maxMana() <= 0 ? 0.0F : (float) mana.mana() / (float) mana.maxMana()));

        graphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, BORDER_COLOR);
        graphics.fill(x + 1, y + 1, x + BAR_WIDTH - 1, y + BAR_HEIGHT - 1, BACKGROUND_COLOR);
        if (fillWidth > 0) {
            graphics.fill(x + 1, y + 1, x + 1 + fillWidth, y + BAR_HEIGHT - 1, FILL_COLOR);
        }

        Component label = Component.literal(mana.mana() + " / " + mana.maxMana() + " Mana" + selectedSpellLabel(player));
        int labelX = (graphics.guiWidth() - minecraft.font.width(label)) / 2;
        graphics.text(minecraft.font, label, labelX, y - 10, TEXT_COLOR, true);
    }

    private static String selectedSpellLabel(LocalPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(ArcanumItems.spellbook.get())) {
                return " | " + SpellbookItem.getSpellName(SpellbookItem.getSelectedSpell(stack));
            }
        }
        return "";
    }
}
