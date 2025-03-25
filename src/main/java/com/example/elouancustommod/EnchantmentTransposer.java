package com.example.elouancustommod;

// import java.util.List;

// import javax.annotation.Nullable;

import com.example.elouancustommod.item.generic.ItemBase;

import net.minecraft.core.Holder;
import net.minecraft.world.item.CreativeModeTab;
// import net.minecraft.client.gui.screens.Screen;
// import net.minecraft.network.chat.Component;
// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
// import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
// import net.minecraft.world.level.Level;
// import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
// import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
// import net.minecraftforge.api.distmarker.Dist;
// import net.minecraftforge.api.distmarker.OnlyIn;

public class EnchantmentTransposer extends ItemBase {

    public EnchantmentTransposer() {
        this(getDefaultProperties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    protected EnchantmentTransposer(Properties properties) {
        super(properties);
    }

    // @Override
    // @OnlyIn(Dist.CLIENT)
    // public void appendHoverText(ItemStack stack, @Nullable Level worldIn,
    // List<Component> list, TooltipFlag flagIn) {
    // if (Screen.hasShiftDown()) {
    // ItemLoreHelper.addLocalizedString(list,
    // "tooltip.enigmaticlegacy.enchantmentTransposer1");
    // ItemLoreHelper.addLocalizedString(list,
    // "tooltip.enigmaticlegacy.enchantmentTransposer2");
    // ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
    // ItemLoreHelper.addLocalizedString(list,
    // "tooltip.enigmaticlegacy.enchantmentTransposer3");
    // } else {
    // ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
    // }
    // }

    public boolean canTranspose(Holder<Enchantment> enchant) {
        return true;
    }

    @Override
    public CreativeModeTab getCreativeTab() {
        throw new UnsupportedOperationException("Unimplemented method 'getCreativeTab'");
    }

}
