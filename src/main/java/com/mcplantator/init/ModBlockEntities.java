package com.mcplantator.init;

import com.mcplantator.MCPlantator;
import com.mcplantator.blockentity.BotanicalWorkbenchBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MCPlantator.MOD_ID);

    public static final RegistryObject<BlockEntityType<BotanicalWorkbenchBlockEntity>> BOTANICAL_WORKBENCH =
        BLOCK_ENTITIES.register("botanical_workbench", () ->
            BlockEntityType.Builder.of(BotanicalWorkbenchBlockEntity::new,
                ModBlocks.BOTANICAL_WORKBENCH.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
