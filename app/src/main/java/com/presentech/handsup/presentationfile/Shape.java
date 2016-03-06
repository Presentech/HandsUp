package com.presentech.handsup.presentationfile;

/**
 * Created by Alex Butcher on 25/02/2016.
 */
public class Shape {
    int startTime;
    int duration;
    float xStart;
    float yStart;

    String type;

    float width;
    float height;
    String lineColour;
    String fillColour;

    Shading shading;

    public Shape(int startTime, int duration, float xStart, float yStart, String type, float width,
                 float height, String lineColour, String fillColour, Shading shading) {
        this.startTime = startTime;
        this.duration = duration;
        this.xStart = xStart;
        this.yStart = yStart;
        this.type = type;
        this.width = width;
        this.height = height;
        this.lineColour = lineColour;
        this.fillColour = fillColour;
        this.shading = shading;
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

    public String getType() {
        return type;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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
}
