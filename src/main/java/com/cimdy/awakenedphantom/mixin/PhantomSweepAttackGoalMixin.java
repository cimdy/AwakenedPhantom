package com.cimdy.awakenedphantom.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomSweepAttackGoal")
public abstract class PhantomSweepAttackGoalMixin extends Goal {
        @ModifyExpressionValue(method = "tick",
                at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/world/entity/LivingEntity;getY(D)D"))
        private double y(double v){
            return v + 10;
        }
    }