package com.example.elouancustommod.registries;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.item.EnchantmentTransposerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
  public static final DeferredRegister<MenuType<?>> MENUS =
      DeferredRegister.create(Registries.MENU, ElouanCustomMod.MOD_ID);

  private static <T extends AbstractContainerMenu>
      DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(
          String name, IContainerFactory<T> factory) {
    return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
  }

  public static void register(IEventBus eventBus) {
    MENUS.register(eventBus);
  }

  public static final DeferredHolder<MenuType<?>, MenuType<EnchantmentTransposerMenu>>
      TRANSPOSER_BLOCK_MENU =
          registerMenuType("transposer_block_menu", EnchantmentTransposerMenu::new);
}
