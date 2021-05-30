package dev.sebastianb.ttablets.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.api.Application;
import dev.sebastianb.ttablets.api.ApplicationRegistry;
import dev.sebastianb.ttablets.helper.ByteBuffer2D;
import dev.sebastianb.ttablets.helper.RunningTime;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

@Environment(EnvType.CLIENT)
public class TTabletScreen extends Screen {

    private static final Identifier RESOURCE_BACKGROUND = new Identifier(TTablets.MOD_ID, "textures/gui/ttablet_background.png");

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

    private final RunningTime bootTime = new RunningTime();

    public TTabletScreen() {
        super(new TranslatableText("ttablets"));
    }

    @Override
    public void init() {
        super.init();
        this.bootTime.run();
        this.children().clear();
        this.SCREEN = new BufferedImage(1144, 912, BufferedImage.TYPE_INT_ARGB);
        ApplicationRegistry.APPLICATION_REGISTRY.load();
    }

    @Override
    public void render(@NotNull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);

        int centerX = (width / 2) - WIDTH / 2;
        int centerY = (height / 2) - HEIGHT / 2;

        //RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(RESOURCE_BACKGROUND);
        this.drawTexture(matrix, centerX, centerY, 0, 0, WIDTH, HEIGHT);

        Application app = ApplicationRegistry.APPLICATION_REGISTRY.getActiveApplication();
        if (app.getUpTimeSeconds() <= 2) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(app.getLoadingScreen());
            // warning: 256 and 210 are kinda hacky, replace when you find a formula
            this.drawTexture(matrix, centerX, centerY, 0, 0, 256, 210);
        } else {
            ByteBuffer2D render = app.render(this.SCREEN, mouseX, mouseY);
            displayGLImage(render);
        }
        super.render(matrix, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        // TODO: save state of screen
        super.onClose();
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

    /**
     * A cheaper way of rendering, rather than updating a DynamicTexture every frame.
     * @param image The image to render.
     */
    private void displayGLImage(ByteBuffer2D image) {
        // render as 2D image, max LOD Level, width, height, no border, alpha on, texel data type (idk what that is), and texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) image.getBuffer().flip());
    }

    /**
     * From StackOverflow.
     * @param img The image to resize to the screen dimensions.
     * @return The resized image.
     */
    private static BufferedImage resize(final BufferedImage img) {
        Image tmp = img.getScaledInstance(TTabletScreen.SCREEN_WIDTH, TTabletScreen.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(TTabletScreen.SCREEN_WIDTH, TTabletScreen.SCREEN_HEIGHT, img.getType());
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}
