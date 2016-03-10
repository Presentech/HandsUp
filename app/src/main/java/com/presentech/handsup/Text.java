package com.presentech.handsup;

/**
 * Created by Alex on 25/02/2016.
 */
public class Text {
    int startTime;
    int duration;
    float xStart;
    float yStart;
    String font;
    int fontSize;
    String fontColour;
    String sourceFile;

    String text;

    public Text(int startTime, int duration, float xStart, float yStart, String font, int fontSize, String fontColour, String sourceFile, String text) {
        this.startTime = startTime;
        this.duration = duration;
        this.xStart = xStart;
        this.yStart = yStart;
        this.font = font;
        this.fontSize = fontSize;
        this.fontColour = fontColour;
        this.sourceFile = sourceFile;
        this.text = text;
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

    public String getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontColour() {
        return fontColour;
    }

    public String getText() {
        return text;
    }

    public String getSourceFile() {
        return sourceFile;
    }
}
