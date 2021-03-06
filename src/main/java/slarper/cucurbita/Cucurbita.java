package slarper.cucurbita;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slarper.cucurbita.item.Items;

import static slarper.cucurbita.Sounds.OPEN_BOTTLE_EVENT;

public class Cucurbita implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String ID = "cucurbita";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Items.init();
		Registry.register(Registry.SOUND_EVENT, Sounds.OPEN_BOTTLE, OPEN_BOTTLE_EVENT);

	}
}
