package dev.sebastianb.ttablets.helper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TimerTask;

public class GIF {

    private final Identifier GIF;
    private final GifDecoder DECODER;
    private final GifTimer TIMER;
    private final ArrayList<ByteBuffer2D> BYTE_BUFFER_FRAMES;

    /**
     * Creates a GIF for use in rendering.
     * @param location The location of the GIF, in the resource pack format.
     */
    public GIF(Identifier location) {
        this.GIF = location;
        this.DECODER = new GifDecoder();
        try {
            this.DECODER.read(MinecraftClient.getInstance().getResourceManager().getResource(this.GIF).getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.TIMER = new GifTimer(this.DECODER);
        this.BYTE_BUFFER_FRAMES = new ArrayList<>();
        this.fillByteBufferFrames();
    }

    /**
     * Precreates the ByteBuffers for each frame, so the FPS is saved.
     */
    private void fillByteBufferFrames() {
        for (int i = 0; i < this.DECODER.getFrameCount(); i++) {
            this.BYTE_BUFFER_FRAMES.add(ByteBuffer2D.getByteBuffer2D(this.getFrame(i)));
        }
    }

    /**
     * Resets the current frame position to the beginning.
     */
    public void reset() {
        this.TIMER.run();
    }

    /**
     * Returns the ResourceLocation pointing to the GIF.
     * @return The GIF's ResourceLocation.
     */
    public Identifier getResourceLocation() {
        return this.GIF;
    }

    /**
     * Checks if the frame index specified is valid.
     * @param frame The frame index.
     * @return The validity of the index.
     */
    public boolean isFrameIndexValid(int frame) {
        return frame >= 0 && frame < this.DECODER.getFrameCount();
    }

    /**
     * Finds the current frame and returns it.
     * @return The current frame.
     */
    public BufferedImage getCurrentFrame() {
        return this.DECODER.getFrame(this.TIMER.getCurrentFrame());
    }

    /**
     * Returns the frame at the specified index.
     * @param frame The frame index.
     * @return The frame at the specified index if the index is valid, otherwise null.
     */
    public BufferedImage getFrame(int frame) {
        if (this.isFrameIndexValid(frame))
            return this.DECODER.getFrame(frame);
        else
            return null;
    }

    /**
     * Finds the current frame and returns it as a ByteBuffer2D.
     * @return The current frame.
     */
    public ByteBuffer2D getCurrentFrameAsByteBuffer2D() {
        return this.BYTE_BUFFER_FRAMES.get(this.TIMER.getCurrentFrame());
    }

    /**
     * Returns the frame at the specified index, as a ByteBuffer2D.
     * @param frame The frame index.
     * @return The frame at the specified index if the index is valid, otherwise null.
     */
    public ByteBuffer2D getFrameAsByteBuffer2D(int frame) {
        if (this.isFrameIndexValid(frame))
            return this.BYTE_BUFFER_FRAMES.get(frame);
        else
            return null;
    }

    /**
     * Finds the current frame and returns it as an InputStream.
     * @return The current frame.
     */
    public InputStream getCurrentFrameAsInputStream() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BufferedImage a = this.DECODER.getFrame(this.TIMER.getCurrentFrame());
            ImageIO.write(a, "image/gif", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the frame at the specified index, as an InputStream.
     * @param frame The frame index.
     * @return The frame at the specified index if the index is valid, otherwise null.
     */
    public InputStream getFrameAsInputStream(int frame) {
        if (!this.isFrameIndexValid(frame))
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BufferedImage a = this.DECODER.getFrame(frame);
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
