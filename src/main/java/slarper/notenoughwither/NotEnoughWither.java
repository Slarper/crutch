package slarper.notenoughwither;

import net.fabricmc.api.ModInitializer;

public class NotEnoughWither implements ModInitializer {
    public static final String ID;

    static {
        ID = "notenoughwither";
    }
    @Override
    public void onInitialize() {
        Items.init();
    }
}
