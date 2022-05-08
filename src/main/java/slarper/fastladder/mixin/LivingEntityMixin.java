package slarper.fastladder.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin{
    @Redirect(
            method = "applyMovementInput",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"
            )
    )
    private void redirectMove(LivingEntity entity, MovementType type, Vec3d original){
        if (!entity.isClimbing() || entity.isSneaking()) {
            entity.move(type, original);
        }
        else {
            if (((LivingEntityAccessor)entity).getJumping()){
                entity.move(type, new Vec3d(original.x, 1, original.z));
            } else {
                entity.move(type, original);
            }
        }
    }
}
