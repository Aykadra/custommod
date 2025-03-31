package com.example.elouancustommod.registries;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.item.EnchantmentTransposerBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ElouanCustomMod.MOD_ID);

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }  public static final Supplier<BlockEntityType<EnchantmentTransposerBlockEntity>>
      TRANSPOSER_BLOCK_BE =
          BLOCK_ENTITIES.register(
              "transposer_block_be",
              () ->
                  BlockEntityType.Builder.of(
                          EnchantmentTransposerBlockEntity::new, ModBlocks.TRANSPOSER_BLOCK.get())
                      .build(null));


}
