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
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slarper.transcendingtrident.Functions;

@Mixin(TridentEntity.class)
public class TridentEntityMixin {

    @Redirect(
            method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isThundering()Z"
            )
    )
    private boolean alwaysTrue(World world){
        return true;
    }

    @Redirect(
            method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/TridentEntity;hasChanneling()Z"
            )
    )
    private boolean andIsThunderingOrIsHoldingRod(TridentEntity trident){
        Entity owner = trident.getOwner();
        if (owner instanceof PlayerEntity player){
            return trident.hasChanneling() && (trident.world.isThundering() || Functions.isHoldingLightningRod(player));
        } else {
            return trident.hasChanneling() && trident.world.isThundering();
        }
    }

}
