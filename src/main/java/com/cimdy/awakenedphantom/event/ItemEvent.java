package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class ItemEvent {
    @SubscribeEvent
    public static void ItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().is(ItemRegister.PHANTOM_ELYTRA)) {
            event.getToolTip().add(Component.empty());
            event.getToolTip().add(
                    Component.translatable("item." + AwakenedPhantom.MODID + ".phantom_elytra.tooltip")
                            .withStyle(ChatFormatting.GRAY));
        }
    }
}
