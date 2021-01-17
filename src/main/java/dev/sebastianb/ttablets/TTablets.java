package dev.sebastianb.ttablets;


import dev.sebastianb.ttablets.client.gui.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = TTablets.MOD_ID,
        name = TTablets.MOD_NAME,
        version = TTablets.VERSION
)
public class TTablets {


    public static final String MOD_ID = "ttablets";
    public static final String MOD_NAME = "ttablets";
    public static final String VERSION = "1.0-SNAPSHOT";


    public static final Logger TERRA_UTILS_MOD_LOG = LogManager.getLogger(MOD_ID);




    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static TTablets INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        GuiHandler.init();


    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }


}
