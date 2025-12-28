package com.mcplantator.recipes;

import com.google.gson.JsonObject;
import com.mcplantator.MCPlantator;
import com.mcplantator.init.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Custom recipe type for the Botanical Workbench.
 * Takes 2 ingredients and produces 1 result.
 */
public class BotanicalRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient ingredientTop;
    private final Ingredient ingredientBottom;
    private final ItemStack result;

    public BotanicalRecipe(ResourceLocation id, Ingredient ingredientTop, Ingredient ingredientBottom, ItemStack result) {
        this.id = id;
        this.ingredientTop = ingredientTop;
        this.ingredientBottom = ingredientBottom;
        this.result = result;
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (container.getContainerSize() < 2) {
            return false;
        }

        return ingredientTop.test(container.getItem(0)) &&
               ingredientBottom.test(container.getItem(1));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.BOTANICAL_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.BOTANICAL_TYPE.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredientTop);
        list.add(ingredientBottom);
        return list;
    }

    public Ingredient getIngredientTop() {
        return ingredientTop;
    }

    public Ingredient getIngredientBottom() {
        return ingredientBottom;
    }
}
