package com.hellbreecher.arcanum.common.items;

import com.hellbreecher.arcanum.core.ArcanumItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class SpellPageItem extends Item {
    private final int spell;

    public SpellPageItem(Identifier id, int spell) {
        super(new Properties().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, id)));
        this.spell = spell;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack page = player.getItemInHand(hand);
        boolean foundMagicBook = false;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack codex = player.getInventory().getItem(slot);
            if (!codex.is(ArcanumItems.arcane_codex.get()) && !codex.is(ArcanumItems.forbidden_grimoire.get())) continue;
            foundMagicBook = true;
            if (!SpellbookItem.canLearnSpell(codex, spell)) continue;
            if (SpellbookItem.knowsSpell(codex, spell)) {
                player.sendOverlayMessage(Component.literal("This book already contains " + SpellbookItem.getSpellName(spell)));
                return InteractionResult.SUCCESS;
            }
            if (!level.isClientSide()) {
                if (SpellbookItem.unlockSpell(codex, spell)) {
                    page.consume(1, player);
                    player.sendOverlayMessage(Component.literal("Learned: " + SpellbookItem.getSpellName(spell)));
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (foundMagicBook && SpellbookItem.requiresForbiddenGrimoire(spell)) {
            player.sendOverlayMessage(Component.literal(SpellbookItem.getSpellName(spell) + " can only be inscribed into a Forbidden Grimoire"));
            return InteractionResult.FAIL;
        }
        player.sendOverlayMessage(Component.literal("You need an Arcane Codex or Forbidden Grimoire to decipher this page"));
        return InteractionResult.FAIL;
    }
}
