package com.mcplantator.init;

import com.mcplantator.MCPlantator;
import com.mcplantator.recipes.BotanicalRecipe;
import com.mcplantator.recipes.BotanicalRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MCPlantator.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
        DeferredRegister.create(Registries.RECIPE_TYPE, MCPlantator.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BotanicalRecipe>> BOTANICAL_SERIALIZER =
        SERIALIZERS.register("botanical", BotanicalRecipeSerializer::new);

    public static final RegistryObject<RecipeType<BotanicalRecipe>> BOTANICAL_TYPE =
        RECIPE_TYPES.register("botanical", () -> new RecipeType<>() {
            @Override
            public String toString() {
                return MCPlantator.MOD_ID + ":botanical";
            }
        });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
