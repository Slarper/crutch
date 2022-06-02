package slarper.transcendingtrident.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slarper.transcendingtrident.Functions;

@Mixin(TridentEntity.class)
public class TridentEntityMixin {
    @Inject(
            method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/TridentEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true
    )
    private void spawnThunder(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity, float f, Entity entity2, DamageSource damageSource, SoundEvent soundEvent, float g){
        TridentEntity trident = (TridentEntity) (Object)this;
        if (trident.world instanceof ServerWorld && !trident.world.isThundering() && trident.hasChanneling()) {
            if (entity2 instanceof PlayerEntity owner){
                if (
                        Functions.isLightningRod(owner.getStackInHand(Hand.MAIN_HAND)) || Functions.isLightningRod(owner.getStackInHand(Hand.OFF_HAND))
                ){
                    BlockPos blockPos = entity.getBlockPos();
                    if (trident.world.isSkyVisible(blockPos)) {
                        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(trident.world);
                        assert lightningEntity != null;
                        lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                        lightningEntity.setChanneler(entity2 instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity2 : null);
                        trident.world.spawnEntity(lightningEntity);
                        trident.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, 5.0F, 1.0F);
                        ci.cancel();
                    }
                }
            }
        }
    }


}
