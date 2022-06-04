package com.natamus.replantingcrops.config;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.natamus.replantingcrops.mixin.StateAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;

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

    public static void registerCropsWithOtherProperties(Item item, Block block){
        seedCropPairs.put(item,block);
        ImmutableMap<Property<?>, Comparable<?>> entries = ((StateAccessor)block.getDefaultState()).getEntries();
        ImmutableSet<Property<?>> properties = entries.keySet();
        for(Property<?> property : properties){
            if (property instanceof IntProperty && property.getName().equals("age")){
                cropAgePairs.put(Blocks.COCOA, (IntProperty) property);
                cropsWithOtherProperties.add(block);
                return;
            }
        }
    }
    static  {


        // temporary; need configs in the future.
        seedCropPairs.put(Items.WHEAT_SEEDS, Blocks.WHEAT);
        seedCropPairs.put(Items.CARROT,Blocks.CARROTS);
        seedCropPairs.put(Items.POTATO,Blocks.POTATOES);
        seedCropPairs.put(Items.BEETROOT_SEEDS,Blocks.BEETROOTS);
        seedCropPairs.put(Items.NETHER_WART,Blocks.NETHER_WART);

        registerCropsWithOtherProperties(Items.COCOA_BEANS, Blocks.COCOA);
    }
}
