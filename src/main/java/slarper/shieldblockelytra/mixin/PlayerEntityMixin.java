package slarper.shieldblockelytra.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(
            method = "checkFallFlying",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isHoldingShield(CallbackInfoReturnable<Boolean> cir){
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getStackInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem){
            cir.setReturnValue(false);
        } else if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ShieldItem){
            cir.setReturnValue(false);
        }
    }
}
