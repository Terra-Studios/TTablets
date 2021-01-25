package dev.sebastianb.ttablets.helper;

import java.util.TimerTask;


public class RunningTime extends TimerTask {

    private long number;

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