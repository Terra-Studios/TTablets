package dev.sebastianb.ttablets.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.ttablets.TTablets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.TimerTask;


public class TTabletScreen extends Screen {

    private static final ResourceLocation RESOURCE_BACKGROUND = new ResourceLocation(TTablets.MOD_ID, "textures/gui/ttablet_background.png");

    private static final int WIDTH = 243;
    private static final int HEIGHT = 209;
    private static final int SCREEN_WIDTH = 143;
    private static final int SCREEN_HEIGHT = 114;

    private NativeImage SCREEN;
    // remember to call updateDynamicTexture() every time the screen changes
    private DynamicTexture SCREEN_TEXTURE;
    private ResourceLocation SCREEN_TEXTURE_LOCATION;

    private final BootTime bootTime = new BootTime("first_run");

    public TTabletScreen() {
        super(new TranslationTextComponent("ttablets"));
    }

    @Override
    public void init() {
        super.init();
        this.bootTime.run();
        this.buttons.clear();
        this.SCREEN = new NativeImage(143, 114, true);
        this.SCREEN_TEXTURE = new DynamicTexture(SCREEN);
        this.SCREEN_TEXTURE_LOCATION = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("ttablet_screen", SCREEN_TEXTURE);
    }

    @Override
    public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        //TODO: render screen at 320x240
        this.renderBackground(matrix);
        int centerX = (width / 2) - WIDTH / 2;
        int centerY = (height / 2) - HEIGHT / 2;
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(RESOURCE_BACKGROUND);
        this.blit(matrix, centerX, centerY, 0, 0, WIDTH, HEIGHT);


        int[] colors = new int[]{0xFF0000FF, 0xFF00FF00, 0xFFFF0000};
        int color = colors[this.bootTime.getTimeSeconds() % 3];
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                this.SCREEN.setPixelRGBA(x, y, color);
            }
        }

        this.SCREEN_TEXTURE.updateDynamicTexture();
        Minecraft.getInstance().getTextureManager().bindTexture(SCREEN_TEXTURE_LOCATION);
        this.blit(matrix, centerX + 25, centerY + 67, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        super.render(matrix, mouseX, mouseY, partialTicks);
    }

    @Override
    public void closeScreen() {
        // todo: save state of screen
        super.closeScreen();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
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
