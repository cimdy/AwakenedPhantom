package com.cimdy.awakenedphantom.event;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import com.cimdy.awakenedphantom.effect.EffectRegister;
import com.cimdy.awakenedphantom.effect.PotionRegister;
import com.cimdy.awakenedphantom.item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSpawnPhantomsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

public class PhantomEvent{
    @SubscribeEvent
    public static void PhantomTickEvent(EntityTickEvent.Post event) {
        if(event.getEntity() instanceof Phantom phantom && phantom.level() instanceof ServerLevel serverLevel) {
            //恼鬼召唤
            int spell_time = phantom.getData(AttachRegister.SPELL_TIME);
            int spelling_time = phantom.getData(AttachRegister.SPELLING_TIME);
            int min_spell_time = (int) (1600 / getDifficultyMultiplier(serverLevel, phantom))
                    - phantom.getData(AttachRegister.RARE) * 100; //生成间隔 随难度增加 递减
            spell_time = spell_time + 1;//生成计时
            if (spell_time > min_spell_time && spelling_time == -1) { //播放一个施法声音准备召唤恼鬼
                spelling_time = 0; //初始化施法计时器
                playSound(SoundEvents.EVOKER_CAST_SPELL, phantom); //播放一次施法声音
            }

            if (spelling_time >= 0) {//施法计时器
                spelling_time = spelling_time + 1;
            }

            if (spelling_time == 20) { //施法计时1秒
                spelling_time = -1; //施法计时器重置
                performSpellCasting(phantom); //生成1只恼鬼
                spell_time = 0; //计时器归零
                playSound(SoundEvents.EVOKER_PREPARE_SUMMON, phantom); //播放召唤恼鬼声音
            }
            phantom.setData(AttachRegister.SPELL_TIME, spell_time); //存储计时器数据
            phantom.setData(AttachRegister.SPELLING_TIME, spelling_time);

            //效果赋予
            int effect_time = phantom.getData(AttachRegister.EFFECT_TIME);
            effect_time = effect_time + 1;

            int spell_buff = phantom.getData(AttachRegister.EFFECT_BUFF);
            int min_effect_time = 300 - (int) getDifficultyMultiplier(serverLevel, phantom) * 10
                    - phantom.getData(AttachRegister.RARE) * 20;
            if (effect_time >= min_effect_time && spell_buff == 0) {
                int random = (int) (Math.random() * 100 + 1);
                spell_buff = random / 10;
                playSound(SoundEvents.EVOKER_CAST_SPELL, phantom);
                effect_time = 0;
                phantom.setData(AttachRegister.EFFECT_BUFF, spell_buff);
            }

            phantom.setData(AttachRegister.EFFECT_TIME, effect_time);
        }
    }

