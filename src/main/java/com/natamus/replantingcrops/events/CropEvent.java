/*
 * This is the latest source code of Replanting Crops.
 * Minecraft version: 1.19.x, mod version: 2.5.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a GitHub Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Replanting Crops ever released, along with some other perks.
 *
 * GitHub Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.replantingcrops.events;

import com.natamus.replantingcrops.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class CropEvent {
	private static final HashMap<BlockPos, Item> checkReplant = new HashMap<>();
	// cocoa's blockstate has a FACING property; so can't use the defaultState (FACING=NORTH) directly
	private static final HashMap<BlockPos, BlockState> cropStates = new HashMap<>();
	
	public static boolean onHarvest(World world, PlayerEntity player, BlockPos harvestPos, BlockState state) {
		if (world.isClient()) {
			return true;
		}
		
		if (player == null) {
			return true;
		}
		
		Hand hand = null;
		if (ConfigHandler.mustHoldHoeForReplanting.getValue()) {
			hand = Hand.MAIN_HAND;
			if (!(player.getMainHandStack().getItem() instanceof HoeItem)) {
				hand = Hand.OFF_HAND;
				if (!(player.getOffHandStack().getItem() instanceof HoeItem)) {
					return true;
				}
			}
		}
		
		if (player.isSneaking()) {
			return true;
		}
		
		Block block = state.getBlock();
		
/*		if (block.equals(Blocks.WHEAT)) {
			checkReplant.put(harvestPos, Items.WHEAT_SEEDS);
		}
		else if (block.equals(Blocks.CARROTS)) {
			checkReplant.put(harvestPos, Items.CARROT);
		}
		else if (block.equals(Blocks.POTATOES)) {
			checkReplant.put(harvestPos, Items.POTATO);
		}
		else if (block.equals(Blocks.BEETROOTS)) {
			checkReplant.put(harvestPos, Items.BEETROOT_SEEDS);
		}
		else if (block.equals(Blocks.NETHER_WART)) {
			checkReplant.put(harvestPos, Items.NETHER_WART);
		}
		else if (block.equals(Blocks.COCOA)) {
			cropStates.put(harvestPos, state);
			checkReplant.put(harvestPos, Items.COCOA_BEANS);
		}
		else {
			return true;
		}*/
		if (ConfigHandler.seedCropPairs.containsValue(block)){
			checkReplant.put(harvestPos, ConfigHandler.seedCropPairs.inverse().get(block));
			if (ConfigHandler.cropsWithMoreStates.containsKey(block)){
				cropStates.put(harvestPos, state);
			}
		} else {
			return true;
		}


		
		if (!player.isCreative()) {
			player.getStackInHand(hand).damage(1, player.getRandom(), (ServerPlayerEntity) player);
		}
		
		return true;
	}
	
	public static void onHarvest(World world, Entity entity) {
		if (world.isClient()) {
			return;
		}
		
		if (!(entity instanceof ItemEntity itemEntity)) {
			return;
		}
		
		BlockPos itemPos = entity.getBlockPos();
		if (!checkReplant.containsKey(itemPos)) {
			return;
		}

		ItemStack itemstack = itemEntity.getStack();
		Item item = itemstack.getItem();
/*		if (item.equals(Items.WHEAT_SEEDS)) {
			world.setBlockState(itemPos, Blocks.WHEAT.getDefaultState());
		}
		else if (item.equals(Items.CARROT)) {
			world.setBlockState(itemPos, Blocks.CARROTS.getDefaultState());
		}
		else if (item.equals(Items.POTATO)) {
			world.setBlockState(itemPos, Blocks.POTATOES.getDefaultState());
		}
		else if (item.equals(Items.BEETROOT_SEEDS)) {
			world.setBlockState(itemPos, Blocks.BEETROOTS.getDefaultState());
		}
		else if (item.equals(Items.NETHER_WART)) {
			world.setBlockState(itemPos, Blocks.NETHER_WART.getDefaultState());
		}
		else if (item.equals(Items.COCOA_BEANS)) {
			if (!cropStates.containsKey(itemPos)) {
				checkReplant.remove(itemPos);
				return;
			}
			world.setBlockState(itemPos, cropStates.get(itemPos).with(CocoaBlock.AGE, 0));
			cropStates.remove(itemPos);
		}
		else {
			return;
		}*/
		if (ConfigHandler.seedCropPairs.containsKey(item)){
			Block block = ConfigHandler.seedCropPairs.get(item);
			if (ConfigHandler.cropsWithMoreStates.containsKey(block)){
				if (!cropStates.containsKey(itemPos)) {
					checkReplant.remove(itemPos);
					return;
				}
				IntProperty age = ConfigHandler.cropsWithMoreStates.get(block);
				world.setBlockState(itemPos, cropStates.get(itemPos).with(age, 0));
				cropStates.remove(itemPos);
			} else {
				assert block != null;
				world.setBlockState(itemPos, block.getDefaultState());
			}
		} else {
			return;
		}
		
		checkReplant.remove(itemPos);
		
		if (itemstack.getCount() > 1) {
			itemstack.decrement(1);
		}
		else {
			entity.remove(Entity.RemovalReason.DISCARDED);
		}
	}
}
