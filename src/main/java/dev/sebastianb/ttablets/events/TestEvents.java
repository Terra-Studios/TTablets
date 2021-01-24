package dev.sebastianb.ttablets.events;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestEvents {

    public TestEvents() {
        System.out.println("class loaded ha");
    }

    @SubscribeEvent
    public static void testTimesASecond(RenderGameOverlayEvent.Post event) {
        System.out.println("hi");

    }
}
