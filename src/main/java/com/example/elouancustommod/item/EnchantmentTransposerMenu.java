package com.example.elouancustommod.item;

import com.example.elouancustommod.registries.ModBlocks;
import com.example.elouancustommod.registries.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class EnchantmentTransposerMenu extends AbstractContainerMenu {
  public final EnchantmentTransposerBlockEntity blockEntity;
  private final Level level;

  // Client menu constructor
  public EnchantmentTransposerMenu(
      int containerId,
      Inventory playerInventory,
      FriendlyByteBuf extraData) { // optional FriendlyByteBuf parameter if reading data from server
    this(
        containerId,
        playerInventory,
        playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
  }

  // Server menu constructor
  public EnchantmentTransposerMenu(
      int pContainerId, Inventory playerInventory, BlockEntity entity) {
    super(ModMenuTypes.TRANSPOSER_BLOCK_MENU.get(), pContainerId);
    this.blockEntity = ((EnchantmentTransposerBlockEntity) entity);
    this.level = playerInventory.player.level();
    // Slots de l'inventaire du joueur

    addPlayerInventory(playerInventory);
    addPlayerHotbar(playerInventory);

    // Slots pour les ingrédients
    this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 54, 25));
    this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 54, 43));
    this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 104, 25));
    this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 3, 104, 43));
  }

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    int VANILLA_FIRST_SLOT_INDEX = 0;
    int HOTBAR_SLOT_COUNT = 9;
    int PLAYER_INVENTORY_ROW_COUNT = 3;
    int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    int TE_INVENTORY_SLOT_COUNT = 4;

    Slot sourceSlot = slots.get(pIndex);
    if (!sourceSlot.hasItem()) return ItemStack.EMPTY; // EMPTY_ITEM
    ItemStack sourceStack = sourceSlot.getItem();
    ItemStack copyOfSourceStack = sourceStack.copy();

    // Check if the slot clicked is one of the vanilla container slots
    if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
      // This is a vanilla container slot so merge the stack into the tile inventory
      if (!moveItemStackTo(
          sourceStack,
          TE_INVENTORY_FIRST_SLOT_INDEX,
          TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT,
          false)) {
        return ItemStack.EMPTY; // EMPTY_ITEM
      }
    } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
      // This is a TE slot so merge the stack into the players inventory
      if (!moveItemStackTo(
          sourceStack,
          VANILLA_FIRST_SLOT_INDEX,
          VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,
          false)) {
        return ItemStack.EMPTY;
      }
    } else {
      System.out.println("Invalid slotIndex:" + pIndex);
      return ItemStack.EMPTY;
    }
    // If stack size == 0 (the entire stack was moved) set slot contents to null
    if (sourceStack.getCount() == 0) {
      sourceSlot.set(ItemStack.EMPTY);
    } else {
      sourceSlot.setChanged();
    }
    sourceSlot.onTake(pPlayer, sourceStack);
    return copyOfSourceStack;
  }

  @Override
  public boolean stillValid(@NotNull Player pPlayer) {
    return stillValid(
        ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
        pPlayer,
        ModBlocks.TRANSPOSER_BLOCK.get());
  }

  private void addPlayerInventory(Inventory playerInventory) {
    for (int i = 0; i < 3; ++i) {
      for (int l = 0; l < 9; ++l) {
        this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
      }
    }
  }

  private void addPlayerHotbar(Inventory playerInventory) {
    for (int i = 0; i < 9; ++i) {
      this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
    }
  }
}