    public static void PhantomHurtEvent(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Phantom phantom && phantom.level() instanceof ServerLevel serverLevel
                && event.getSource().getEntity() instanceof LivingEntity) { // 幻翼被实体伤害
            int spell_time = phantom.getData(AttachRegister.SPELL_TIME);
            int min_spell_time =  (int)(1200 / getDifficultyMultiplier(serverLevel, phantom));
            int add_time = spell_time * (min_spell_time * 30 / 100);
            phantom.setData(AttachRegister.SPELL_TIME, spell_time + add_time); //降低30%召唤冷却
        }
    }

    public static void PlayerSpawnPhantomsEvent(PlayerSpawnPhantomsEvent event){
        //幻翼将永远保持生成 而不是只在玩家至少72000刻没睡觉后才开始生成
        event.setResult(PlayerSpawnPhantomsEvent.Result.ALLOW);
    }

    public static void PhantomDeathEvent(LivingDeathEvent event) {
        if(event.getEntity() instanceof Phantom phantom && phantom.level() instanceof ServerLevel serverLevel){
            playSound(SoundEvents.EVOKER_PREPARE_SUMMON, phantom);
            performSpellCasting(phantom); //死亡立即召唤一只
            if(event.getSource().getEntity() instanceof Player player) {
                int rare = phantom.getData(AttachRegister.RARE);
                RandomSource randomSource = serverLevel.random;
                //抢夺
                int loot = EnchantmentHelper.getEnchantmentLevel(serverLevel.holderOrThrow(Enchantments.LOOTING), player);
                if((3 + loot) * (100 + (rare + player.getLuck()) * 25) / 100 > randomSource.nextInt(100) + 1){
                    phantom.spawnAtLocation(serverLevel, ItemRegister.PHANTOM_ELYTRA.toStack());
                }else { // 如果不掉落幻翼翅则掉落药水箭和药水
                    int random1 = serverLevel.random.nextInt(100);
                    ItemStack arrow = new ItemStack(
                            random1 > 50 ? Items.TIPPED_ARROW :
                                    random1 > 32 ? Items.POTION :
                                            random1 > 16 ? Items.SPLASH_POTION :
                                                    Items.LINGERING_POTION);
                    int random2 = serverLevel.random.nextInt(100);
                    arrow.set(DataComponents.POTION_CONTENTS, new PotionContents(
                            random2 > 66 ? PotionRegister.CAUSE_UNLUCK_POTION :
                                    random2 > 33 ? PotionRegister.CAUSE_BLINDNESS_POTION
                                            : PotionRegister.CAUSE_MOVEMENT_SLOWDOWN_POTION));
                    phantom.spawnAtLocation(serverLevel, arrow);
                }
            }

        }
    }

    public static void PhantomJoinLevelEvent(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof Phantom phantom && phantom.level() instanceof ServerLevel serverLevel){
            if(!phantom.hasData(AttachRegister.RARE)){
                int rarity = serverLevel.random.nextInt(100) + 1;
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
        if(event.getEntity() instanceof Vex vex) {
            if(!vex.level().isClientSide && vex.getOwner() != null && vex.getOwner().hasData(AttachRegister.EFFECT_BUFF)){
                int spell_buff = vex.getOwner().getData(AttachRegister.EFFECT_BUFF);
                if(spell_buff > 0){
                    //amp = 0  1  3  4
                    int amp = Math.max(vex.getOwner().getData(AttachRegister.RARE) * 2  - 1, 0);
                    amp = Math.min(amp, 4);
                    switch (spell_buff) {
                        case 1 -> vex.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, amp));
                        case 2 -> vex.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 300, amp));
                        case 3 -> vex.addEffect(new MobEffectInstance(MobEffects.SPEED, 300, amp));
                        case 4 -> vex.addEffect(new MobEffectInstance(MobEffects.HASTE, 1, amp));
                        case 5 -> vex.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, amp));
                        case 6 -> vex.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, amp));
                        case 7 -> vex.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 300, amp));
                        case 8 -> vex.addEffect(new MobEffectInstance(EffectRegister.CAUSE_UNLUCK, 300, amp));
                        case 9 -> vex.addEffect(new MobEffectInstance(EffectRegister.CAUSE_BLINDNESS, 300, amp));
                        case 10 -> vex.addEffect(new MobEffectInstance(EffectRegister.CAUSE_MOVEMENT_SLOWDOWN, 300, amp));
                    }
                    vex.getOwner().setData(AttachRegister.EFFECT_BUFF,0);
                }
            }
        }
    }

    public static float getDifficultyMultiplier(ServerLevel serverLevel, LivingEntity living) {
        float difficulty = serverLevel.getCurrentDifficultyAt(living.getOnPos()).getEffectiveDifficulty();
        float multiplier = serverLevel.getCurrentDifficultyAt(living.getOnPos()).getSpecialMultiplier();
        return difficulty * (1 + multiplier) / 3 * 2;
    }

    public static void playSound(@NotNull SoundEvent soundEvent, LivingEntity living) { //播放声音
        if (living.level().isClientSide) {
            living.level()
                    .playLocalSound(
                            living.getX(),
                            living.getY(),
                            living.getZ(),
                            soundEvent,
                            living.getSoundSource(),
                            0.95F + living.level().random.nextFloat() * 0.05F,
                            0.95F + living.level().random.nextFloat() * 0.05F,
                            false
                    );
        }
    }

    protected static void performSpellCasting(Mob mob) { //生成恼鬼
        if(mob.level() instanceof ServerLevel serverLevel){
            BlockPos blockpos = mob.blockPosition().offset(-2 + serverLevel.random.nextInt(5),
                    1, -2 + serverLevel.random.nextInt(5));
            Vex vex = new Vex(EntityType.VEX, serverLevel);
            vex.setPos(Vec3.atLowerCornerOf(blockpos));
            vex.finalizeSpawn(serverLevel, mob.level().getCurrentDifficultyAt(blockpos),
                    EntitySpawnReason.MOB_SUMMONED, null);
            vex.setOwner(mob);
            vex.setBoundOrigin(blockpos);
            vex.setLimitedLife(20 * (30 + serverLevel.random.nextInt(90)));
            serverLevel.addFreshEntityWithPassengers(vex);
            serverLevel.gameEvent(GameEvent.ENTITY_PLACE, blockpos, GameEvent.Context.of(mob));
        }
    }
}
