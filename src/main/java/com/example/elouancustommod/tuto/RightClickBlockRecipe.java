package com.example.elouancustommod.tuto;

import com.example.elouancustommod.registries.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

// The generic parameter for Recipe<T> is our RightClickBlockInput from above.
public record RightClickBlockRecipe(BlockState inputState, Ingredient inputItem, ItemStack result)
    implements Recipe<RightClickBlockInput> {

  // Add a constructor that sets all properties.

  @Override
  public NonNullList<Ingredient> getIngredients() {
    NonNullList<Ingredient> list = NonNullList.create();
    list.add(this.inputItem);
    return list;
  }

  // Grid-based recipes should return whether their recipe can fit in the given
  // dimensions.
  // We don't have a grid, so we just return if any item can be placed in there.
  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return width * height >= 1;
  }

  // Check whether the given input matches this recipe. The first parameter
  // matches the generic.
  // We check our blockstate and our item stack, and only return true if both
  // match.
  @Override
  public boolean matches(RightClickBlockInput input, Level level) {
    return this.inputState == input.state() && this.inputItem.test(input.stack());
  }

  @Override
  public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
    return this.result;
  }

  @Override
  public @NotNull ItemStack assemble(RightClickBlockInput input, HolderLookup.Provider registries) {
    return this.result.copy();
  }

  @Override
  public @NotNull RecipeSerializer<?> getSerializer() {
    return ModRecipes.RIGHT_CLICK_BLOCK_RECIPE_SERIALIZER.get();
  }

  @Override
  public @NotNull RecipeType<?> getType() {
    return ModRecipes.RIGHT_CLICK_BLOCK_RECIPE_TYPE.get();
  }
}
