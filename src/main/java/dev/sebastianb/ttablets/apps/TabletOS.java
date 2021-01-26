package dev.sebastianb.ttablets.apps;

import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.api.IApplication;
import dev.sebastianb.ttablets.helper.ByteBuffer2D;
import dev.sebastianb.ttablets.helper.GIF;
import dev.sebastianb.ttablets.helper.RunningTime;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;


public final class TabletOS extends IApplication {

    private GIF TEST_GIF;


    public TabletOS() {
        super(TTablets.MOD_ID + "_os",
                new TranslationTextComponent("gui.ttablets_os.title"),
                new ResourceLocation(TTablets.MOD_ID, "textures/gui/os/logo.png"),
                new ResourceLocation(TTablets.MOD_ID, "textures/gui/os/loading_screen.png"));
    }

    @Override
    public void initResources() {
        TEST_GIF = new GIF(new ResourceLocation(TTablets.MOD_ID, "media/gif/weeb.gif"));
    }

    @Override
    @Nonnull
    public ByteBuffer2D render(@Nonnull final BufferedImage previousFrame, int pixelX, int pixelY) {
        return TEST_GIF.getCurrentFrameAsByteBuffer2D();
    }
}
