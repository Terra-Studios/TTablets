package dev.sebastianb.ttablets.client.gui;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.*;

public class GuiOurPad extends TScreen {



    private double vx;
    private double vy;
    private double vw;
    private double vh;


    @Override
    public void initGui() {
        super.initGui();

        vw = ((double) width) - 32.0f;
        vh = vw / (59.0 / 30.0);
        vx = 16.0f;
        vy = (((double) height) - vh) / 2.0f;
    }


    private static void addRect(BufferBuilder bb, double x, double y, double w, double h) {
        bb.pos(x, y, 0.0).endVertex();
        bb.pos(x + w, y, 0.0).endVertex();
        bb.pos(x + w, y + h, 0.0).endVertex();
        bb.pos(x, y + h, 0.0).endVertex();
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float ptt) {
        drawDefaultBackground();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_CULL_FACE);
        glColor4f(0.73f, 0.73f, 0.73f, 1.0f);

        Tessellator t = Tessellator.getInstance();
        {
            BufferBuilder bb = t.getBuffer();
            bb.begin(GL_QUADS, DefaultVertexFormats.POSITION);
            addRect(bb, vx, vy - 16, vw, 16);
            addRect(bb, vx, vy + vh, vw, 16);
            addRect(bb, vx - 16, vy, 16, vh);
            addRect(bb, vx + vw, vy, 16, vh);
        }
        t.draw();

        glEnable(GL_TEXTURE_2D);

//        if(pad.view != null) {
//            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//            pad.view.draw(vx, vy + vh, vx + vw, vy);
//        }

        glEnable(GL_CULL_FACE);
    }


}
