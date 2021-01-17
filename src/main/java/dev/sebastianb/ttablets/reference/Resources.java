package dev.sebastianb.ttablets.reference;

import dev.sebastianb.ttablets.TTablets;
import net.minecraft.util.ResourceLocation;

public class Resources {
    public static final class Gui {
        protected static final String path = "textures/gui/";

        public static final ResourceLocation TABLET = new ResourceLocation(TTablets.MOD_ID, path + "tablet_gui.png");
    }
}
