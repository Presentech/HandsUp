package com.presentech.handsup.presentationfile;

/**
 * Created by Alex Butcher on 25/02/2016.
 */
public class Image {
    String sourceFile;
    int startTime;
    int duration;
    float xStart;
    float yStart;
    float width;
    float height;

    public Image(String sourceFile, int startTime, int duration, float xStart, float yStart, float width, float height) {
        this.sourceFile = sourceFile;
        this.startTime = startTime;
        this.duration = duration;
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        this.height = height;
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
