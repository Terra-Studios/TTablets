package dev.sebastianb.ttablets.client;

import com.google.common.base.Preconditions;
import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.items.TTabletsItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(modid = TTablets.MOD_ID, value = Side.CLIENT)
public class ClientEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_VARIANT = "normal";

    @SubscribeEvent
    public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event) {



        registerItemModel(TTabletsItems.TABLET);


        LOGGER.debug("Registered models");

    }


    private static void registerItemModel(@Nonnull final Item item) {
        Preconditions.checkNotNull(item, "Item cannot be null!");
        final ResourceLocation registryName = item.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item Registry Name cannot be null!");
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), DEFAULT_VARIANT));
    }
}
