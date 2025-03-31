package com.example.elouancustommod.registries;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.crafting.EnchantmentTransposingRecipe;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
      DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElouanCustomMod.MOD_ID);

  public static final Supplier<RecipeSerializer<EnchantmentTransposingRecipe>>
      ALT_ENCHANTMENT_TRANSPOSING_RECIPE_SERIALIZER =
          SERIALIZERS.register(
              "enchantment_transposing", () -> EnchantmentTransposingRecipe.SERIALIZER);

  public static void register(IEventBus eventBus) {
    SERIALIZERS.register(eventBus);
  }
}
