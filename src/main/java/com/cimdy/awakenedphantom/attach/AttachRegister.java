package com.cimdy.awakenedphantom.attach;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachRegister {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AwakenedPhantom.MODID);

    public static final Supplier<AttachmentType<Integer>> SPELL_BUFF = ATTACHMENT_TYPES.register(
            "spell_buff", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> CAUSE_MOVEMENT_SLOWDOWN = ATTACHMENT_TYPES.register(
            "cause_movement_slowdown", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> CAUSE_BLINDNESS = ATTACHMENT_TYPES.register(
            "cause_blindness", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> CAUSE_UNLUCK = ATTACHMENT_TYPES.register(
            "cause_unluck", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> RARE = ATTACHMENT_TYPES.register(
            "rare", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
}
