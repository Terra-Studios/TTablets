package dev.sebastianb.ttablets;

import dev.sebastianb.ttablets.client.ClientTestEvents;
import dev.sebastianb.ttablets.util.TTabletRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(TTablets.MOD_ID)
public class TTablets {

    public static final String MOD_ID = "ttablets";
    public static IEventBus MOD_EVENT_BUS;



    public TTablets() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        registerComponents(MOD_EVENT_BUS);
        //registerCommonEvents(EVENT_BUS);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> TTablets::registerClientEvents);
    }

    public static void registerComponents(final IEventBus EVENT_BUS) {
        TTabletRegistry.ITEM_REGISTRY.register(EVENT_BUS);
    }

//    public static void registerCommonEvents(final IEventBus EVENT_BUS) {
//        EVENT_BUS.register(EventSubscribers.class);
//    }

    public static void registerClientEvents() {
        System.out.println("HELLO");
        MOD_EVENT_BUS.register(new ClientTestEvents());
    }
}