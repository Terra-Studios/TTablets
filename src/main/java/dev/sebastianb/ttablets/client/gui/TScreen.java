package dev.sebastianb.ttablets.client.gui;

import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class TScreen extends GuiScreen {

    //Code "borrowed" from web displays. Check out the mod!

    public static TScreen CURRENT_SCREEN = null;

    private final HashMap<Class<? extends Event>, Method> eventMap = new HashMap<>();

    protected int syncTicks = 40;
    private int syncTicksLeft = -1;


    public TScreen() {
        Method[] methods = getClass().getMethods();

        for(Method m : methods) {
            if(m.getAnnotation(GuiSubscribe.class) != null) {
                if (!Modifier.isPublic(m.getModifiers())) {
                    throw new RuntimeException("Found non public @GuiSubscribe");
                }

                Class<?> params[] = m.getParameterTypes();
                if(params.length != 1 || !Event.class.isAssignableFrom(params[0])) {
                    throw new RuntimeException("Invalid parameters for @GuiSubscribe");
                }

                eventMap.put((Class<? extends Event>) params[0], m);
            }
        }

    }




    protected void requestSync() {
        syncTicksLeft = syncTicks - 1;
    }

    protected boolean syncRequested() {
        return syncTicksLeft >= 0;
    }

    protected void abortSync() {
        syncTicksLeft = -1;
    }

    protected void sync() {

    }


    @Override
    public void updateScreen() {
        if(syncTicksLeft >= 0) {
            if(--syncTicksLeft < 0) {
                sync();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }




}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface GuiSubscribe {

}