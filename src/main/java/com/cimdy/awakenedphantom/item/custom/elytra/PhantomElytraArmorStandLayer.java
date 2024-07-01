package com.cimdy.awakenedphantom.item.custom.elytra;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;

public class PhantomElytraArmorStandLayer extends ElytraLayer<ArmorStand, ArmorStandArmorModel> {
    private static final ResourceLocation TEXTURE_ELYTRA = ResourceLocation.fromNamespaceAndPath(AwakenedPhantom.MODID,
            "textures/entity/phantom_elytra.png");

    public PhantomElytraArmorStandLayer(ArmorStandRenderer armorStandRenderer,
                                          EntityModelSet entityModelSet) {
        super(armorStandRenderer, entityModelSet);
    }

    @Override
    public boolean shouldRender(ItemStack stack, ArmorStand entity) {
        return stack.getItem() == ItemRegister.PHANTOM_ELYTRA.get();
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, ArmorStand entity) {
        return TEXTURE_ELYTRA;
    }
}