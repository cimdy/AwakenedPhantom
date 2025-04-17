package com.cimdy.awakenedphantom;

import com.cimdy.awakenedphantom.effect.PotionRegister;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AwakenedPhantom.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AWAKENED_PHANTOM_TAB = CREATIVE_MODE_TABS.register("awakened_phantom_tab",
            () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + AwakenedPhantom.MODID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemRegister.PHANTOM_ELYTRA.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemRegister.PHANTOM_ELYTRA);
            }).build());

}
