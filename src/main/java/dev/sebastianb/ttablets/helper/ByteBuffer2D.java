package dev.sebastianb.ttablets.helper;

import java.nio.ByteBuffer;


public class ByteBuffer2D {

    private final ByteBuffer buffer;
    private final int width;
    private final int height;

    /**
     * It's the same as a normal buffer, but it stores the width and height of the image for rendering. Pretty neat.
     */
    public ByteBuffer2D(ByteBuffer buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}