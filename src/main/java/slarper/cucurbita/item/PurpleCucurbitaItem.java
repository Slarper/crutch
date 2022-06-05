package slarper.cucurbita.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import slarper.cucurbita.Sounds;
import slarper.cucurbita.mixin.EntityTypeAccessor;

import java.util.Objects;
import java.util.Optional;

public class PurpleCucurbitaItem extends Item {
    public PurpleCucurbitaItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!(context.getWorld() instanceof ServerWorld world)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            PlayerEntity player = context.getPlayer();
            NbtCompound itemNbt = itemStack.getNbt();

            BlockPos pos;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                pos = blockPos;
            } else {
                pos = blockPos.offset(direction);
            }

            EntityType<?> entityType = this.getEntityType(itemNbt);
            if (entityType == null){
                return ActionResult.FAIL;
            }

            Entity entity = entityType.create(world);
            if (entity == null) {
                return ActionResult.CONSUME;
            }
            EntityType.loadFromEntityNbt(world,player,entity,itemNbt);
            entity.setPosition((double)pos.getX() + 0.5D, pos.getY() + 1, (double)pos.getZ() + 0.5D);
            boolean invertY = !Objects.equals(blockPos, pos) && direction == Direction.UP;
            double d = EntityTypeAccessor.invokeGetOriginY(world, pos, invertY, entity.getBoundingBox());
            entity.refreshPositionAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + d, (double)pos.getZ() + 0.5D, entity.getYaw(), entity.getPitch());
            world.spawnEntityAndPassengers(entity);
            if (player!=null){
                player.setStackInHand(context.getHand(),new ItemStack(Items.COPPER_CUCURBITA));
            }
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
            world.playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    entity.getBlockPos(), // The position of where the sound will come from
                    Sounds.OPEN_BOTTLE_EVENT, // The sound that will play
                    SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                    1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    public EntityType<?> getEntityType(@Nullable NbtCompound nbt) {
        if (nbt != null && nbt.contains("EntityTag", 10)) {
            NbtCompound nbtCompound = nbt.getCompound("EntityTag");
            if (nbtCompound.contains("id", 8)) {
                Optional<EntityType<?>> entityType = EntityType.get(nbtCompound.getString("id"));
                return entityType.orElse(null);
            }
        }
        return null;
    }
}
