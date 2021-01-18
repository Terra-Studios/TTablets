package dev.sebastianb.ttablets.client.gui;

import dev.sebastianb.ttablets.reference.Resources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Random;
import java.util.TimerTask;


@SideOnly(Side.CLIENT)
public class GUITabletScreen extends TScreen {

    protected static final ResourceLocation RESOURCE_BACKGROUND = Resources.Gui.TABLET;

    protected static final int GUI_WIDTH = 243;
    protected static final int GUI_HEIGHT = 209;

    GuiButton powerButton;
    final int POWER_BUTTON = 0;

    private Random random = new Random();
    Task t1 = new Task("Task 1");


    public GUITabletScreen() {
        t1.run();
    }


    @Override
    public void initGui() {
        //buttonList.clear(); // clear just in-case there's something
        //buttonList.add(powerButton = new GuiButton(POWER_BUTTON, 0,0, 20, 20, "o"));
        //BufferBuilder b;



        super.initGui();
    }


    private void drawPixel(int X, int Y, int color) {
        drawVerticalLine(X,Y,Y,color);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int centerX = (width / 2) - GUI_WIDTH / 2;
        int centerY = (height / 2) - GUI_HEIGHT / 2;

        //tablet texture behind the screen
        GlStateManager.pushMatrix();
        {
            mc.renderEngine.bindTexture(RESOURCE_BACKGROUND);
            drawTexturedModalRect(centerX, centerY, 0,0, GUI_WIDTH, GUI_HEIGHT);
        }
        GlStateManager.popMatrix();


        int x = centerX + 25;
        int y = centerY + 67;

        //drawn screen
        GlStateManager.pushMatrix();
        {

            //bad for loop, please kill. Makes like 5k objects = 5k drawn pixels
            for (int num = 0; num <= 142; num++) {
                for (int otherNum = 0; otherNum <= 113; otherNum++) {
                    if (num % 2 == 0) {
                        drawPixel(x + num,y + otherNum, Color.RED.getRGB());


                    }
                }
            }
            if (t1.getNumber() % 20 == 0) {
                System.out.println(t1.getNumber());
            }



        }
        GlStateManager.popMatrix();





        drawString(mc.fontRenderer, "hello: " + random.nextInt(20), centerX, centerY, 0xFFFFFF);


        super.drawScreen(mouseX, mouseY, partialTicks);

    }


    @Override
    public void onGuiClosed() {
        t1.cancel();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case POWER_BUTTON:
                System.out.println("hi");
                break;
        }
        super.actionPerformed(button);
    }



}

class Task extends TimerTask {
    private String name;
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
