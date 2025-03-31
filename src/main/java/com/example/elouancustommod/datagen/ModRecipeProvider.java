package com.example.elouancustommod.datagen;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.registries.ModBlocks;
import com.example.elouancustommod.registries.ModItems;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
  public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries);
  }

  protected static void oreSmelting(
      RecipeOutput recipeOutput,
      List<ItemLike> pIngredients,
      RecipeCategory pCategory,
      ItemLike pResult,
      float pExperience,
      int pCookingTIme,
      String pGroup) {
    oreCooking(
        recipeOutput,
        RecipeSerializer.SMELTING_RECIPE,
        SmeltingRecipe::new,
        pIngredients,
        pCategory,
        pResult,
        pExperience,
        pCookingTIme,
        pGroup,
        "_from_smelting");
  }

  protected static void oreBlasting(
      RecipeOutput recipeOutput,
      List<ItemLike> pIngredients,
      RecipeCategory pCategory,
      ItemLike pResult,
      float pExperience,
      int pCookingTime,
      String pGroup) {
    oreCooking(
        recipeOutput,
        RecipeSerializer.BLASTING_RECIPE,
        BlastingRecipe::new,
        pIngredients,
        pCategory,
        pResult,
        pExperience,
        pCookingTime,
        pGroup,
        "_from_blasting");
  }

  protected static <T extends AbstractCookingRecipe> void oreCooking(
      RecipeOutput recipeOutput,
      RecipeSerializer<T> pCookingSerializer,
      AbstractCookingRecipe.Factory<T> factory,
      List<ItemLike> pIngredients,
      RecipeCategory pCategory,
      ItemLike pResult,
      float pExperience,
      int pCookingTime,
      String pGroup,
      String pRecipeName) {
    for (ItemLike itemlike : pIngredients) {
      SimpleCookingRecipeBuilder.generic(
              Ingredient.of(itemlike),
              pCategory,
              pResult,
              pExperience,
              pCookingTime,
              pCookingSerializer,
              factory)
          .group(pGroup)
          .unlockedBy(getHasName(itemlike), has(itemlike))
          .save(
              recipeOutput,
              ElouanCustomMod.MOD_ID
                  + ":"
                  + getItemName(pResult)
                  + pRecipeName
                  + "_"
                  + getItemName(itemlike));
    }
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANTEMENT_TRANSPOSER.get())
        .pattern("BBB")
        .pattern("BoB")
        .pattern("BBB")
        .define('B', Items.LAPIS_LAZULI)
        .define('o', Items.BOOK)
        .unlockedBy("has_lapis", has(Items.LAPIS_LAZULI))
        .save(recipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TRANSPOSER_BLOCK.get())
        .pattern("BBB")
        .pattern("B B")
        .pattern("BBB")
        .define('B', Items.LAPIS_BLOCK)
        .unlockedBy("has_lapis", has(Items.LAPIS_BLOCK))
        .save(recipeOutput);
  }
}
