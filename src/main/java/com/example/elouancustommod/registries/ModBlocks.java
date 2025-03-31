package com.example.elouancustommod.registries;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.item.EnchantmentTransposerBlock;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
  public static final DeferredRegister.Blocks BLOCKS =
      DeferredRegister.createBlocks(ElouanCustomMod.MOD_ID);

  public static final DeferredBlock<Block> TRANSPOSER_BLOCK =
      registerBlock(
          "transposer_block", () -> new EnchantmentTransposerBlock(BlockBehaviour.Properties.of()));

  private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
    DeferredBlock<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
  }

  private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
    ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
  }

  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
