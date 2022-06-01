package slarper.cucurbita.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import slarper.cucurbita.Sounds;

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

            // play sound
            World world = user.getWorld();
            if (!world.isClient) {
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        entity.getBlockPos(), // The position of where the sound will come from
                        Sounds.OPEN_BOTTLE_EVENT, // The sound that will play
                        SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                        1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );
            }
        }



        return super.useOnEntity(stack, user, entity, hand);
    }


}
