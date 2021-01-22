package dev.sebastianb.ttablets;

import dev.sebastianb.ttablets.util.TTabletRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(TTablets.MOD_ID)
public class TTablets {

    public static final String MOD_ID = "ttablets";


    public TTablets() {
        IEventBus EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        registerComponents(EVENT_BUS);
        //registerCommonEvents(EVENT_BUS);
        //DistExecutor.runWhenOn(Dist.CLIENT, () -> TTablets::registerClientEvents);
    }

    public static void registerComponents(final IEventBus EVENT_BUS) {
        TTabletRegistry.ITEM_REGISTRY.register(EVENT_BUS);
    }

    /*public static void registerCommonEvents(final IEventBus EVENT_BUS) {
        EVENT_BUS.register(EventSubscribers.class);
    }

    public static void registerClientEvents() {
        IEventBus EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        EVENT_BUS.register(ClientEventSubscribers.class);
    }*/
}