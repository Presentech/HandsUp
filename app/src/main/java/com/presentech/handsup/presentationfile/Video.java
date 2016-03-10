package com.presentech.handsup.presentationfile;

/**
 * Created by Alex  Butcher on 25/02/2016.
 */
public class Video {
    String sourceFile;
    int startTime;
    int duration;
    float xStart;
    float yStart;
    boolean loop;

    public Video(String sourceFile, int startTime, int duration, float xStart, float yStart, boolean loop) {
        this.sourceFile = sourceFile;
        this.startTime = startTime;
        this.duration = duration;
        this.xStart = xStart;
        this.yStart = yStart;
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

    public float getxStart() {
        return xStart;
    }

    public float getyStart() {
        return yStart;
    }

    public boolean isLoop() {
        return loop;
    }
}
