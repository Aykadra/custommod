package com.example.elouancustommod.item.custom;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentTransposer extends Item {

  public EnchantmentTransposer() {
    this(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
  }

  protected EnchantmentTransposer(Properties properties) {
    super(properties);
  }

  @Override
  public void appendHoverText(
      ItemStack pStack,
      TooltipContext pContext,
      List<Component> pTooltipComponents,
      TooltipFlag pTooltipFlag) {
    pTooltipComponents.add(
        Component.translatable("tooltip.elouancustommod.enchantment_transposer"));
    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
  }

  public boolean canTranspose(Holder<Enchantment> enchant) {
    return true;
  }

  public CreativeModeTab getCreativeTab() {
    throw new UnsupportedOperationException("Unimplemented method 'getCreativeTab'");
  }
}
