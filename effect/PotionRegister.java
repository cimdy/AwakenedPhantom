package com.cimdy.awakenedphantom.effect;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PotionRegister {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, AwakenedPhantom.MODID);

    public static final Holder<Potion> CAUSE_BLINDNESS_POTION = POTIONS.register("cause_blindness_potion",
            registryName -> new Potion(registryName.getPath(),
                    new MobEffectInstance(EffectRegister.CAUSE_BLINDNESS, 90 * 20, 0)));

    public static final Holder<Potion> CAUSE_UNLUCK_POTION = POTIONS.register("cause_unluck_potion",
            registryName -> new Potion(registryName.getPath(),
                    new MobEffectInstance(EffectRegister.CAUSE_UNLUCK, 90 * 20, 0)));

    public static final Holder<Potion> CAUSE_MOVEMENT_SLOWDOWN_POTION = POTIONS.register("cause_movement_slowdown_potion",
            registryName -> new Potion(registryName.getPath(),
                    new MobEffectInstance(EffectRegister.CAUSE_MOVEMENT_SLOWDOWN, 90 * 20, 0)));
}
