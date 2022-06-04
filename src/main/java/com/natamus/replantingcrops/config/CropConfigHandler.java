package com.natamus.replantingcrops.config;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.natamus.replantingcrops.mixin.StateAccessor;
import com.natamus.replantingcrops.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

// for convincing all items are registered, we use lazy-mode.
// so don't load this class on mod initialization
public class CropConfigHandler {
    // a Bidirectional Map of the seed - the crop block
    public static HashBiMap<Item, Block> seedCropPairs = HashBiMap.create();
    // for the crop who has state properties except age - like facing
    // (e.g. cocoa has 2 state properties of facing and age).
    // the value is age object
    // different crop has different age object, so you can only modify it by java code instead of data-driven.
    // the age object is in crop block class and Properties class.
    // For add-on mod, call CropsWithMoreStates.put(xx,xx) in initialize().
    public static HashMap<Block, IntProperty> cropAgePairs = new HashMap<>();
    public static ArrayList<Block> cropsWithOtherProperties = new ArrayList<>();

    public final static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();


    public static void registerCropsWithOtherProperties(Item item, Block block){
        ImmutableMap<Property<?>, Comparable<?>> entries = ((StateAccessor)block.getDefaultState()).getEntries();
        ImmutableSet<Property<?>> properties = entries.keySet();
        for(Property<?> property : properties){
            if (property instanceof IntProperty && property.getName().equals("age")){
                cropAgePairs.put(block, (IntProperty) property);
                cropsWithOtherProperties.add(block);
                seedCropPairs.put(item,block);
                return;
            }
        }
    }

    public static Item getItemById(String id){
        return Registry.ITEM.get(new Identifier(id));
    }

    public static Block getBlockById(String id){
        return Registry.BLOCK.get(new Identifier(id));
    }

    static  {
        Path p = Paths.get("config", Reference.MOD_ID + ".crops" + ".json");
        if (!Files.exists(p)){
            String json = GSON.toJson(defaultCropConfigObject.getInstance());
            try {
                Files.writeString(p,json, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            } catch (IOException e) {
                Reference.LOGGER.error("Unable to write default config",e);
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(p)) {
            ArrayList<SeedCropEntry> crops = GSON.fromJson(reader, CropConfigObject.class).crops;
            for(SeedCropEntry entry:crops){
                Item item = getItemById(entry.seed);
                Block block = getBlockById(entry.block);
                if (item == Items.AIR || block == Blocks.AIR){
                    continue;
                }
                if(entry.withOtherPropertiesExceptAge){
                    registerCropsWithOtherProperties(item, block);
                } else {
                    seedCropPairs.put(item, block);
                }
            }
        } catch (IOException | JsonParseException e) {
            Reference.LOGGER.error("Error loading config",e);
        }
    }

}
