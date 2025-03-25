package com.example.elouancustommod.crafting;

import java.util.function.Supplier;

import com.example.elouancustommod.ElouanCustomMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, ElouanCustomMod.MOD_ID);

    public static final Supplier<RecipeSerializer<EnchantmentTransposingRecipe>> RIGHT_CLICK_BLOCK = RECIPE_SERIALIZERS
            .register("enchantment_transposing", () -> EnchantmentTransposingRecipe.SERIALIZER);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        EnchantmentTransposingRecipe.RECIPE_TYPES.register(eventBus);
    }
}
