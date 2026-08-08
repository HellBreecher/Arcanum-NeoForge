package com.hellbreecher.arcanum.fabric.client;

import com.hellbreecher.arcanum.common.handler.mana.ManaData;
import com.hellbreecher.arcanum.common.handler.mana.ManaManager;
import com.hellbreecher.arcanum.common.items.SpellbookItem;
import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;

final class FabricManaHud {
    static void render(GuiGraphicsExtractor graphics, DeltaTracker delta) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.gui.hud.isHidden() || !ManaManager.hasManaFocus(player)) return;
        ManaData mana = ManaManager.get(player);
        int width = 182, height = 5;
        int x = (graphics.guiWidth() - width) / 2, y = graphics.guiHeight() - 49;
        int fill = Math.round((width - 2) * (mana.maxMana() <= 0 ? 0 : (float) mana.mana() / mana.maxMana()));
        graphics.fill(x, y, x + width, y + height, 0xCC5A1309);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, 0xAA1F0502);
        if (fill > 0) graphics.fill(x + 1, y + 1, x + 1 + fill, y + height - 1, 0xFFFF3B16);
        Component label = Component.literal(mana.mana() + " / " + mana.maxMana() + " Mana" + spell(player));
        graphics.text(minecraft.font, label, (graphics.guiWidth() - minecraft.font.width(label)) / 2, y - 10, 0xFFFFB199, true);
    }

    private static String spell(LocalPlayer player) {
        for (InteractionHand hand : InteractionHand.values()) {
            var stack = player.getItemInHand(hand);
            if (stack.is(ArcanumItems.spellbook.get())) return " | " + SpellbookItem.getSpellName(SpellbookItem.getSelectedSpell(stack));
        }
        return SpellbookItem.isDeveloper(player) ? " | Author's Mantle: " + SpellbookItem.getSpellName(SpellbookItem.getAuthorMantleSpell(player)) : "";
    }
}
