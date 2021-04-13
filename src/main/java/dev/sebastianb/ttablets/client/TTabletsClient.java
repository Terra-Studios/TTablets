package dev.sebastianb.ttablets.client;

import dev.sebastianb.ttablets.apps.TabletOS;
import dev.sebastianb.ttablets.api.ApplicationRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static dev.sebastianb.ttablets.api.ApplicationRegistry.APPLICATION_REGISTRY;

@Environment(EnvType.CLIENT)
public final class TTabletsClient implements ClientModInitializer {

    public static TabletOS TABLET_OS;

    @Override
    public void onInitializeClient() {
        TABLET_OS = new TabletOS();
        APPLICATION_REGISTRY.addApplication(TABLET_OS);
        try {
            APPLICATION_REGISTRY.setActiveApplicationID(TABLET_OS.getID());
        } catch (ApplicationRegistry.IDNotFoundException e) {
            e.printStackTrace();
        }
    }
}
