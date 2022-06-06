package slarper.transcendingtrident.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import slarper.transcendingtrident.Functions;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"
            )
    )
    private boolean canRiptide1(PlayerEntity player){
        return Functions.canRiptide(player);
    }

    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"
            )
    )
    private boolean canRiptide2(PlayerEntity player){
        return Functions.canRiptide(player);
    }
}
