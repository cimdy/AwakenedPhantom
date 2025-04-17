package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.effect.EffectRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class EntityEvent {
    @SubscribeEvent
    public static void LivingAttackEvent(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity target
                && event.getSource().getEntity() instanceof LivingEntity living
                && !event.getEntity().level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) event.getEntity().level();
            RandomSource random = serverLevel.random;
            if (living.hasEffect(EffectRegister.CAUSE_MOVEMENT_SLOWDOWN)) {
                if ((living.getEffect(EffectRegister.CAUSE_MOVEMENT_SLOWDOWN).getAmplifier() + 1) * 20
                        >= random.nextInt(100) + 1) {
                    target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 0));
                }
            }
            if (living.hasEffect(EffectRegister.CAUSE_BLINDNESS)) {
                if ((living.getEffect(EffectRegister.CAUSE_BLINDNESS).getAmplifier() + 1) * 20
                        >= random.nextInt(100) + 1) {
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                }
            }
            if (living.hasEffect(EffectRegister.CAUSE_UNLUCK)) {
                if ((living.getEffect(EffectRegister.CAUSE_UNLUCK).getAmplifier() + 1) * 20
                        >= random.nextInt(100) + 1) {
                    target.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 200, 0));
                }
            }
        }
    }
}
