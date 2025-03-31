package com.example.elouancustommod.item;

import com.example.elouancustommod.registries.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class EnchantmentTransposerBlockEntity extends BlockEntity implements MenuProvider {
  private static final int INPUT_TRANPOSER_SLOT = 0;
  private static final int INPUT_ECHANTED_ITEM_SLOT = 1;
  private static final int OUTPUT_ECHANTED_BOOK_SLOT = 2;
  private static final int OUTPUT_ITEM_SLOT = 3;
  public final ItemStackHandler itemHandler =
      new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
          setChanged();
          if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
          }
        }
      };
  private int value;

  public EnchantmentTransposerBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.TRANSPOSER_BLOCK_BE.get(), pPos, pBlockState);
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("block.elouancustommod.transposer_block");
  }

  public void drops() {
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
    for (int i = 0; i < itemHandler.getSlots(); i++) {
      inventory.setItem(i, itemHandler.getStackInSlot(i));
    }

    Containers.dropContents(this.level, this.worldPosition, inventory);
  }

  @Override
  public @Nullable AbstractContainerMenu createMenu(
      int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
    return new EnchantmentTransposerMenu(pContainerId, pPlayerInventory, this);
  }

  @Override
  public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.loadAdditional(pTag, pRegistries);

    itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
  }

  @Override
  public void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
    super.saveAdditional(pTag, pRegistries);
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
    return saveWithoutMetadata(pRegistries);
  }

  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }
}
