package com.cimdy.awakenedphantom.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {

    @Shadow private int nextTick;

    @Inject(method = "tick",at = @At(value = "INVOKE",target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",ordinal = 0,shift = At.Shift.AFTER))
    private void tick(ServerLevel level, boolean b, boolean b2, CallbackInfoReturnable<Integer> cir){
        this.nextTick = 1;
    }

}
