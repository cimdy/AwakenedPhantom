package com.cimdy.awakenedphantom.mixin;

import com.cimdy.awakenedphantom.attach.AttachRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Phantom.class)
public abstract class PhantomMixin extends FlyingMob {

    @Unique
    float awakenedPhantom_NeoForge_1_20_6$Difficulty = this.level().getCurrentDifficultyAt(this.getOnPos()).getEffectiveDifficulty();
    @Unique
    float awakenedPhantom_NeoForge_1_20_6$Multiplier = this.level().getCurrentDifficultyAt(this.getOnPos()).getSpecialMultiplier();
    @Unique
    float awakenedPhantom_NeoForge_1_20_6$DifficultyMultiplier = awakenedPhantom_NeoForge_1_20_6$Difficulty * (1 + awakenedPhantom_NeoForge_1_20_6$Multiplier) / 3 * 2; // 1 - 9

    @Unique
    private static final EntityDataAccessor<Integer> DATA_SUMMON_TIME = SynchedEntityData.defineId(PhantomMixin.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Integer> IS_CAST = SynchedEntityData.defineId(PhantomMixin.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Integer> SPELL_TIME = SynchedEntityData.defineId(PhantomMixin.class, EntityDataSerializers.INT) ;

    protected PhantomMixin(EntityType<? extends FlyingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder, CallbackInfo ci) {
        pBuilder.define(DATA_SUMMON_TIME,0);
        pBuilder.define(IS_CAST,-1);
        pBuilder.define(SPELL_TIME,0);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void tick(CallbackInfo ci) {
        int summon_time = this.entityData.get(DATA_SUMMON_TIME);
        int is_cast = this.entityData.get(IS_CAST);
        int min_summon_time = (int)(1600 / awakenedPhantom_NeoForge_1_20_6$DifficultyMultiplier) - this.getData(AttachRegister.RARE) * 100;//生成间隔 随难度增加 递减
        summon_time = summon_time + 1;//生成计时
        if(summon_time > min_summon_time && is_cast == -1){//播放一个施法声音准备召唤恼鬼
            is_cast = 0;//初始化施法计时器
            playSound(SoundEvents.EVOKER_CAST_SPELL);//播放一次施法声音
        }

        if(is_cast >= 0){//施法计时器
            is_cast = is_cast + 1;
        }

        if (is_cast == 20){//施法计时1秒
            is_cast = -1; //施法计时器重置
            awakenedPhantom_NeoForge_1_20_6$performSpellCasting();//生成1只恼鬼
            summon_time = 0;//计时器归零
            playSound(SoundEvents.EVOKER_PREPARE_SUMMON);//播放召唤恼鬼声音
        }
        this.entityData.set(DATA_SUMMON_TIME, summon_time);//存储计时器数据
        this.entityData.set(IS_CAST, is_cast);

        int spell_time = this.entityData.get(SPELL_TIME);
        spell_time = spell_time + 1;

        if(!this.level().isClientSide){
            int spell_buff = this.getData(AttachRegister.SPELL_BUFF);
            int min_spell_time = 300 - (int) awakenedPhantom_NeoForge_1_20_6$DifficultyMultiplier * 10  - this.getData(AttachRegister.RARE) * 20;
            if (spell_time >= min_spell_time && spell_buff == 0) {
                int random = (int) (Math.random() * 100 + 1);
                spell_buff = random / 10;
                playSound(SoundEvents.EVOKER_CAST_SPELL);
                spell_time = 0;
                this.setData(AttachRegister.SPELL_BUFF,spell_buff);
            }
        }

        this.entityData.set(SPELL_TIME,spell_time);
    }

    @Inject(method = "getHurtSound", at = @At(value = "HEAD"))
    private void getHurtSound(DamageSource pDamageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (!this.level().isClientSide && pDamageSource.getEntity() instanceof LivingEntity) {
            int summon_time = this.entityData.get(DATA_SUMMON_TIME);
            int min_summon_time =  (int)(1200 / awakenedPhantom_NeoForge_1_20_6$DifficultyMultiplier);
            int add_time = summon_time * (min_summon_time * 30 / 100);
            this.getEntityData().set(DATA_SUMMON_TIME, summon_time + add_time);//被生物攻击受伤时，降低30%召唤冷却
        }
    }

    @Inject(method = "getDeathSound", at = @At(value = "HEAD"))
    private void getDeathSound(CallbackInfoReturnable<SoundEvent> cir){
        playSound(SoundEvents.EVOKER_PREPARE_SUMMON);
        awakenedPhantom_NeoForge_1_20_6$performSpellCasting();//死亡立即召唤一只
    }

    public void playSound(@NotNull SoundEvent soundEvent) {//播放声音
        if (this.level().isClientSide) {
            this.level()
                    .playLocalSound(
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            soundEvent,
                            this.getSoundSource(),
                            0.95F + this.random.nextFloat() * 0.05F,
                            0.95F + this.random.nextFloat() * 0.05F,
                            false
                    );
        }
    }

    @Unique
    protected void awakenedPhantom_NeoForge_1_20_6$performSpellCasting() {//生成恼鬼
        if(!this.level().isClientSide){
            ServerLevel serverLevel = ((ServerLevel)this.level());
            BlockPos blockpos = this.blockPosition().offset(-2 + this.random.nextInt(5), 1, -2 + this.random.nextInt(5));
            Vex vex = EntityType.VEX.create(this.level());
            if (vex != null) {
                vex.moveTo(blockpos, 0.0F, 0.0F);
                vex.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null);
                vex.setOwner(this);
                vex.setBoundOrigin(blockpos);
                vex.setLimitedLife(20 * (30 + this.random.nextInt(90)));
                serverLevel.addFreshEntityWithPassengers(vex);
                serverLevel.gameEvent(GameEvent.ENTITY_PLACE, blockpos, GameEvent.Context.of(this));
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomSweepAttackGoal")
    public static class PhantomSweepAttackGoalMixin extends Goal {
        @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Phantom;doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z"))
        public boolean doDamage(Phantom phantom, Entity entity){
            return false;
        }

        @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getY(D)D"))
        public double move(LivingEntity living, double v){
            return living.getY() + 10.0;
        }

        @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
        public AABB aabb(LivingEntity living){
            AABB box = living.getBoundingBox();
            return new AABB(box.minX, box.minY + 10, box.minZ, box.maxX, box.maxY + 10, box.maxZ);
        }

        @Unique
        public boolean canUse() {
            return false;
        }
    }
}
