package com.mcplantator.client.screen;

import com.mcplantator.MCPlantator;
import com.mcplantator.container.BotanicalWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Screen (GUI) for the Botanical Workbench.
 */
public class BotanicalWorkbenchScreen extends AbstractContainerScreen<BotanicalWorkbenchMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MCPlantator.MOD_ID, "textures/gui/botanical_workbench_gui.png");

    public BotanicalWorkbenchScreen(BotanicalWorkbenchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Centrer le titre
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }
}
