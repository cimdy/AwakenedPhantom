package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class GenEvent {
    @SubscribeEvent
    public static void ServerGatherDataEvent(GatherDataEvent.Client event) {
        event.getGenerator().addProvider(true, new LanguageProvider(
                event.getGenerator().getPackOutput(), AwakenedPhantom.MODID, "zh_cn") {
            @Override
            protected void addTranslations() {
                this.add("itemGroup." + AwakenedPhantom.MODID, "幻翼觉醒了");
                this.add("item." + AwakenedPhantom.MODID + "." + ItemRegister.PHANTOM_ELYTRA.getId().getPath(),
                        "幻翼翅");
                this.add("item." + AwakenedPhantom.MODID + "." + "phantom_elytra.tooltip",
                        "无法附魔");
                this.add("effect." + AwakenedPhantom.MODID + "." + EffectRegister.CAUSE_BLINDNESS.getId().getPath(),
                        "攻击概率造成失明");
                this.add("effect." + AwakenedPhantom.MODID + "." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath(),
                        "攻击概率造成缓慢");
                this.add("effect." + AwakenedPhantom.MODID + "." + EffectRegister.CAUSE_UNLUCK.getId().getPath(),
                        "攻击概率造成不幸");
                this.add("item.minecraft.tipped_arrow.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "操控失明之箭");
                this.add("item.minecraft.tipped_arrow.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "操控缓慢之箭");
                this.add("item.minecraft.tipped_arrow.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "操控不幸之箭");
                this.add("item.minecraft.potion.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "操控失明药水");
                this.add("item.minecraft.potion.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "操控缓慢药水");
                this.add("item.minecraft.potion.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "操控不幸药水");
                this.add("item.minecraft.splash_potion.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "喷溅型操控失明药水");
                this.add("item.minecraft.splash_potion.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "喷溅型操控缓慢药水");
                this.add("item.minecraft.splash_potion.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "喷溅型操控不幸药水");
                this.add("item.minecraft.lingering_potion.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "滞留型操控失明药水");
                this.add("item.minecraft.lingering_potion.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "滞留型操控缓慢药水");
                this.add("item.minecraft.lingering_potion.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "滞留型操控不幸药水");
            }
        });

        event.getGenerator().addProvider(true, new LanguageProvider(
                event.getGenerator().getPackOutput(), AwakenedPhantom.MODID, "en_us") {
            @Override
            protected void addTranslations() {
                this.add("itemGroup." + AwakenedPhantom.MODID, "幻翼觉醒了");
                this.add("item." + AwakenedPhantom.MODID + "." + ItemRegister.PHANTOM_ELYTRA.getId().getPath(),
                        "幻翼翅");
                this.add("item." + AwakenedPhantom.MODID + "." + "phantom_elytra.tooltip",
                        "无法附魔");
                this.add("effect." + AwakenedPhantom.MODID + "." + EffectRegister.CAUSE_BLINDNESS.getId().getPath(),
                        "攻击概率造成失明");
                this.add("effect." + AwakenedPhantom.MODID + "." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath(),
                        "攻击概率造成缓慢");
                this.add("effect." + AwakenedPhantom.MODID + "." + EffectRegister.CAUSE_UNLUCK.getId().getPath(),
                        "攻击概率造成不幸");
                this.add("item.minecraft.tipped_arrow.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "操控失明之箭");
                this.add("item.minecraft.tipped_arrow.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "操控缓慢之箭");
                this.add("item.minecraft.tipped_arrow.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "操控不幸之箭");
                this.add("item.minecraft.potion.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "操控失明药水");
                this.add("item.minecraft.potion.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "操控缓慢药水");
                this.add("item.minecraft.potion.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "操控不幸药水");
                this.add("item.minecraft.splash_potion.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "喷溅型操控失明药水");
                this.add("item.minecraft.splash_potion.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "喷溅型操控缓慢药水");
                this.add("item.minecraft.splash_potion.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "喷溅型操控不幸药水");
                this.add("item.minecraft.lingering_potion.effect." + EffectRegister.CAUSE_BLINDNESS.getId().getPath() + "_potion",
                        "滞留型操控失明药水");
                this.add("item.minecraft.lingering_potion.effect." + EffectRegister.CAUSE_MOVEMENT_SLOWDOWN.getId().getPath() + "_potion",
                        "滞留型操控缓慢药水");
                this.add("item.minecraft.lingering_potion.effect." + EffectRegister.CAUSE_UNLUCK.getId().getPath() + "_potion",
                        "滞留型操控不幸药水");
            }
        });
    }
}
