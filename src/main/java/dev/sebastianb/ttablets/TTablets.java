package dev.sebastianb.ttablets;

import dev.sebastianb.ttablets.items.Items;
import net.fabricmc.api.ModInitializer;

public final class TTablets implements ModInitializer {

    public static final String MOD_ID = "ttablets";

    @Override
    public void onInitialize() {
        Items.registerItems();
    }
}