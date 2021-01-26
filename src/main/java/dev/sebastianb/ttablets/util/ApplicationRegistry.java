package dev.sebastianb.ttablets.util;

import dev.sebastianb.ttablets.api.IApplication;

import java.util.HashMap;


public final class ApplicationRegistry {

    private String ACTIVE_APPLICATION_ID = null;
    private boolean loaded = false;
    private final HashMap<String,IApplication> APPLICATIONS = new HashMap<>();


    public void addApplication(IApplication application) {
        if (!isApplicationRegistered(application)) {
            APPLICATIONS.put(application.getID(), application);
        }
    }

    public boolean isApplicationRegistered(IApplication application) {
        return APPLICATIONS.containsKey(application.getID());
    }

    public String getActiveApplicationID() {
        return ACTIVE_APPLICATION_ID;
    }

    public IApplication getActiveApplication() {
        return APPLICATIONS.get(ACTIVE_APPLICATION_ID);
    }

    public void setActiveApplicationID(String ID) throws IDNotFoundException {
        if (APPLICATIONS.containsKey(ID)) {
            if (ACTIVE_APPLICATION_ID != null)
                APPLICATIONS.get(ACTIVE_APPLICATION_ID).setActive(false);
            ACTIVE_APPLICATION_ID = ID;
            APPLICATIONS.get(ID).setActive(true);
        } else
            throw new IDNotFoundException("Application ID not registered");
    }

    public void load() {
        resetAll();
        if (!loaded) {
            for (IApplication application : APPLICATIONS.values())
                application.initResources();
            loaded = true;
        }
    }

    public void resetAll() {
        for (IApplication application : APPLICATIONS.values()) {
            application.resetUpTime();
        }
    }


    public static final class IDNotFoundException extends Exception {
        public IDNotFoundException(String error) {
            super(error);
        }
    }
}
