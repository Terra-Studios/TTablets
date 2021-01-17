package dev.sebastianb.ttablets.client.gui;

import dev.sebastianb.ttablets.reference.Resources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Random;


@SideOnly(Side.CLIENT)
public class GUITabletScreen extends GuiScreen {

    protected static final ResourceLocation RESOURCE_BACKGROUND = Resources.Gui.TABLET;

    protected static final int GUI_WIDTH = 241;
    protected static final int GUI_HEIGHT = 209;

    GuiButton powerButton;
    final int POWER_BUTTON = 0;

    private Random random = new Random();

    public GUITabletScreen() {
        System.out.println("HI");


    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int centerX = (width / 2) - GUI_WIDTH / 2;
        int centerY = (height / 2) - GUI_HEIGHT / 2;



        GlStateManager.pushMatrix();
        {

            double scaledX = (double) width / GUI_WIDTH;



            GlStateManager.scale(scaledX,scaledX,0);




            //GlStateManager.scale(1.9F,1.9F,0);


            GlStateManager.translate(0,0,0);





            mc.renderEngine.bindTexture(RESOURCE_BACKGROUND);
            drawTexturedModalRect(0,0, 0,0, GUI_WIDTH, GUI_HEIGHT);
        }
        GlStateManager.popMatrix();


        drawString(mc.fontRenderer, "hello: " + random.nextInt(20), centerX, centerY, 0xFFFFFF);

        ItemStack icon = new ItemStack(Blocks.COMMAND_BLOCK);

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(centerX + 40,centerY + 80,0);
            GlStateManager.scale(5,1,0);
            mc.getRenderItem().renderItemAndEffectIntoGUI(icon, 0, 0);
        }
        GlStateManager.popMatrix();



        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    public void initGui() {
        buttonList.clear(); // clear just in-case there's something
        buttonList.add(powerButton = new GuiButton(POWER_BUTTON, 0,0, 20, 20, "o"));
        super.initGui();
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

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
