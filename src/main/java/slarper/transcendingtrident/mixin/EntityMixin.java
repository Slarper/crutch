package slarper.transcendingtrident.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(
            method = "isTouchingWaterOrRain",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isHoldingWaterBucket(CallbackInfoReturnable<Boolean> cir){
        Entity entity = (Entity)(Object)this;
        if (entity instanceof PlayerEntity player) {
            Item main = player.getStackInHand(Hand.MAIN_HAND).getItem();
            Item off = player.getStackInHand(Hand.OFF_HAND).getItem();
            if ((main == Items.WATER_BUCKET && off instanceof TridentItem)||(off == Items.WATER_BUCKET && main instanceof TridentItem)){
                cir.setReturnValue(true);
            }
        }
    }
}
