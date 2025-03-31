package com.example.elouancustommod.crafting;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.item.EnchantmentTransposer;
import java.util.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

public class AltEnchantmentTransposingRecipe extends CustomRecipe {
  public static final SimpleCraftingRecipeSerializer<AltEnchantmentTransposingRecipe> SERIALIZER =
      new SimpleCraftingRecipeSerializer<>(AltEnchantmentTransposingRecipe::new);

  public AltEnchantmentTransposingRecipe(CraftingBookCategory ctg) {
    super(ctg);
  }

  @Override
  public ItemStack assemble(CraftingInput inv, HolderLookup.Provider access) {
    ElouanCustomMod.LOGGER.debug("Il se passe un truc");
    List<ItemStack> stackList = new ArrayList<ItemStack>();
    ItemStack transposer = null;

    for (int i = 0; i < inv.size(); i++) {
      ItemStack checkedItemStack = inv.getItem(i);

      if (!checkedItemStack.isEmpty()) {
        if (checkedItemStack.getItem() instanceof EnchantmentTransposer) {
          if (transposer == null) {
            transposer = checkedItemStack.copy();
          } else return ItemStack.EMPTY;
        } else {
          stackList.add(checkedItemStack);
        }
      }
    }

    if (transposer != null
        && stackList.size() == 1
        && stackList.get(0).isEnchanted()
        && this.canDisenchant(transposer, stackList.get(0)))
      return this.disenchant(transposer, stackList.get(0)).getA();

    return ItemStack.EMPTY;
  }

  @Override
  public boolean matches(CraftingInput inv, Level world) {
    List<ItemStack> stackList = new ArrayList<ItemStack>();
    ItemStack transposer = null;

    for (int i = 0; i < inv.size(); i++) {
      ItemStack checkedItemStack = inv.getItem(i);

      if (!checkedItemStack.isEmpty()) {
        if (checkedItemStack.getItem() instanceof EnchantmentTransposer) {
          if (transposer == null) {
            transposer = checkedItemStack.copy();
          } else return false;
        } else {
          stackList.add(checkedItemStack);
        }
      }
    }

    if (transposer != null
        && stackList.size() == 1
        && stackList.get(0).isEnchanted()
        && this.canDisenchant(transposer, stackList.get(0))) return true;

    return false;
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
    NonNullList<ItemStack> remaining = NonNullList.withSize(inv.size(), ItemStack.EMPTY);
    Map<ItemStack, Integer> stackList = new HashMap<>();
    ItemStack transposer = null;

    for (int i = 0; i < inv.size(); i++) {
      ItemStack checkedItemStack = inv.getItem(i);

      if (!checkedItemStack.isEmpty()) {
        if (checkedItemStack.getItem() instanceof EnchantmentTransposer) {
          if (transposer == null) {
            transposer = checkedItemStack.copy();
          } else return remaining;
        } else {
          stackList.put(checkedItemStack, i);
        }
      }
    }

    if (transposer != null && stackList.size() == 1) {
      ItemStack returned = stackList.keySet().iterator().next();

      if (returned.isEnchanted() && this.canDisenchant(transposer, returned)) {
        remaining.set(stackList.get(returned), this.disenchant(transposer, returned).getB());
      }
    }

    return remaining;
  }

  private Tuple<ItemStack, ItemStack> disenchant(ItemStack transposer, ItemStack target) {
    // Créer des copies modifiables des enchantements
    ItemEnchantments currentEnchantment = EnchantmentHelper.getEnchantmentsForCrafting(target);
    ItemEnchantments.Mutable leftoverEnchantment = new ItemEnchantments.Mutable(currentEnchantment);
    ItemEnchantments.Mutable transposedEnchantment =
        new ItemEnchantments.Mutable(currentEnchantment);

    // Filtrer les enchantements
    transposedEnchantment.removeIf(
        enchant -> !((EnchantmentTransposer) transposer.getItem()).canTranspose(enchant));
    leftoverEnchantment.removeIf(
        enchant -> ((EnchantmentTransposer) transposer.getItem()).canTranspose(enchant));

    // Créer le livre enchanté
    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
    EnchantmentHelper.setEnchantments(book, currentEnchantment);

    // Créer l'item avec les enchantements restants
    ItemStack item = target.copy();
    EnchantmentHelper.setEnchantments(item, leftoverEnchantment.toImmutable());

    return new Tuple<>(book, item);
  }

  private boolean canDisenchant(ItemStack transposer, ItemStack target) {
    return EnchantmentHelper.getEnchantmentsForCrafting(target).keySet().stream()
        .anyMatch(((EnchantmentTransposer) transposer.getItem())::canTranspose);
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return width * height >= 2;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return SERIALIZER;
  }

  @Override
  public RecipeType<?> getType() {
    return super.getType();
  }
}
