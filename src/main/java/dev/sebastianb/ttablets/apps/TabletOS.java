package dev.sebastianb.ttablets.apps;

import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.api.Application;
import dev.sebastianb.ttablets.helper.ByteBuffer2D;
import dev.sebastianb.ttablets.helper.GIF;
import net.minecraft.util.Identifier;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public final class TabletOS extends Application {

    private GIF TEST_GIF;

    public TabletOS() {
        super(TTablets.MOD_ID + "_os",
                new TranslatableText("gui.ttablets_os.title"),
                new Identifier(TTablets.MOD_ID, "textures/gui/os/logo.png"),
                new Identifier(TTablets.MOD_ID, "textures/gui/os/loading_screen.png"));
    }

    @Override
    public void initResources() {
        TEST_GIF = new GIF(new Identifier(TTablets.MOD_ID, "media/gif/weeb.gif"));
    }

    @Override
    @NotNull
    public ByteBuffer2D render(@NotNull final BufferedImage previousFrame, int pixelX, int pixelY) {
        return TEST_GIF.getCurrentFrameAsByteBuffer2D();
    }
}
