package slarper.cucurbita.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import slarper.cucurbita.Cucurbita;

public class Items {
    public static void init(){}
    private static Item register(String id, Item item){
        return Registry.register(Registry.ITEM, new Identifier(Cucurbita.ID, id), item);
    }

    public static final Item COPPER_CUCURBITA;
    public static final Item PURPLE_CUCURBITA;

    static {
        COPPER_CUCURBITA = register("copper_cucurbita", new CopperCucurbitaItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS)));
        PURPLE_CUCURBITA = register("purple_cucurbita", new PurpleCucurbitaItem(new FabricItemSettings().maxCount(1)));
    }
}
