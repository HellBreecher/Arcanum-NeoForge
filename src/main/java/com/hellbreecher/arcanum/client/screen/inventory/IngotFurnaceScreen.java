package com.hellbreecher.arcanum.client.screen.inventory;

import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import com.hellbreecher.arcanum.common.blocks.container.IngotFurnaceMenu;
import net.minecraft.resources.Identifier;
import java.util.List;

public class IngotFurnaceScreen extends AbstractFurnaceScreen<IngotFurnaceMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/container/furnace.png");
    private static final Identifier LIT_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath("minecraft", "container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath("minecraft", "container/furnace/burn_progress");

    public IngotFurnaceScreen(IngotFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, title, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE, List.of());
    }

    @Override
    public void render(net.minecraft.client.gui.GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
