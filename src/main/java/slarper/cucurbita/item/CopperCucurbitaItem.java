package slarper.cucurbita.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class CopperCucurbitaItem extends Item {
    public CopperCucurbitaItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {


        // Only catch animals
        if ((entity instanceof AnimalEntity) && (user != null)){
            // swing hand
            user.swingHand(hand);
            // delete the mob
            entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);

            // give user a new cucurbita with mob
            ItemStack cucurbita = new ItemStack(Items.PURPLE_CUCURBITA);
            entity.saveSelfNbt(cucurbita.getOrCreateSubNbt(EntityType.ENTITY_TAG_KEY));

            user.setStackInHand(hand,cucurbita);
        }



        return super.useOnEntity(stack, user, entity, hand);
    }


}
