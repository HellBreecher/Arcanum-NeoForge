package com.hellbreecher.arcanum.client.screen.inventory;

import com.hellbreecher.arcanum.common.blocks.container.SapphireGeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class SapphireGeneratorScreen extends AbstractContainerScreen<SapphireGeneratorMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("arcanum", "textures/gui/generator.png");
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    private static final int BURN_U = 176;
    private static final int BURN_V = 0;
    private static final int BURN_X = 57;
    private static final int BURN_Y = 37;
    private static final int BURN_WIDTH = 14;
    private static final int BURN_HEIGHT = 14;

    private static final int ENERGY_U = 176;
    private static final int ENERGY_V = 14;
    private static final int ENERGY_X = 81;
    private static final int ENERGY_Y = 14;
    private static final int ENERGY_WIDTH = 14;
    private static final int ENERGY_HEIGHT = 60;

    public SapphireGeneratorScreen(SapphireGeneratorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
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
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left, top, 0.0F, 0.0F, this.imageWidth, this.imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        int burnTime = this.menu.getBurnTime();
        int burnTotal = Math.max(1, this.menu.getBurnTimeTotal());
        if (burnTime > 0) {
            int burnHeight = Mth.clamp(Math.round(BURN_HEIGHT * (burnTime / (float) burnTotal)), 0, BURN_HEIGHT);
            if (burnHeight > 0) {
                int yOffset = BURN_HEIGHT - burnHeight;
                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        TEXTURE,
                        left + BURN_X,
                        top + BURN_Y + yOffset,
                        BURN_U,
                        BURN_V + yOffset,
                        BURN_WIDTH,
                        burnHeight,
                        TEXTURE_WIDTH,
                        TEXTURE_HEIGHT
                );
            }
        }

        int energy = this.menu.getEnergyStored();
        int capacity = Math.max(1, this.menu.getEnergyCapacity());
        int fill = Mth.clamp(Math.round(ENERGY_HEIGHT * (energy / (float) capacity)), 0, ENERGY_HEIGHT);
        if (fill > 0) {
            int yOffset = ENERGY_HEIGHT - fill;
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    TEXTURE,
                    left + ENERGY_X,
                    top + ENERGY_Y + yOffset,
                    ENERGY_U,
                    ENERGY_V + yOffset,
                    ENERGY_WIDTH,
                    fill,
                    TEXTURE_WIDTH,
                    TEXTURE_HEIGHT
            );
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderEnergyTooltip(graphics, mouseX, mouseY);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    private void renderEnergyTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        int x0 = left + ENERGY_X;
        int y0 = top + ENERGY_Y;
        int x1 = x0 + ENERGY_WIDTH;
        int y1 = y0 + ENERGY_HEIGHT;
        if (mouseX >= x0 && mouseX < x1 && mouseY >= y0 && mouseY < y1) {
            int energy = this.menu.getEnergyStored();
            int capacity = this.menu.getEnergyCapacity();
            Component text = Component.literal(energy + " / " + capacity + " FE")
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5FD3FF)));
            graphics.setTooltipForNextFrame(this.font, text, mouseX, mouseY);
        }
    }
}
