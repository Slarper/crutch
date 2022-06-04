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

package com.natamus.replantingcrops;

import com.natamus.replantingcrops.config.ConfigHandler;
import com.natamus.replantingcrops.events.CropEvent;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() { 
		ConfigHandler.setup();

/*
		ConfigHandler.cropAgePairs.put(Blocks.COCOA, CocoaBlock.AGE);
*/


		registerEvents();
	}
	
	private void registerEvents() {
		PlayerBlockBreakEvents.BEFORE.register(
				(world, player, pos, state, entity) -> CropEvent.onHarvest(world,player,pos,state)
		);
		
		ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerWorld world) -> CropEvent.onHarvest(world, entity));
	}
}
