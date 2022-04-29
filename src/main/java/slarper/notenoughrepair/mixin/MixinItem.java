package slarper.notenoughrepair.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem {

    @Shadow public abstract boolean isDamageable();

    @Inject(
            method = "canRepair",
            at = @At("HEAD"),
            cancellable = true
    )
    private void canRepairByString(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir){
        if (this.isDamageable() && ingredient.getItem() == Items.STRING){
            cir.setReturnValue(true);
        }
    }
}
