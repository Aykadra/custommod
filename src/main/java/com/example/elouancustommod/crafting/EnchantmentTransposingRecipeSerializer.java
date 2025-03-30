package com.example.elouancustommod.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class EnchantmentTransposingRecipeSerializer
    implements RecipeSerializer<EnchantmentTransposingRecipe> {
  public static final MapCodec<EnchantmentTransposingRecipe> CODEC =
      RecordCodecBuilder.mapCodec(
          inst ->
              inst.group(
                      Ingredient.CODEC
                          .fieldOf("transposer")
                          .forGetter(EnchantmentTransposingRecipe::transposer),
                      Ingredient.CODEC
                          .fieldOf("enchanted_item")
                          .forGetter(EnchantmentTransposingRecipe::enchantedItem),
                      ItemStack.CODEC
                          .fieldOf("result")
                          .forGetter(EnchantmentTransposingRecipe::result))
                  .apply(inst, EnchantmentTransposingRecipe::new));

  @Override
  public MapCodec<EnchantmentTransposingRecipe> codec() {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, EnchantmentTransposingRecipe> streamCodec() {
    return StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        EnchantmentTransposingRecipe::transposer,
        Ingredient.CONTENTS_STREAM_CODEC,
        EnchantmentTransposingRecipe::enchantedItem,
        ItemStack.STREAM_CODEC,
        EnchantmentTransposingRecipe::result,
        EnchantmentTransposingRecipe::new);
  }
}
