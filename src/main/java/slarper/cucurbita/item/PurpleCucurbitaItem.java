package slarper.cucurbita.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import java.util.Objects;
import java.util.Optional;

public class PurpleCucurbitaItem extends Item {
    public PurpleCucurbitaItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        // swing hand
        if (context.getPlayer() != null){
            context.getPlayer().swingHand(context.getHand());
        }

        // try to reveal mob from nbt
        Optional<Entity> optional = EntityType.getEntityFromNbt(context.getStack().getOrCreateSubNbt(EntityType.ENTITY_TAG_KEY),context.getWorld());
        if (optional.isPresent()){
            Entity entity = optional.get();
            entity.setPosition(context.getHitPos());
            context.getWorld().spawnEntity(entity);
            ItemStack calabash = new ItemStack(Items.COPPER_CUCURBITA);
            Objects.requireNonNull(context.getPlayer()).setStackInHand(context.getHand(),calabash);
        }

        return super.useOnBlock(context);
    }
}
