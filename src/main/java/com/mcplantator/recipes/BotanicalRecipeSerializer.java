package com.mcplantator.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Serializer for BotanicalRecipe.
 * Handles JSON parsing and network serialization.
 */
public class BotanicalRecipeSerializer implements RecipeSerializer<BotanicalRecipe> {
    @Override
    public @NotNull BotanicalRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        Ingredient ingredientTop = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient_top"));
        Ingredient ingredientBottom = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient_bottom"));
        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

        return new BotanicalRecipe(recipeId, ingredientTop, ingredientBottom, result);
    }

    @Override
    public @Nullable BotanicalRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        Ingredient ingredientTop = Ingredient.fromNetwork(buffer);
        Ingredient ingredientBottom = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();

        return new BotanicalRecipe(recipeId, ingredientTop, ingredientBottom, result);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull BotanicalRecipe recipe) {
        recipe.getIngredientTop().toNetwork(buffer);
        recipe.getIngredientBottom().toNetwork(buffer);
        buffer.writeItem(recipe.getResultItem(null));
    }
}
