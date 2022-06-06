package slarper.transcendingtrident.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
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
