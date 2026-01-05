package com.mcplantator.client;

import com.mcplantator.MCPlantator;
import com.mcplantator.client.screen.AncientDebrisExtractorScreen;
import com.mcplantator.client.screen.BotanicalWorkbenchScreen;
import com.mcplantator.client.screen.IronExtractorScreen;
import com.mcplantator.init.ModBlocks;
import com.mcplantator.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MCPlantator.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.BOTANICAL_WORKBENCH.get(), BotanicalWorkbenchScreen::new);
            MenuScreens.register(ModMenuTypes.IRON_EXTRACTOR.get(), IronExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.ANCIENT_DEBRIS_EXTRACTOR.get(), AncientDebrisExtractorScreen::new);

            // Set render layer for transparent textures
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.GUNPOWDER_CROP.get(), RenderType.cutout());
        });
    }
}
