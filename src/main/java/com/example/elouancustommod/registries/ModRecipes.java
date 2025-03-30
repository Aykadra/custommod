package com.example.elouancustommod.registries;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.crafting.EnchantmentTransposingRecipe;
import com.example.elouancustommod.crafting.EnchantmentTransposingRecipeSerializer;
import com.example.elouancustommod.tuto.RightClickBlockRecipe;
import com.example.elouancustommod.tuto.RightClickBlockRecipeSerializer;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
      DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElouanCustomMod.MOD_ID);
  public static final DeferredRegister<RecipeType<?>> TYPES =
      DeferredRegister.create(Registries.RECIPE_TYPE, ElouanCustomMod.MOD_ID);

  public static final Supplier<RecipeSerializer<RightClickBlockRecipe>>
      RIGHT_CLICK_BLOCK_RECIPE_SERIALIZER =
          SERIALIZERS.register("right_click_block", RightClickBlockRecipeSerializer::new);
  public static final Supplier<RecipeSerializer<EnchantmentTransposingRecipe>>
      ENCHANTMENT_TRANSPOSING_RECIPE_SERIALIZER =
          SERIALIZERS.register(
              "enchantment_transposing", EnchantmentTransposingRecipeSerializer::new);

  public static final Supplier<RecipeType<RightClickBlockRecipe>> RIGHT_CLICK_BLOCK_RECIPE_TYPE =
      TYPES.register(
          "right_click_block",
          () ->
              RecipeType.<RightClickBlockRecipe>simple(
                  ResourceLocation.fromNamespaceAndPath(
                      ElouanCustomMod.MOD_ID, "right_click_block")));
  public static final Supplier<RecipeType<EnchantmentTransposingRecipe>>
      ENCHANTMENT_TRANSPOSING_RECIPE_TYPE =
          TYPES.register(
              "enchantment_transposing",
              () ->
                  RecipeType.<EnchantmentTransposingRecipe>simple(
                      ResourceLocation.fromNamespaceAndPath(
                          ElouanCustomMod.MOD_ID, "enchantment_transposing")));

  public static void register(IEventBus eventBus) {
    SERIALIZERS.register(eventBus);
    TYPES.register(eventBus);
  }
}
