package com.example.elouancustommod.datagen;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.registries.ModItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModItemTagProvider extends ItemTagsProvider {
  public ModItemTagProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> lookupProvider,
      CompletableFuture<TagLookup<Block>> blockTags,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, blockTags, ElouanCustomMod.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {

    tag(ItemTags.BOOKSHELF_BOOKS).add(ModItems.ENCHANTEMENT_TRANSPOSER.get());
  }
}
