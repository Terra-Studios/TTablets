package dev.sebastianb.ttablets.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.ttablets.TTablets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.TimerTask;


public class TTabletScreen extends Screen {

    private static final ResourceLocation RESOURCE_BACKGROUND = new ResourceLocation(TTablets.MOD_ID, "textures/gui/ttablet_background.png");

    private static final int WIDTH = 243;
    private static final int HEIGHT = 209;

    private final Button powerButton = new Button(
            0,
            0,
            20,
            20,
            new TranslationTextComponent("gui.power_button.title"),
            (button) -> {});

    private final Random random = new Random();
    Task t1 = new Task("Task 1");


    public TTabletScreen() {
        super(new TranslationTextComponent("ttablets"));
        t1.run();
    }

    @Override
    public void init() {
        super.init();
        this.buttons.clear();
        this.buttons.add(powerButton);
    }

    @Override
    public void render(@Nonnull MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        int centerX = (width / 2) - WIDTH / 2;
        int centerY = (height / 2) - HEIGHT / 2;
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(RESOURCE_BACKGROUND);
        this.blit(matrix, centerX, centerY, 0, 0, WIDTH, HEIGHT);

        Minecraft.getInstance().fontRenderer.drawString(matrix, "hello: " + random.nextInt(20), centerX, centerY, 0xFFFFFF);

        super.render(matrix, mouseX, mouseY, partialTicks);
    }

    @Override
    public void closeScreen() {
        t1.cancel();
        super.closeScreen();
    }


    private static class Task extends TimerTask {

        private final String name;
        private int number;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            number = 0;
        }

        public int getNumber() {
            number++;
            return number;
        }
    }
}
