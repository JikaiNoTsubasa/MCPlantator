package com.mcplantator.client.screen;

import com.mcplantator.MCPlantator;
import com.mcplantator.menu.AncientDebrisExtractorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Screen for Ancient Debris Extractor GUI
 */
public class AncientDebrisExtractorScreen extends AbstractContainerScreen<AncientDebrisExtractorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MCPlantator.MOD_ID, "textures/gui/ancient_debris_extractor.png");

    public AncientDebrisExtractorScreen(AncientDebrisExtractorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Render title with custom color #c6c6c6
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xc6c6c6, false);
        // Render inventory label with custom color #c6c6c6
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0xc6c6c6, false);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Render only the visible part (176×166) from the texture
        // Texture size is 208×166 (includes the animated sprites zone)
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 208, 166);

        renderProgressArrow(guiGraphics, x, y);
        renderFuelBar(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            int progress = menu.getScaledProgress();
            // Arrow goes from left to right (horizontal)
            // Sprite starts at x=177 in the texture
            guiGraphics.blit(TEXTURE, x + 80, y + 35, 177, 0, progress, 16, 208, 166);
        }
    }

    private void renderFuelBar(GuiGraphics guiGraphics, int x, int y) {
        int fuelProgress = menu.getScaledFuelProgress();
        if (fuelProgress > 0) {
            // Fuel bar goes from bottom to top (vertical)
            // Sprite starts at x=177, y=16 in the texture
            guiGraphics.blit(TEXTURE, x + 56, y + 36 + (14 - fuelProgress), 177, 16 + (14 - fuelProgress), 14, fuelProgress, 208, 166);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
