package slarper.transcendingtrident;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Functions {
    public static boolean isLightningRod(ItemStack stack){
        return stack.getItem() == Items.LIGHTNING_ROD;
    }
    public static boolean isHoldingLightningRod(PlayerEntity player){
        return isLightningRod(player.getOffHandStack()) || isLightningRod(player.getMainHandStack());
    }
    public static boolean isRiptideItem(ItemStack stack){
        return stack.isIn(Reference.RIPTIDE_ITEMS);
    }
    public static boolean canRiptide(PlayerEntity player){
        if (isRiptideItem(player.getOffHandStack()) || isRiptideItem(player.getMainHandStack())){
            return true;
        } else {
            return player.isTouchingWaterOrRain();
        }
    }
}
