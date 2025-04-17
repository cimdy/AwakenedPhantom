package com.cimdy.awakenedphantom.effect.custom;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CauseUnluck extends MobEffect {

    public CauseUnluck(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity living, int pAmplifier) {
        super.applyEffectTick(level, living, pAmplifier);
        living.setData(AttachRegister.CAUSE_UNLUCK,(pAmplifier + 1) * 20);
        return true;
    }

    @Override
    public void onMobRemoved(ServerLevel level, LivingEntity pLivingEntity, int pAmplifier, Entity.RemovalReason pReason) {
        super.onMobRemoved(level, pLivingEntity, pAmplifier, pReason);
        pLivingEntity.setData(AttachRegister.CAUSE_UNLUCK,0);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
