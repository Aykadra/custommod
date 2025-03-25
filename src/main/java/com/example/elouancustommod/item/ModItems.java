package com.example.elouancustommod.item;

import com.example.elouancustommod.ElouanCustomMod;
import com.example.elouancustommod.EnchantmentTransposer;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ElouanCustomMod.MOD_ID);

    public static final DeferredItem<Item> ENCHANTEMENT_TRANSPOSER = ITEMS.register("enchantment_transposer",
            () -> new EnchantmentTransposer());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
