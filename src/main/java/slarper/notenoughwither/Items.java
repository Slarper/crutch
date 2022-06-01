package slarper.notenoughwither;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item WITHER_BONE;
    public static void init(){
    }

    public static Item register(String id, Item item){
        return Registry.register(Registry.ITEM, new Identifier(NotEnoughWither.ID, id), item);
    }

    static {
        WITHER_BONE = register("wither_bone", new WitherBoneItem(new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON)));
    }
}
