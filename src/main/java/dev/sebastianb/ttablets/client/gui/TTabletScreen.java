package dev.sebastianb.ttablets.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.decoder.GifDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.TimerTask;


public class TTabletScreen extends Screen {

    private static final ResourceLocation RESOURCE_BACKGROUND = new ResourceLocation(TTablets.MOD_ID, "textures/gui/ttablet_background.png");
    private static final ResourceLocation TEST_GIF = new ResourceLocation(TTablets.MOD_ID, "media/gif/weeb.gif");
    ByteArrayOutputStream os = new ByteArrayOutputStream();


    private static final int WIDTH                  = 243;
    private static final int HEIGHT                 = 209;
    private static final int SCREEN_PLACE_WIDTH     = 143; // this variable & one beneath is the base resolution in pixels
    private static final int SCREEN_PLACE_HEIGHT    = 114;
    private static final int SCALED_NUMBER          = 8;
    private static final int SCREEN_WIDTH           = SCREEN_PLACE_WIDTH * SCALED_NUMBER;  // 1144
    private static final int SCREEN_HEIGHT          = SCREEN_PLACE_HEIGHT * SCALED_NUMBER; //  912

    private static final float DOWNSCALED_VALUE     = 1f / SCALED_NUMBER; // 0.125 or 1/8 | this value is used to properly scale the screen

    private NativeImage SCREEN;
    // remember to call updateDynamicTexture() every time the screen changes
    private DynamicTexture SCREEN_TEXTURE;
    private ResourceLocation SCREEN_TEXTURE_LOCATION;

    private final BootTime bootTime = new BootTime("first_run");

    public TTabletScreen() {
        super(new TranslationTextComponent("ttablets"));
    }


    /**
     * test method to alternate between colors
     */
    protected void displayColors() {
        int blue = 0xFF0000FF;
        int green = 0xFF00FF00;
        int red = 0xFFFF0000;

        int[] colors = new int[]{blue, green, red};
        int color = colors[this.bootTime.getTimeSeconds() % colors.length];
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                this.SCREEN.setPixelRGBA(x, y, color);
            }
        }
    }


    protected InputStream readGifFrame(int frameNum) {
        GifDecoder d = new GifDecoder();

        try {
            InputStream GIF = Minecraft.getInstance().getResourceManager().getResource(TEST_GIF).getInputStream();
            d.read(GIF);

            BufferedImage a = d.getFrame(frameNum);
            ImageIO.write(a, "gif", os);

            return new ByteArrayInputStream(os.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Oopsie whoopsie I messed up"); // this shouldn't happen
        return null;
    }

    protected void displayImage(int frameNum) {

        try {
            NativeImage image = NativeImage.read(readGifFrame(frameNum));
            for (int x = 0; x < SCREEN_WIDTH; x++) {
                for (int y = 0; y < SCREEN_HEIGHT; y++) {
                    this.SCREEN.setPixelRGBA(x,y, image.getPixelRGBA(x,y));

                }
            }
            System.out.println(image.getHeight());
            System.out.println("This: " + SCREEN_HEIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void init() {
        super.init();
        this.bootTime.run();
        this.buttons.clear();
        this.SCREEN = new NativeImage(1144, 912, true);
        this.SCREEN_TEXTURE = new DynamicTexture(SCREEN);
        this.SCREEN_TEXTURE_LOCATION = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("ttablet_screen", SCREEN_TEXTURE);



    }

    @Override
    public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        int centerX = (width / 2) - WIDTH / 2;
        int centerY = (height / 2) - HEIGHT / 2;
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(RESOURCE_BACKGROUND);
        this.blit(matrix, centerX, centerY, 0, 0, WIDTH, HEIGHT);

        //displayColors();
        displayImage(1);


        RenderSystem.scalef(DOWNSCALED_VALUE, DOWNSCALED_VALUE, DOWNSCALED_VALUE);


        this.SCREEN_TEXTURE.updateDynamicTexture();
        Minecraft.getInstance().getTextureManager().bindTexture(SCREEN_TEXTURE_LOCATION);

        this.blit(matrix, ((width / 2) * SCALED_NUMBER) - (96 * SCALED_NUMBER), ((height / 2) * SCALED_NUMBER) - (37 * SCALED_NUMBER), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        //this.blit(matrix, centerX, centerY, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);




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




    private static class BootTime extends TimerTask {

        private final String name;
        private long number;

        public BootTime(String name) {
            this.name = name;
        }

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
