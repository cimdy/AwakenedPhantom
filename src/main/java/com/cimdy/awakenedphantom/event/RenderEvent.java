package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.item.custom.elytra.PhantomElytraArmorStandLayer;
import com.cimdy.awakenedphantom.item.custom.elytra.PhantomElytraLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class RenderEvent {
    public static void EntityRenderersEvent(EntityRenderersEvent.AddLayers event){
        EntityModelSet entityModels = event.getEntityModels();
        event.getSkins().forEach(s -> {
            LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>> livingEntityRenderer = event.getSkin(s);
            if(livingEntityRenderer instanceof PlayerRenderer playerRenderer){
                playerRenderer.addLayer(new PhantomElytraLayer(playerRenderer, entityModels));
            }
        });
        LivingEntityRenderer<ArmorStand, ? extends EntityModel<ArmorStand>> livingEntityRenderer = event.getRenderer(EntityType.ARMOR_STAND);
        if(livingEntityRenderer instanceof ArmorStandRenderer armorStandRenderer){
            armorStandRenderer.addLayer(new PhantomElytraArmorStandLayer(armorStandRenderer, entityModels));
        }
    }
}
