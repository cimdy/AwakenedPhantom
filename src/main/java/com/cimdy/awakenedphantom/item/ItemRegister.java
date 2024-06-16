package com.cimdy.awakenedphantom.item;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.cimdy.awakenedphantom.item.custom.PhantomElytra;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AwakenedPhantom.MODID);

    public static final DeferredItem<Item> PHANTOM_ELYTRA = ITEMS.register("phantom_elytra",
            () -> new PhantomElytra(new Item.Properties().durability(216).rarity(Rarity.UNCOMMON)));
}
