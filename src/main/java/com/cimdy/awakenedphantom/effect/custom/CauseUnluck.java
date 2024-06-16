package com.cimdy.awakenedphantom.effect.custom;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CauseUnluck extends MobEffect {

    public CauseUnluck(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int pAmplifier) {
        super.applyEffectTick(living, pAmplifier);
        living.setData(AttachRegister.CAUSE_UNLUCK,(pAmplifier + 1) * 20);
        return true;
    }

    @Override
    public void onMobRemoved(LivingEntity pLivingEntity, int pAmplifier, Entity.RemovalReason pReason) {
        super.onMobRemoved(pLivingEntity, pAmplifier, pReason);
        pLivingEntity.setData(AttachRegister.CAUSE_UNLUCK,0);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
