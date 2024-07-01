package com.cimdy.awakenedphantom.item.custom.elytra;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PhantomElytraLayer extends ElytraLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation TEXTURE_ELYTRA = ResourceLocation.fromNamespaceAndPath(AwakenedPhantom.MODID,
            "textures/entity/phantom_elytra.png");

    public PhantomElytraLayer(
            RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> rendererIn,
            EntityModelSet modelSet) {
        super(rendererIn, modelSet);
    }

    @Override
    public boolean shouldRender(ItemStack stack, AbstractClientPlayer entity) {
        return stack.getItem() == ItemRegister.PHANTOM_ELYTRA.get();
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, AbstractClientPlayer entity) {
        return TEXTURE_ELYTRA;
    }

}
