package slarper.transcendingtrident.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
            boolean b1 = player.getStackInHand(Hand.OFF_HAND).getItem() == Items.WATER_BUCKET;
            boolean b2 = player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem;
            if (b1 && b2){
                cir.setReturnValue(true);
            }
        }
    }


/*    @Inject(
            method = "isTouchingWaterOrRain",
            at = @At("HEAD"),
            cancellable = true
    )*/
    private void isHoldingRiptide(CallbackInfoReturnable<Boolean> cir){
        Entity entity = (Entity)(Object)this;
        if (entity instanceof PlayerEntity player) {
            ItemStack stack = null;
            if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem){
                stack = player.getStackInHand(Hand.MAIN_HAND);
            } else if (player.getStackInHand(Hand.OFF_HAND).getItem() instanceof TridentItem){
                stack = player.getStackInHand(Hand.OFF_HAND);
            }
            if (stack != null){
                int riptide = EnchantmentHelper.getRiptide(stack);
                if (riptide > 0){
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
