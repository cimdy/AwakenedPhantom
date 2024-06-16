package com.cimdy.awakenedphantom.mixin;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;


@Mixin(Vex.class)
public abstract class VexMixin  extends Monster implements TraceableEntity {
    @Shadow @Nullable
    Mob owner;

    @Shadow public abstract void tick();

    protected VexMixin(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow @Nullable public Entity getOwner() {
        return this.owner;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci){
        if(!this.level().isClientSide && this.owner != null && this.owner.hasData(AttachRegister.SPELL_BUFF)){
            int spell_buff = this.owner.getData(AttachRegister.SPELL_BUFF);
            if(spell_buff > 0){
                //amp = 0  1  3  4
                int amp = Math.max(this.owner.getData(AttachRegister.RARE) * 2  - 1, 0);
                amp = Math.min(amp, 4);
                switch (spell_buff) {
                    case 1 -> this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, amp));
                    case 2 -> this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, amp));
                    case 3 -> this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, amp));
                    case 4 -> this.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, amp));
                    case 5 -> this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, amp));
                    case 6 -> this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, amp));
                    case 7 -> this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, amp));
                    case 8 -> this.addEffect(new MobEffectInstance(EffectRegister.CAUSE_UNLUCK, 300, amp));
                    case 9 -> this.addEffect(new MobEffectInstance(EffectRegister.CAUSE_BLINDNESS, 300, amp));
                    case 10 -> this.addEffect(new MobEffectInstance(EffectRegister.CAUSE_MOVEMENT_SLOWDOWN, 300, amp));
                }
                this.owner.setData(AttachRegister.SPELL_BUFF,0);
            }
        }
    }
}
