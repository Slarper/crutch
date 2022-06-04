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

package com.natamus.replantingcrops.config;

import com.google.common.collect.HashBiMap;
import com.natamus.replantingcrops.util.Reference;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class ConfigHandler { 
	public static PropertyMirror<Boolean> mustHoldHoeForReplanting = PropertyMirror.create(ConfigTypes.BOOLEAN);
	// a Bidirectional Map of the seed - the crop block
	public static HashBiMap<Item, Block> seedCropPairs = HashBiMap.create();
	// for the crop who has state properties except age - like facing
	// (e.g. cocoa has 2 state properties of facing and age).
	// the value is age object
	// different crop has different age object, so you can only modify it by java code instead of data-driven.
	// the age object is in crop block class and Properties class.
	// For add-on mod, call CropsWithMoreStates.put(xx,xx) in initialize().
	public static HashMap<Block, IntProperty> cropsWithMoreStates = new HashMap<>();

	private static final ConfigTree CONFIG = ConfigTree.builder() 
			.beginValue("mustHoldHoeForReplanting", ConfigTypes.BOOLEAN, true)
			.withComment("If enabled, players must hold a hoe in their hand to automatically replant the crop.")
			.finishValue(mustHoldHoeForReplanting::mirror)

			.build();

	private static void writeDefaultConfig(Path path, JanksonValueSerializer serializer) {
		try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
			FiberSerialization.serialize(CONFIG, s, serializer);
		} catch (IOException ignored) {}

	}

	public static void setup() {
		JanksonValueSerializer serializer = new JanksonValueSerializer(false);
		Path p = Paths.get("config", Reference.MOD_ID + ".json");
		writeDefaultConfig(p, serializer);

		try (InputStream s = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
			FiberSerialization.deserialize(CONFIG, s, serializer);
		} catch (IOException | ValueDeserializationException e) {
			System.out.println("Error loading config");
		}

		// temporary; need configs in the future.
		seedCropPairs.put(Items.WHEAT_SEEDS, Blocks.WHEAT);
		seedCropPairs.put(Items.CARROT,Blocks.CARROTS);
		seedCropPairs.put(Items.POTATO,Blocks.POTATOES);
		seedCropPairs.put(Items.BEETROOT_SEEDS,Blocks.BEETROOTS);
		seedCropPairs.put(Items.NETHER_WART,Blocks.NETHER_WART);
		seedCropPairs.put(Items.COCOA_BEANS,Blocks.COCOA);

	}
}