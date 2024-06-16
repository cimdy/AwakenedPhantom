package com.cimdy.awakenedphantom;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import com.cimdy.awakenedphantom.event.EntityEvent;
import com.cimdy.awakenedphantom.event.PhantomEvent;
import com.cimdy.awakenedphantom.event.RenderEvent;
import com.cimdy.awakenedphantom.item.ItemRegister;
import com.cimdy.awakenedphantom.item.custom.PhantomElytra;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AwakenedPhantom.MODID)
public class AwakenedPhantom
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "awakened_phantom";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public AwakenedPhantom(IEventBus modEventBus, ModContainer modContainer)
    {
        AttachRegister.ATTACHMENT_TYPES.register(modEventBus);
        EffectRegister.MOB_EFFECTS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        CreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onClientSetup);
        NeoForge.EVENT_BUS.register(this);

        NeoForge.EVENT_BUS.addListener(PhantomEvent::PlayerSpawnPhantomsEvent);
        NeoForge.EVENT_BUS.addListener(PhantomEvent::EntityJoinLevelEvent);
        NeoForge.EVENT_BUS.addListener(PhantomEvent::LivingHurtEvent);
        NeoForge.EVENT_BUS.addListener(EntityEvent::LivingAttackEvent);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(RenderEvent::EntityRenderersEvent);
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void onClientSetup(FMLClientSetupEvent event) {
        // broken Property
        ItemProperties.register(ItemRegister.PHANTOM_ELYTRA.get(), new ResourceLocation(MODID, "broken"),
                (stack, arg1, arg2, arg3) -> PhantomElytra.isUseable(stack) ? 0 : 1);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
