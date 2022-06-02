package slarper.transcendingtrident.mixin;

import net.minecraft.block.LightningRodBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {
    @Redirect(
            method = "onProjectileHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isThundering()Z"
            )
    )
    private boolean alwaysThunder(World instance){
        return true;
    }
}
