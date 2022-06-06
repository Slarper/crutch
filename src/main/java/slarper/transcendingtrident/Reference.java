package slarper.transcendingtrident;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Reference {
    public static final TagKey<Item> RIPTIDE_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("transcendingtrident","riptide_items"));

}
