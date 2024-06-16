package com.cimdy.awakenedphantom.effect;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.cimdy.awakenedphantom.effect.custom.CauseBlindness;
import com.cimdy.awakenedphantom.effect.custom.CauseMovementSlowdown;
import com.cimdy.awakenedphantom.effect.custom.CauseUnluck;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, AwakenedPhantom.MODID);

    public static final DeferredHolder<MobEffect, CauseMovementSlowdown> CAUSE_MOVEMENT_SLOWDOWN = MOB_EFFECTS.register("cause_movement_slowdown", () -> new CauseMovementSlowdown(
            MobEffectCategory.BENEFICIAL, 9154528));

    public static final DeferredHolder<MobEffect, CauseBlindness> CAUSE_BLINDNESS = MOB_EFFECTS.register("cause_blindness", () -> new CauseBlindness(
            MobEffectCategory.BENEFICIAL, 2039587));

    public static final DeferredHolder<MobEffect, CauseUnluck> CAUSE_UNLUCK = MOB_EFFECTS.register("cause_unluck", () -> new CauseUnluck(
            MobEffectCategory.BENEFICIAL, 12624973));
}
