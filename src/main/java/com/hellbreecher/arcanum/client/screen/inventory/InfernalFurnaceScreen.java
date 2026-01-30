package com.hellbreecher.arcanum.client.screen.inventory;

import com.hellbreecher.arcanum.common.blocks.container.InfernalFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class InfernalFurnaceScreen extends AbstractContainerScreen<InfernalFurnaceMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("arcanum", "textures/gui/infernalfurnace.png");

    public InfernalFurnaceScreen(InfernalFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 20, this.height / 2 - 49);
    }
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left = this.leftPos;
        int top = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left, top, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        int progressWidth = Math.round(this.menu.getCookProgress() * 24.0F);
        if (progressWidth > 0) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left + 79, top + 35, 176.0F, 14.0F, progressWidth, 16, 256, 256);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
