package com.mcplantator;

import com.mcplantator.init.ModBlocks;
import com.mcplantator.init.ModItems;
import com.mcplantator.init.ModBlockEntities;
import com.mcplantator.init.ModMenuTypes;
import com.mcplantator.init.ModRecipes;
import com.mcplantator.init.ModCreativeTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MCPlantator.MOD_ID)
public class MCPlantator {
    public static final String MOD_ID = "mcplantator";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MCPlantator() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register deferred registers
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        // Register mod lifecycle listeners
        modEventBus.addListener(this::commonSetup);

        // Register to the Forge event bus
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("MCPlantator mod initialized!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("MCPlantator common setup complete!");
    }
}
