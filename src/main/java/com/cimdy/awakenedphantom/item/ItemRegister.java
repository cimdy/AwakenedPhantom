package com.cimdy.awakenedphantom.item;

import com.cimdy.awakenedphantom.AwakenedPhantom;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.item.Items.PHANTOM_MEMBRANE;

public class ItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AwakenedPhantom.MODID);

    public static final DeferredItem<Item> PHANTOM_ELYTRA = ITEMS.register("phantom_elytra",
            registryName -> new PhantomElytra(
                    new Item.Properties().durability(216).rarity(Rarity.RARE)
                            .component(DataComponents.GLIDER, Unit.INSTANCE)
                            .component(DataComponents.EQUIPPABLE,
                                    Equippable.builder(EquipmentSlot.CHEST)
                                            .setEquipSound(SoundEvents.ARMOR_EQUIP_ELYTRA)
                                            .setAsset(EquipmentAssets.ELYTRA)
                                            .setDamageOnHurt(false)
                                            .build())
                            .component(DataComponents.ENCHANTABLE, null)
                            .setId(Items.ELYTRA.builtInRegistryHolder().getKey())
                            .overrideDescription("item." + AwakenedPhantom.MODID + ".phantom_elytra")
                            .repairable(PHANTOM_MEMBRANE)));
}
