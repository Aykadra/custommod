package com.example.elouancustommod.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EnchantmentTransposerBlock extends Block implements EntityBlock {
  public static final MapCodec<EnchantmentTransposerBlock> CODEC =
      simpleCodec(EnchantmentTransposerBlock::new);

  public EnchantmentTransposerBlock(Properties pProperties) {
    super(pProperties);
  }

  @Override
  protected MapCodec<? extends Block> codec() {
    return CODEC;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new EnchantmentTransposerBlockEntity(pPos, pState);
  }

  @Override
  protected RenderShape getRenderShape(BlockState pState) {
    return RenderShape.MODEL;
  }

  @Override
  public void onRemove(
      BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
    if (pState.getBlock() != pNewState.getBlock()) {
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if (blockEntity
          instanceof EnchantmentTransposerBlockEntity enchantmentTransposerBlockEntity) {
        enchantmentTransposerBlockEntity.drops();
      }
    }

    super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
  }

  @Override
  protected ItemInteractionResult useItemOn(
      ItemStack pStack,
      BlockState pState,
      Level pLevel,
      BlockPos pPos,
      Player pPlayer,
      InteractionHand pHand,
      BlockHitResult pHitResult) {
    if (!pLevel.isClientSide()) {
      BlockEntity entity = pLevel.getBlockEntity(pPos);
      if (entity instanceof EnchantmentTransposerBlockEntity enchantmentTransposerBlockEntity) {
        ((ServerPlayer) pPlayer)
            .openMenu(
                new SimpleMenuProvider(
                    enchantmentTransposerBlockEntity, Component.literal("Growth Chamber")),
                pPos);
      } else {
        throw new IllegalStateException("Our Container provider is missing!");
      }
    }

    return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
  }
}
