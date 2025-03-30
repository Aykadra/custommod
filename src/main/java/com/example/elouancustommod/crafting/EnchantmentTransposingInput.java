package com.example.elouancustommod.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record EnchantmentTransposingInput(Ingredient transposer, Ingredient enchantedItem)
    implements RecipeInput {
  @Override
  public @NotNull ItemStack getItem(int slot) {
    return switch (slot) {
      case 0 -> transposer.getItems()[0];
      case 1 -> enchantedItem.getItems()[0];
      default -> ItemStack.EMPTY;
    };
  }

  @Override
  public int size() {
    return 2;
  }
}
