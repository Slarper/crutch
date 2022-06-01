package slarper.cucurbita.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import slarper.cucurbita.Sounds;

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

            World world = context.getWorld();
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

        return super.useOnBlock(context);
    }
}
