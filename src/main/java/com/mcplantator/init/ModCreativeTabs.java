package com.mcplantator.init;

import com.mcplantator.MCPlantator;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MCPlantator.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MCPLANTATOR_TAB = CREATIVE_MODE_TABS.register("mcplantator",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GUNPOWDER_SEED.get()))
                    .title(Component.translatable("itemGroup.mcplantator"))
                    .displayItems((parameters, output) -> {
                        // Add all mod items to the creative tab
                        output.accept(ModItems.GUNPOWDER_SEED.get());
                        output.accept(ModBlocks.BOTANICAL_WORKBENCH_ITEM.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
