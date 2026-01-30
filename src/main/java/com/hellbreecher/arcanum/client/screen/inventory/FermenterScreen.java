package com.hellbreecher.arcanum.client.screen.inventory;

import com.hellbreecher.arcanum.common.blocks.container.FermenterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class FermenterScreen extends AbstractContainerScreen<FermenterMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/container/brewing_stand.png");
    private static final Identifier BREW_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/brewing_stand/brew_progress");
    private static final Identifier BUBBLES_SPRITE = Identifier.withDefaultNamespace("container/brewing_stand/bubbles");
    private static final int[] BUBBLE_LENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public FermenterScreen(FermenterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left, top, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        int brewTime = this.menu.getBrewTime();
        int brewTotal = Math.max(1, this.menu.getBrewTimeTotal());
        if (brewTime > 0) {
            int progressHeight = Mth.clamp(Math.round(28.0F * (1.0F - (float) brewTime / (float) brewTotal)), 0, 28);
            if (progressHeight > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BREW_PROGRESS_SPRITE, 9, 28, 0, 0, left + 97, top + 16, 9, progressHeight);
            }

            int bubbleIndex = (brewTime / 2) % BUBBLE_LENGTHS.length;
            int bubbleHeight = BUBBLE_LENGTHS[bubbleIndex];
            if (bubbleHeight > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUBBLES_SPRITE, 12, 29, 0, 29 - bubbleHeight, left + 63, top + 14 + 29 - bubbleHeight, 12, bubbleHeight);
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
