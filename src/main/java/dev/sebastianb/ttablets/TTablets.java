package dev.sebastianb.ttablets;

import dev.sebastianb.ttablets.apps.TabletOS;
import dev.sebastianb.ttablets.util.ApplicationRegistry;
import dev.sebastianb.ttablets.util.TTabletRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(TTablets.MOD_ID)
public class TTablets {

    public static final String MOD_ID = "ttablets";
    public static IEventBus MOD_EVENT_BUS;
    public static TabletOS TABLET_OS;


    public TTablets() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        registerComponents(MOD_EVENT_BUS);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> TTablets::clientSetup);
    }

    public static void registerComponents(final IEventBus EVENT_BUS) {
        TTabletRegistry.ITEM_REGISTRY.register(EVENT_BUS);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientSetup() {
        TTablets.TABLET_OS = new TabletOS();
        TTabletRegistry.APPLICATION_REGISTRY.addApplication(TTablets.TABLET_OS);
        try {
            TTabletRegistry.APPLICATION_REGISTRY.setActiveApplicationID(TTablets.TABLET_OS.getID());
        } catch (ApplicationRegistry.IDNotFoundException e) {
            e.printStackTrace();
        }
    }
}