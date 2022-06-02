package slarper.transcendingtrident;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Functions {
    public static boolean isLightningRod(ItemStack stack){
        return stack.getItem() == Items.LIGHTNING_ROD;
    }
}
