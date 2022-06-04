package com.natamus.replantingcrops.config;

import java.util.ArrayList;

public class defaultCropConfigObject {
    public ArrayList<Entry> crops;

    private defaultCropConfigObject(){
        crops = new ArrayList<>();
        crops.add(new Entry("minecraft:wheat_seeds","minecraft:wheat",false));
        crops.add(new Entry("minecraft:carrot","minecraft:carrots",false));
        crops.add(new Entry("minecraft:potato","minecraft:potatoes",false));
        crops.add(new Entry("minecraft:beetroot_seeds","minecraft:beetroots",false));
        crops.add(new Entry("minecraft:nether_wart","minecraft:nether_wart",false));
        crops.add(new Entry("minecraft:cocoa_beans","minecraft:cocoa",true));
    }

    public static defaultCropConfigObject getInstance(){
        return Inner.instance;
    }

    private static class Inner {
        private static final defaultCropConfigObject instance = new defaultCropConfigObject();
    }

    public static class Entry {
        public String seed;
        public String block;
        public boolean withOtherPropertiesExceptAge;

        public Entry(String seed, String block, boolean withOtherPropertiesExceptAge) {
            this.seed = seed;
            this.block = block;
            this.withOtherPropertiesExceptAge = withOtherPropertiesExceptAge;
        }
    }
}
