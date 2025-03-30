package com.example.elouancustommod.registries;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.crafting.EnchantmentTransposingRecipe;
import com.example.elouancustommod.tuto.RightClickBlockRecipe;
import com.example.elouancustommod.tuto.RightClickBlockRecipeSerializer;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
  public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
      DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElouanCustomMod.MOD_ID);

  public static final Supplier<RecipeSerializer<RightClickBlockRecipe>> RIGHT_CLICK_BLOCK =
      RECIPE_SERIALIZERS.register("right_click_block", RightClickBlockRecipeSerializer::new);

  public static void register(IEventBus eventBus) {
    RECIPE_SERIALIZERS.register(eventBus);
    EnchantmentTransposingRecipe.RECIPE_TYPES.register(eventBus);
  }
}
