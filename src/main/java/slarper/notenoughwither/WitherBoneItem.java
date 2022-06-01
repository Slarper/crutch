package slarper.notenoughwither;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;

public class WitherBoneItem extends Item {
    public WitherBoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (context.getPlayer()!=null){
            context.getPlayer().swingHand(context.getHand());
        }
        if (world instanceof ServerWorld) {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.SPAWNER)) {
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                if (blockEntity instanceof MobSpawnerBlockEntity) {
                    MobSpawnerLogic mobSpawnerLogic = ((MobSpawnerBlockEntity)blockEntity).getLogic();
                    NbtCompound nbt = new NbtCompound();
                    mobSpawnerLogic.writeNbt(nbt);
                    if (nbt.getCompound("SpawnData")
                            .getCompound("entity")
                            .getString("id")
                            .equals("minecraft:blaze")
                    ){
                        EntityType<?> entityType = EntityType.WITHER_SKELETON;
                        mobSpawnerLogic.setEntityId(entityType);
                        blockEntity.markDirty();
                        world.updateListeners(blockPos, blockState, blockState, 3);
                        itemStack.decrement(1);
                        return ActionResult.CONSUME;
                    }
                }
            }
        }
        return super.useOnBlock(context);
    }
}
