package com.mcplantator.init;

import com.mcplantator.MCPlantator;
import com.mcplantator.recipes.BotanicalRecipe;
import com.mcplantator.recipes.BotanicalRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MCPlantator.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BotanicalRecipe>> BOTANICAL_SERIALIZER =
        SERIALIZERS.register("botanical", BotanicalRecipeSerializer::new);

    public static final RecipeType<BotanicalRecipe> BOTANICAL_TYPE =
        new RecipeType<>() {
            @Override
            public String toString() {
                return MCPlantator.MOD_ID + ":botanical";
            }
        };

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
