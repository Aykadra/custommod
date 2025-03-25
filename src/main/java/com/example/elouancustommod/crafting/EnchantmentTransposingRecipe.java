package com.example.elouancustommod.crafting;

import java.util.ArrayList;
import java.util.List;

import com.example.elouancustommod.EnchantmentTransposer;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

/**
 * Special recipe type for transposing enchantments.
 * 
 * @author Integral
 */

public class EnchantmentTransposingRecipe extends CustomRecipe {
	public static final SimpleCraftingRecipeSerializer<EnchantmentTransposingRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(
			EnchantmentTransposingRecipe::new);

	public EnchantmentTransposingRecipe(CraftingBookCategory ctg) {
		super(ctg);
	}

	@SuppressWarnings("null")
	@Override
	public ItemStack assemble(CraftingInput inv, Provider access) {
		List<ItemStack> stackList = new ArrayList<ItemStack>();
		ItemStack transposer = null;

		for (int i = 0; i < inv.size(); i++) {
			ItemStack checkedItemStack = inv.getItem(i);

			if (!checkedItemStack.isEmpty()) {
				if (checkedItemStack.getItem() instanceof EnchantmentTransposer) {
					if (transposer == null) {
						transposer = checkedItemStack.copy();
					} else
						return ItemStack.EMPTY;
				} else {
					stackList.add(checkedItemStack);
				}
			}
		}

		if (transposer != null && stackList.size() == 1 && stackList.get(0).isEnchanted()
				&& this.canDisenchant(transposer, stackList.get(0)))
			return this.disenchant(transposer, stackList.get(0)).getA();

		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@SuppressWarnings("null")
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
					} else
						return false;
				} else {
					stackList.add(checkedItemStack);
				}

			}

		}

		if (transposer != null && stackList.size() == 1 && stackList.get(0).isEnchanted()
				&& this.canDisenchant(transposer, stackList.get(0)))
			return true;

		return false;
	}

	private Tuple<ItemStack, ItemStack> disenchant(ItemStack transposer, ItemStack target) {

		ItemEnchantments transposed = EnchantmentHelper.getEnchantmentsForCrafting(target);
		ItemEnchantments leftover = EnchantmentHelper.getEnchantmentsForCrafting(target);
		transposed.keySet()
				.removeIf(enchant -> !((EnchantmentTransposer) transposer.getItem()).canTranspose(enchant));
		leftover.keySet().removeIf(enchant -> ((EnchantmentTransposer) transposer.getItem()).canTranspose(enchant));

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

}
