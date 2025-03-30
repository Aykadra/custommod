package com.example.elouancustommod;

import com.example.elouancustommod.registries.ModItems;
import com.example.elouancustommod.registries.ModRecipes;
import com.example.elouancustommod.tuto.EventHandler;
import com.mojang.logging.LogUtils;
import java.util.stream.Collectors;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ElouanCustomMod.MOD_ID)
public class ElouanCustomMod {
  public static final String MOD_ID = "elouancustommod";
  public static final Logger LOGGER = LogUtils.getLogger();

  public ElouanCustomMod(IEventBus modEventBus, ModContainer modContainer) {
    // Register the commonSetup method for modloading
    modEventBus.addListener(this::commonSetup);

    NeoForge.EVENT_BUS.register(this);
    NeoForge.EVENT_BUS.register(EventHandler.class);
    ModItems.register(modEventBus);
    ModRecipes.register(modEventBus);

    // Register the item to a creative tab
    modEventBus.addListener(this::addCreative);
    LOGGER.debug("Test de logging - Si vous voyez ce message, le logging fonctionne");
    LOGGER.info("Niveau INFO");
    LOGGER.warn("Niveau WARN");
    LOGGER.error("Niveau ERROR");
    //
    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  public static Logger getLogger() {
    return LOGGER;
  }

  private void commonSetup(final FMLCommonSetupEvent event) {}

  // Add the example block item to the building blocks tab
  private void addCreative(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
      event.accept(ModItems.ENCHANTEMENT_TRANSPOSER);
    }
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    MinecraftServer serverLevel = ServerLifecycleHooks.getCurrentServer();
    if (serverLevel != null) {

      RecipeManager recipes = serverLevel.getRecipeManager();
      ;
      // Like before, pass the desired recipe type.
      LOGGER.debug(
          "Types de recettes enregistrés: {}",
          recipes.getOrderedRecipes().stream()
              .map(RecipeHolder::toString)
              .collect(Collectors.joining(", ")));
    }
  }

  // You can use EventBusSubscriber to automatically register all static methods
  // in the class annotated with @SubscribeEvent
  @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {}
  }
}
