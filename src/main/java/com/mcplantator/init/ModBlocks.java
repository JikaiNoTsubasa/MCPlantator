package com.mcplantator.init;

import com.mcplantator.MCPlantator;
import com.mcplantator.blocks.BotanicalWorkbenchBlock;
import com.mcplantator.blocks.GunpowderCropBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, MCPlantator.MOD_ID);

    // Gunpowder Crop (no item form)
    public static final RegistryObject<Block> GUNPOWDER_CROP = BLOCKS.register("gunpowder_crop",
            () -> new GunpowderCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    // Botanical Workbench
    public static final RegistryObject<Block> BOTANICAL_WORKBENCH = BLOCKS.register("botanical_workbench",
            () -> new BotanicalWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    // Block items
    public static final RegistryObject<Item> BOTANICAL_WORKBENCH_ITEM =
        ModItems.ITEMS.register("botanical_workbench",
            () -> new BlockItem(BOTANICAL_WORKBENCH.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
