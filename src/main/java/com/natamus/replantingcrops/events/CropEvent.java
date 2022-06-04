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
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class CropEvent {
	private static final HashMap<BlockPos, Item> checkreplant = new HashMap<>();
	private static final HashMap<BlockPos, BlockState> cocoaStates = new HashMap<>();
	
	public static boolean onHarvest(World world, PlayerEntity player, BlockPos hpos, BlockState state) {
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
		
		if (block.equals(Blocks.WHEAT)) {
			checkreplant.put(hpos, Items.WHEAT_SEEDS);
		}
		else if (block.equals(Blocks.CARROTS)) {
			checkreplant.put(hpos, Items.CARROT);
		}
		else if (block.equals(Blocks.POTATOES)) {
			checkreplant.put(hpos, Items.POTATO);
		}
		else if (block.equals(Blocks.BEETROOTS)) {
			checkreplant.put(hpos, Items.BEETROOT_SEEDS);
		}
		else if (block.equals(Blocks.NETHER_WART)) {
			checkreplant.put(hpos, Items.NETHER_WART);
		}
		else if (block.equals(Blocks.COCOA)) {
			cocoaStates.put(hpos, state);
			checkreplant.put(hpos, Items.COCOA_BEANS);
		}
		else {
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
		
		if (!(entity instanceof ItemEntity itementity)) {
			return;
		}
		
		BlockPos ipos = entity.getBlockPos();
		if (!checkreplant.containsKey(ipos)) {
			return;
		}

		ItemStack itemstack = itementity.getStack();
		Item item = itemstack.getItem();
		if (item.equals(Items.WHEAT_SEEDS)) {
			world.setBlockState(ipos, Blocks.WHEAT.getDefaultState());
		}
		else if (item.equals(Items.CARROT)) {
			world.setBlockState(ipos, Blocks.CARROTS.getDefaultState());
		}
		else if (item.equals(Items.POTATO)) {
			world.setBlockState(ipos, Blocks.POTATOES.getDefaultState());
		}
		else if (item.equals(Items.BEETROOT_SEEDS)) {
			world.setBlockState(ipos, Blocks.BEETROOTS.getDefaultState());
		}
		else if (item.equals(Items.NETHER_WART)) {
			world.setBlockState(ipos, Blocks.NETHER_WART.getDefaultState());
		}
		else if (item.equals(Items.COCOA_BEANS)) {
			if (!cocoaStates.containsKey(ipos)) {
				checkreplant.remove(ipos);
				return;
			}
			world.setBlockState(ipos, cocoaStates.get(ipos).with(CocoaBlock.AGE, 0));
			cocoaStates.remove(ipos);
		}
		else {
			return;
		}
		
		checkreplant.remove(ipos);
		
		if (itemstack.getCount() > 1) {
			itemstack.decrement(1);
		}
		else {
			entity.remove(Entity.RemovalReason.DISCARDED);
		}
	}
}
