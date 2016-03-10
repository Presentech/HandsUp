package com.presentech.handsup.presentationfile;

/**
 * Created by Alex Butcher on 25/02/2016.
 */
public class Audio {
    String sourceFile;
    int startTime;
    int duration;
    boolean loop;

    public Audio(String sourceFile, int startTime, int duration, boolean loop) {
        this.sourceFile = sourceFile;
        this.startTime = startTime;
        this.duration = duration;
        this.loop = loop;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isLoop() {
        return loop;
    }
}
