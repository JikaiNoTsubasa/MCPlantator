package com.mcplantator.client.screen;

import com.mcplantator.MCPlantator;
import com.mcplantator.menu.IronExtractorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Screen for Iron Extractor GUI
 */
public class IronExtractorScreen extends AbstractContainerScreen<IronExtractorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MCPlantator.MOD_ID, "textures/gui/iron_extractor.png");

    public IronExtractorScreen(IronExtractorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        // Add custom widgets here if needed
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderFuelBar(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            int progress = menu.getScaledProgress();
            guiGraphics.blit(TEXTURE, x + 80, y + 35, 176, 0, 16, progress);
        }
    }

    private void renderFuelBar(GuiGraphics guiGraphics, int x, int y) {
        int fuelProgress = menu.getScaledFuelProgress();
        if (fuelProgress > 0) {
            guiGraphics.blit(TEXTURE, x + 57, y + 37 + (14 - fuelProgress), 176, 14 - fuelProgress, 14, fuelProgress + 1);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
