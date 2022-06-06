package slarper.piglinrespect;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Reference {
    public static final TagKey<Item> PIGLIN_RESPECT_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("piglinrespect","piglin_respect_items"));
}
