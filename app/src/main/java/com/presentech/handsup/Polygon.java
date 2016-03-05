package com.presentech.handsup;

/**
 * Created by Alex on 25/02/2016.
 */
public class Polygon {
    int startTime;
    int duration;
    String lineColour;
    String fillColour;
    Shading shading;
    String sourceFile;

    public Polygon(int startTime, int duration, String lineColour, String fillColour,
                   Shading shading, String sourceFile) {
        this.startTime = startTime;
        this.duration = duration;
        this.lineColour = lineColour;
        this.fillColour = fillColour;
        this.shading = shading;
        this.sourceFile = sourceFile;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public String getLineColour() {
        return lineColour;
    }

    public String getFillColour() {
        return fillColour;
    }

    public Shading getShading() {
        return shading;
    }

    public String getSourceFile() {
        return sourceFile;
    }
}
