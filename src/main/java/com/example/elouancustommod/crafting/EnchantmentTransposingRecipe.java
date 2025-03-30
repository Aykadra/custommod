package com.example.elouancustommod.crafting;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.item.EnchantmentTransposer;
import com.example.elouancustommod.registries.ModRecipes;
import java.util.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class EnchantmentTransposingRecipe implements Recipe<EnchantmentTransposingInput> {
  private final Ingredient transposer;
  private final Ingredient enchantedItem;
  private final ItemStack result;

  public EnchantmentTransposingRecipe(
      Ingredient transposer, Ingredient enchantedItem, ItemStack result) {

    ElouanCustomMod.LOGGER.debug("Construction de ma classe");
    this.transposer = transposer;
    this.enchantedItem = enchantedItem;
    this.result = result;
  }

  @Override
  public @NotNull NonNullList<Ingredient> getIngredients() {
    ElouanCustomMod.LOGGER.debug("Tentative de getIngredients");
    NonNullList<Ingredient> list = NonNullList.create();
    list.add(this.transposer);
    list.add(this.enchantedItem);
    return list;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    ElouanCustomMod.LOGGER.debug("Tentative de canCraftInDimensions");
    return width * height >= 2;
  }

  @Override
  public boolean matches(EnchantmentTransposingInput inv, @NotNull Level world) {
    ElouanCustomMod.LOGGER.debug("Tentative matches");
    ElouanCustomMod.LOGGER.debug("  - Transposer: {}", inv.getItem(0));
    ElouanCustomMod.LOGGER.debug("  - Item enchanté: {}", inv.getItem(1));

    List<ItemStack> stackList = new ArrayList<>();
    ItemStack pTransposer = null;

    for (int i = 0; i < inv.size(); i++) {
      ItemStack checkedItemStack = inv.getItem(i);
      if (checkedItemStack.isEmpty()) {
        continue;
      }
      if (!(checkedItemStack.getItem() instanceof EnchantmentTransposer)) {
        stackList.add(checkedItemStack);
        continue;
      }
      if (pTransposer != null) {
        ElouanCustomMod.LOGGER.debug("  - Erreur: Plusieurs transposers détectés");
        return false;
      }
      // Le premier transposer présent a été trouvé
      pTransposer = checkedItemStack.copy();
      // on le mets dans pTransposer

    }
    if (pTransposer == null) {
      ElouanCustomMod.LOGGER.debug("  - Pas de transposer");
      return false;
    }

    if (stackList.size() != 1 || !stackList.getFirst().isEnchanted()) {
      ElouanCustomMod.LOGGER.debug("  - Recette invalide");
      return false;
    }
    ElouanCustomMod.LOGGER.debug("  - Recette valide");
    return this.canDisenchant(pTransposer, stackList.getFirst());
  }

  @Override
  public @NotNull ItemStack getResultItem(Provider pRegistries) {
    ElouanCustomMod.LOGGER.debug("Tentative de getResultItem");
    return this.result;
  }

  @Override
  public @NotNull ItemStack assemble(EnchantmentTransposingInput pInput, Provider access) {
    ElouanCustomMod.LOGGER.debug("=== Assemblage de la recette ===");
    List<ItemStack> stackList = new ArrayList<ItemStack>();
    ItemStack pTransposer = null;

    for (int i = 0; i < pInput.size(); i++) {
      ItemStack checkedItemStack = pInput.getItem(i);

      if (!checkedItemStack.isEmpty()) {
        ElouanCustomMod.LOGGER.debug("Super patate");
        if (checkedItemStack.getItem() instanceof EnchantmentTransposer) {
          ElouanCustomMod.LOGGER.debug("Un tome est détecté");
          if (pTransposer == null) {
            pTransposer = checkedItemStack.copy();
          } else return ItemStack.EMPTY;
        } else {
          stackList.add(checkedItemStack);
        }
      }
    }

    if (pTransposer != null
        && stackList.size() == 1
        && stackList.getFirst().isEnchanted()
        && this.canDisenchant(pTransposer, stackList.getFirst())) {
      ItemStack enchantedBook = this.disenchant(pTransposer, stackList.getFirst()).getA();
      ElouanCustomMod.LOGGER.debug("Un tome est détecté");
      return enchantedBook;
    }

    return ItemStack.EMPTY;
  }

  @Override
  public @NotNull NonNullList<ItemStack> getRemainingItems(EnchantmentTransposingInput inv) {
    ElouanCustomMod.LOGGER.debug("Tentative de getRemainingItems");

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

  private Tuple<ItemStack, ItemStack> disenchant(ItemStack pTransposer, ItemStack target) {
    ElouanCustomMod.LOGGER.debug("Tentative de disenchant");

    ItemEnchantments transposed = EnchantmentHelper.getEnchantmentsForCrafting(target);
    ItemEnchantments leftover = EnchantmentHelper.getEnchantmentsForCrafting(target);
    transposed
        .keySet()
        .removeIf(
            enchant -> !((EnchantmentTransposer) pTransposer.getItem()).canTranspose(enchant));
    leftover
        .keySet()
        .removeIf(enchant -> ((EnchantmentTransposer) pTransposer.getItem()).canTranspose(enchant));

    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
    EnchantmentHelper.setEnchantments(book, transposed);

    ItemStack item = target.copy();
    EnchantmentHelper.setEnchantments(item, leftover);

    return new Tuple<>(book, item);
  }

  private boolean canDisenchant(ItemStack transposer, ItemStack target) {
    return EnchantmentHelper.getEnchantmentsForCrafting(target).keySet().stream()
        .anyMatch(((EnchantmentTransposer) transposer.getItem())::canTranspose);
  }

  @Override
  public @NotNull RecipeSerializer<?> getSerializer() {
    return ModRecipes.ENCHANTMENT_TRANSPOSING_RECIPE_SERIALIZER.get();
  }

  @Override
  public @NotNull RecipeType<?> getType() {
    return ModRecipes.ENCHANTMENT_TRANSPOSING_RECIPE_TYPE.get();
  }

  public Ingredient transposer() {
    return transposer;
  }

  public Ingredient enchantedItem() {
    return enchantedItem;
  }

  public ItemStack result() {
    return result;
  }
}
