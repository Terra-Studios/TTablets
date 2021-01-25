package dev.sebastianb.ttablets.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.helper.GIF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.BufferUtils;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL11.*;


public class TTabletScreen extends Screen {

    private static final ResourceLocation RESOURCE_BACKGROUND = new ResourceLocation(TTablets.MOD_ID, "textures/gui/ttablet_background.png");

    // for testing GIFs
    private final GIF TEST_GIF = new GIF(new ResourceLocation(TTablets.MOD_ID, "media/gif/weeb.gif"));

    private static final int WIDTH                  = 243;
    private static final int HEIGHT                 = 209;
    // SCREEN_PLACE_WIDTH and SCREEN_PLACE_HEIGHT are the base resolution in pixels
    private static final int SCREEN_PLACE_WIDTH     = 143;
    private static final int SCREEN_PLACE_HEIGHT    = 114;
    private static final int SCALED_NUMBER          = 8;
    private static final int SCREEN_WIDTH           = SCREEN_PLACE_WIDTH * SCALED_NUMBER;  // 1144
    private static final int SCREEN_HEIGHT          = SCREEN_PLACE_HEIGHT * SCALED_NUMBER; //  912
    // used to properly scale the screen
    private static final float DOWNSCALED_VALUE     = 1f / SCALED_NUMBER;

    private BufferedImage SCREEN;

    private final BootTime bootTime = new BootTime();


    public TTabletScreen() {
        super(new TranslationTextComponent("ttablets"));
    }

    @Override
    public void init() {
        super.init();
        this.bootTime.run();
        this.buttons.clear();
        this.SCREEN = new BufferedImage(1144, 912, BufferedImage.TYPE_INT_ARGB);
        this.TEST_GIF.reset();
    }

    @Override
    public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);

        int centerX = (width / 2) - WIDTH / 2;
        int centerY = (height / 2) - HEIGHT / 2;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(RESOURCE_BACKGROUND);
        this.blit(matrix, centerX, centerY, 0, 0, WIDTH, HEIGHT);

        // testing, remember instead to render the GIF to part of this.SCREEN and render that
        displayGLImage(TEST_GIF.getCurrentFrame());
        super.render(matrix, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void closeScreen() {
        // TODO: save state of screen
        super.closeScreen();
    }

    /**
     * Old debug method, puts various colors on this.SCREEN
     */
    private void displayColors() {
        int red = 0xFF0000;
        int green = 0x00FF00;
        int blue = 0x0000FF;

        int color = new int[]{red, green, blue}[this.bootTime.getTimeSeconds() % 3];
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                this.SCREEN.setRGB(x, y, color);
            }
        }
    }
    /*
    private void displayImage(MatrixStack matrix, NativeImage image, int frameNum) {
        try {
            NativeImage newImage = NativeImage.read(Objects.requireNonNull(readGifFrame(frameNum)));
            for (int x = 0; x < SCREEN_WIDTH; x++) {
                for (int y = 0; y < SCREEN_HEIGHT; y++) {
                    image.setPixelRGBA(x,y, newImage.getPixelRGBA(x,y));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.SCREEN_TEXTURE.updateDynamicTexture();
        RenderSystem.scalef(DOWNSCALED_VALUE, DOWNSCALED_VALUE, DOWNSCALED_VALUE);
        Minecraft.getInstance().getTextureManager().bindTexture(SCREEN_TEXTURE_LOCATION);
        this.blit(matrix,
                ((width / 2) * SCALED_NUMBER) - (96 * SCALED_NUMBER),
                ((height / 2) * SCALED_NUMBER) - (37 * SCALED_NUMBER),
                0, 0,
                SCREEN_WIDTH, SCREEN_HEIGHT);
    }*/

    /**
     * A cheaper way of rendering, rather than updating a DynamicTexture every frame.
     * @param image The image to render.
     */
    public void displayGLImage(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); // 4 being bytes per pixel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)((pixel >> 16) & 0xFF));
                buffer.put((byte)((pixel >> 8) & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)((pixel >> 24) & 0xFF));
            }
        }
        displayGLImage(buffer, image.getWidth(), image.getHeight());
    }

    public void displayGLImage(ByteBuffer image, int width, int height) {
        // render as 2D image, max LOD Level, width, height, no border, alpha on, texel data type (idk what that is), and texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) image.flip());
    }


    private static class BootTime extends TimerTask {

        private long number;

        @Override
        public void run() {
            number = System.currentTimeMillis();
        }

        public long getTime() {
            return System.currentTimeMillis() - number;
        }

        public int getTimeSeconds() {
            return (int)(System.currentTimeMillis() - number) / 1000;
        }
    }
}
