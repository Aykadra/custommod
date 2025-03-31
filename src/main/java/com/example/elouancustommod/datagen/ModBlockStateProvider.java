package com.example.elouancustommod.datagen;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
  public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, ElouanCustomMod.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    blockWithItem(ModBlocks.TRANSPOSER_BLOCK);
  }

  private void blockWithItem(DeferredBlock<?> deferredBlock) {
    simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
  }

  private void blockItem(DeferredBlock<?> deferredBlock) {
    simpleBlockItem(
        deferredBlock.get(),
        new ModelFile.UncheckedModelFile(
            "elouancustommod:block/" + deferredBlock.getId().getPath()));
  }

  private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
    simpleBlockItem(
        deferredBlock.get(),
        new ModelFile.UncheckedModelFile(
            "elouancustommod:block/" + deferredBlock.getId().getPath() + appendix));
  }
}
