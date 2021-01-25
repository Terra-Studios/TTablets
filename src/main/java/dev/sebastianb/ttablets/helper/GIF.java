package dev.sebastianb.ttablets.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.TimerTask;


public class GIF {

    private final ResourceLocation GIF;
    private final GifDecoder DECODER;
    private final GifTimer TIMER;
    private final ArrayList<ByteBuffer2D> BYTE_BUFFER_FRAMES;

    /**
     * Creates a GIF for use in rendering.
     * @param location The location of the GIF, in the resource pack format.
     */
    public GIF(ResourceLocation location) {
        this.GIF = location;
        this.DECODER = new GifDecoder();
        try {
            this.DECODER.read(Minecraft.getInstance().getResourceManager().getResource(this.GIF).getInputStream());
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
            BufferedImage image = this.getFrame(i);
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 being bytes per pixel
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            this.BYTE_BUFFER_FRAMES.add(new ByteBuffer2D(buffer, image.getWidth(), image.getHeight()));
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
    public ResourceLocation getResourceLocation() {
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
