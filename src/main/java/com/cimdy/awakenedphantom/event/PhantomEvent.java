package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSpawnPhantomsEvent;

import java.util.Random;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.getMobLooting;

public class PhantomEvent{
    @SubscribeEvent
    public static void PlayerSpawnPhantomsEvent(PlayerSpawnPhantomsEvent event){
        event.setResult(PlayerSpawnPhantomsEvent.Result.ALLOW);//幻翼将永远保持生成 而不是只在玩家至少72000刻没睡觉后才开始生成
    }

    public static void LivingHurtEvent(LivingHurtEvent event) {
        if(!event.getEntity().level().isClientSide
                && event.getSource().getEntity() instanceof Player player
                && event.getEntity() instanceof Phantom phantom){
            float damage = event.getAmount();
            if(phantom.getHealth() + phantom.getAbsorptionAmount() <= damage && phantom.hasData(AttachRegister.RARE)){
                ServerLevel serverLevel = (ServerLevel) event.getEntity().level();
                int rare = phantom.getData(AttachRegister.RARE);
                RandomSource randomSource = serverLevel.random;
                if((3 + getMobLooting(player)) * (100 + (rare + player.getLuck()) * 25) / 100 > randomSource.nextInt(100) + 1){
                    ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, serverLevel);
                    itemEntity.setItem(ItemRegister.PHANTOM_ELYTRA.toStack());
                    itemEntity.moveTo(phantom.getEyePosition());
                    phantom.onItemPickup(itemEntity);
                    serverLevel.addFreshEntity(itemEntity);
                }
            }
        }
    }

    public static void EntityJoinLevelEvent(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof Phantom phantom){
            if(!phantom.hasData(AttachRegister.RARE)){
                int rarity = new Random().nextInt(100) + 1;
                int rare = 0;
                if(rarity > 50 && rarity <= 85){
                    rare = 1;
                }else if(rarity > 85 && rarity <= 95){
                    rare = 2;
                }else if(rarity > 95){
                    rare = 3;
                }

                if(rare > 0) {
                    double health = phantom.getAttributeValue(Attributes.MAX_HEALTH) * (rare * 50 + 100) / 100;
                    phantom.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
                    phantom.heal((float) health);
                    double scale = phantom.getAttributeValue(Attributes.SCALE);
                    phantom.getAttribute(Attributes.SCALE).setBaseValue(scale * (100 - 20 * rare) / 100);
                }

                phantom.setData(AttachRegister.RARE, rare);
            }
        }
    }
}
