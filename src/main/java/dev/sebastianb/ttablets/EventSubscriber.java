package dev.sebastianb.ttablets;

import dev.sebastianb.ttablets.items.ItemTablet;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(modid = TTablets.MOD_ID)
public class EventSubscriber {



    /* register items */
    @SubscribeEvent
    public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();


        registry.register(new ItemTablet("tablet"));
        TTablets.TERRA_UTILS_MOD_LOG.debug("Registered Items");


    }

}
