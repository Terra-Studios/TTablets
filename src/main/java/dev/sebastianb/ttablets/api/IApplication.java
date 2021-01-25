package dev.sebastianb.ttablets.api;

import dev.sebastianb.ttablets.helper.RunningTime;
import dev.sebastianb.ttablets.util.ApplicationRegistry;
import dev.sebastianb.ttablets.util.TTabletRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

/**
 * Extend this class to create a custom TTablets application.
 */
public abstract class IApplication {

    private final String ID;
    private final TranslationTextComponent NAME;
    private final ResourceLocation ICON;
    private final ResourceLocation LOADING_SCREEN;

    private boolean ACTIVE;
    final RunningTime RUNTIME;

    /**
     * Creates a new application, and registers it. You should load all your resources in this constructor, to eliminate lag when opening the app.
     * Applications should be registered when the client is finished loading.
     * @param ID Your application must have a unique ID. I suggest using "modid_nameofapp".
     * @param name The user-friendly name of the app.
     * @param icon The path to the user-friendly icon of the app. Size must be a power of 2. Recommended size is 64x64.
     * @param loadingScreen The path to the screen. It is currently 1144 x 912 pixels. It will display when your app starts.
     */
    public IApplication(@Nonnull String ID, @Nonnull TranslationTextComponent name, @Nonnull ResourceLocation icon, @Nonnull ResourceLocation loadingScreen) {
        this.ID = ID;
        this.NAME = name;
        this.ICON = icon;
        this.LOADING_SCREEN = loadingScreen;
        this.RUNTIME = new RunningTime();
        this.register();
    }

    /**
     * Registers the application.
     */
    private void register() {
        TTabletRegistry.APPLICATION_REGISTRY.addApplication(this);
    }

    /**
     * @return The ID of the application.
     */
    @Nonnull
    public final String getID() {
        return this.ID;
    };

    /**
     * @return The user-friendly name of the app.
     */
    @Nonnull
    public final TranslationTextComponent getDisplayName() {
        return this.NAME;
    };

    /**
     * @return The path to the user-friendly icon of the app. Size must be a power of 2. Recommended size is 64x64.
     */
    @Nonnull
    public final ResourceLocation getIcon() {
        return this.ICON;
    };

    /**
     * @return The path to the loading screen of the application.
     */
    @Nonnull
    public final ResourceLocation getLoadingScreen() {
        return this.LOADING_SCREEN;
    };

    /**
     * @return Whether this application is running or not.
     */
    @Nonnull
    public final boolean getActive() {
        return this.ACTIVE;
    }

    /**
     * This function should only be called by the active Tablet OS. Please do not touch it!
     * @param active Whether the application is active or not.
     */
    public final void setActive(@Nonnull boolean active) {
        this.ACTIVE = active;
    }

    /**
     * @return The uptime of the application, in milliseconds.
     */
    public final long getUpTime() {
        return this.RUNTIME.getTime();
    }

    /**
     * @return The uptime of the application, in seconds.
     */
    public final int getUpTimeSeconds() {
        return this.RUNTIME.getTimeSeconds();
    }

    /**
     * Renders the application to the screen. It is only called when the application is active. Please return the input image if nothing has been modified.
     * @param previousFrame The previously drawn frame.
     * @param mouseX The X position of the mouse, from 0 to 1144.
     * @param mouseY The Y position of the mouse, from 0 to 912.
     */
    @Nonnull
    public abstract BufferedImage render(@Nonnull final BufferedImage previousFrame, int mouseX, int mouseY);

    /**
     * Fired when the mouse is pressed and the application is active.
     * @param mouseX The X position of the mouse, from 0 to 1144.
     * @param mouseY The Y position of the mouse, from 0 to 912.
     */
    public void onMousePressed(int mouseX, int mouseY) {}

    /**
     * Fired when a key is pressed and the application is active.
     * @param keycode The key pressed.
     */
    public void onKeyPressed(int keycode) {}
}
