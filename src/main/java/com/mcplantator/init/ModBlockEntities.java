package com.mcplantator.init;

import com.mcplantator.MCPlantator;
import com.mcplantator.blockentity.BotanicalWorkbenchBlockEntity;
import com.mcplantator.blockentities.AncientDebrisExtractorBlockEntity;
import com.mcplantator.blockentities.IronExtractorBlockEntity;
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

    public static final RegistryObject<BlockEntityType<IronExtractorBlockEntity>> IRON_EXTRACTOR =
        BLOCK_ENTITIES.register("iron_extractor", () ->
            BlockEntityType.Builder.of(IronExtractorBlockEntity::new,
                ModBlocks.IRON_EXTRACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<AncientDebrisExtractorBlockEntity>> ANCIENT_DEBRIS_EXTRACTOR =
        BLOCK_ENTITIES.register("ancient_debris_extractor", () ->
            BlockEntityType.Builder.of(AncientDebrisExtractorBlockEntity::new,
                ModBlocks.ANCIENT_DEBRIS_EXTRACTOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
