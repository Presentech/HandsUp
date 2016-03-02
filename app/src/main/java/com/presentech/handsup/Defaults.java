package com.presentech.handsup;

/**
 * Created by Alex on 02/03/2016.
 */
public class Defaults {

    String backgroundColour;
    String font;
    int fontSize;
    String fontColour;
    String lineColour;
    String fillColour;

    public Defaults(String backgroundColour, String font, int fontSize, String fontColour, String lineColour, String fillColour) {
        this.backgroundColour = backgroundColour;
        this.font = font;
        this.fontSize = fontSize;
        this.fontColour = fontColour;
        this.lineColour = lineColour;
        this.fillColour = fillColour;
    }

    public String getBackgroundColour() {
        return backgroundColour;
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

    public String getLineColour() {
        return lineColour;
    }

    public String getFillColour() {
        return fillColour;
    }



}
