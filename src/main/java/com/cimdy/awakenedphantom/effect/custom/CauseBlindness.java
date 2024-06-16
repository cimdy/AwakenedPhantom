package com.cimdy.awakenedphantom.effect.custom;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CauseBlindness extends MobEffect {

    public CauseBlindness(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int pAmplifier) {
        super.applyEffectTick(living, pAmplifier);
        living.setData(AttachRegister.CAUSE_BLINDNESS,(pAmplifier + 1) * 20);
        return true;
    }

    @Override
    public void onMobRemoved(LivingEntity pLivingEntity, int pAmplifier, Entity.RemovalReason pReason) {
        super.onMobRemoved(pLivingEntity, pAmplifier, pReason);
        pLivingEntity.setData(AttachRegister.CAUSE_BLINDNESS,0);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
