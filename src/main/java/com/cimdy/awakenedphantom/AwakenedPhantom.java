package com.cimdy.awakenedphantom;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import com.cimdy.awakenedphantom.effect.PotionRegister;
import com.cimdy.awakenedphantom.event.EntityEvent;
import com.cimdy.awakenedphantom.event.GenEvent;
import com.cimdy.awakenedphantom.event.ItemEvent;
import com.cimdy.awakenedphantom.event.PhantomEvent;
import com.cimdy.awakenedphantom.item.ItemRegister;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(AwakenedPhantom.MODID)
public class AwakenedPhantom
{
    public static final String MODID = "awakened_phantom";

    public AwakenedPhantom(IEventBus modEventBus, ModContainer modContainer)
    {
        AttachRegister.ATTACHMENT_TYPES.register(modEventBus);
        EffectRegister.MOB_EFFECTS.register(modEventBus);
        PotionRegister.POTIONS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        CreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(PhantomEvent::PlayerSpawnPhantomsEvent);
        NeoForge.EVENT_BUS.addListener(PhantomEvent::PhantomJoinLevelEvent);
        NeoForge.EVENT_BUS.addListener(PhantomEvent::PhantomDeathEvent);
        NeoForge.EVENT_BUS.addListener(PhantomEvent::PhantomHurtEvent);
        NeoForge.EVENT_BUS.addListener(PhantomEvent::PhantomTickEvent);
        NeoForge.EVENT_BUS.addListener(ItemEvent::ItemTooltipEvent);

        NeoForge.EVENT_BUS.addListener(EntityEvent::LivingAttackEvent);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(GenEvent::ServerGatherDataEvent);
        }

        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

}
