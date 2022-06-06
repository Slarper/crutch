package slarper.piglinrespect.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slarper.piglinrespect.Reference;

import java.util.Iterator;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(
            method = "wearsGoldArmor",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void isInTag(LivingEntity entity, CallbackInfoReturnable<Boolean> cir, Iterable<ItemStack> iterable, Iterator<ItemStack> var2, ItemStack itemStack){
        if (itemStack.isIn(Reference.PIGLIN_RESPECT_ITEMS)){
            cir.setReturnValue(true);
        }
    }
}
