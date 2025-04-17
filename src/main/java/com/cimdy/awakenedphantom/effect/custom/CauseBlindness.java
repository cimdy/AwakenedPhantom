package com.cimdy.awakenedphantom.effect.custom;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CauseBlindness extends MobEffect {

    public CauseBlindness(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
