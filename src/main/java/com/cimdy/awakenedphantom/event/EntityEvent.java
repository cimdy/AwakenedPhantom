package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Random;

public class EntityEvent {
    @SubscribeEvent
    public static void LivingAttackEvent(LivingAttackEvent event){
        if(event.getEntity() instanceof LivingEntity target
                && event.getSource().getEntity() instanceof LivingEntity living
                && !event.getEntity().level().isClientSide){
            ServerLevel serverLevel = (ServerLevel) event.getEntity().level();
            RandomSource randomSource = serverLevel.random;
            if(living.hasEffect(EffectRegister.CAUSE_MOVEMENT_SLOWDOWN)){
                int cause_movement_slowdown = event.getEntity().getData(AttachRegister.CAUSE_MOVEMENT_SLOWDOWN);
                int chance = randomSource.nextInt(100) + 1;
                if(cause_movement_slowdown >= chance){
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
                }
            }
            if(living.hasEffect(EffectRegister.CAUSE_BLINDNESS)){
                int cause_blindness = event.getEntity().getData(AttachRegister.CAUSE_BLINDNESS);
                int chance = randomSource.nextInt(100) + 1;
                if(cause_blindness >= chance){
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                }
            }
            if(living.hasEffect(EffectRegister.CAUSE_UNLUCK)){
                int cause_unluck = event.getEntity().getData(AttachRegister.CAUSE_UNLUCK);
                int chance = randomSource.nextInt(100) + 1;
                if(cause_unluck >= chance){
                    target.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 200, 0));
                }
            }
        }
    }
}
