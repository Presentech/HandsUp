package com.presentech.handsup.presentationfile;

/**
 * Created by Alex Butcher on 03/03/2016.
 */
public class Shading {
    float x1;
    float x2;
    float y1;
    float y2;
    String colour1;
    String colour2;

    public Shading(float x1, float x2, float y1, float y2, String colour1, String colour2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.colour1 = colour1;
        this.colour2 = colour2;
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }

    public String getColour1() {
        return colour1;
    }

    public String getColour2() {
        return colour2;
    }
}
