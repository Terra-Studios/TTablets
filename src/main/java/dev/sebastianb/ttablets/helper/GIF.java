package dev.sebastianb.ttablets.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;


public class GIF {

    private final ResourceLocation GIF;
    private final GifDecoder GIF_DECODER;
    private final GifTimer GIF_TIMER;

    /**
     * Creates a GIF for use in rendering.
     * @param location The location of the GIF, in the resource pack format.
     */
    public GIF(ResourceLocation location) {
        this.GIF = location;
        this.GIF_DECODER = new GifDecoder();
        try {
            this.GIF_DECODER.read(Minecraft.getInstance().getResourceManager().getResource(this.GIF).getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.GIF_TIMER = new GifTimer(this.GIF_DECODER);
    }

    /**
     * Resets the current frame position to the beginning.
     */
    public void reset() {
        this.GIF_TIMER.run();
    }

    /**
     * Finds the current frame and returns it.
     * @return The current frame.
     */
    public BufferedImage getCurrentFrame() {
        return this.GIF_DECODER.getFrame(this.GIF_TIMER.getCurrentFrame());
    }

    /**
     * Finds the current frame and returns it as an InputStream.
     * @return The current frame.
     */
    public InputStream getCurrentFrameAsInputStream() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BufferedImage a = this.GIF_DECODER.getFrame(this.GIF_TIMER.getCurrentFrame());
            ImageIO.write(a, "image/gif", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A timer to assist in finding the current frame.
     */
    private static class GifTimer extends TimerTask {

        private long currentTime;
        private int currentFrame;
        private int delay;

        private final GifDecoder gif;

        public GifTimer(GifDecoder gif) {
            this.currentTime = System.currentTimeMillis();
            this.currentFrame = 0;
            this.gif = gif;
            this.delay = this.gif.getDelay(this.currentFrame);
        }

        @Override
        public void run() {
            this.currentTime = System.currentTimeMillis();
            this.currentFrame = 0;
            this.delay = this.gif.getDelay(this.currentFrame);
        }

        public int getCurrentFrame() {
            long current = System.currentTimeMillis() - this.currentTime;
            if (current > delay) {
                this.currentTime = System.currentTimeMillis();
                if (++currentFrame == gif.getFrameCount())
                    currentFrame = 0;
                this.delay = this.gif.getDelay(this.currentFrame);
                return this.currentFrame;
            }
            return this.currentFrame;
        }
    }
}
