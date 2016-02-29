package com.presentech.handsup;


import java.util.List;

/**
 * Created by Alex on 22/02/2016.
 */
public class PresentationFile {
    /* Document Info */
    String title;
    String author;
    String version;
    String Comment;

    /* Defaults */
    String defBackgroundColour;
    String defFont;
    int defFontSize;
    String defFontColour;
    String defLineColour;
    String defFillColour;


    List<Slide> slides;

    public List<Slide> getSlides() {
        return slides;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDefBackgroundColour() {
        return defBackgroundColour;
    }

    public void setDefBackgroundColour(String defBackgroundColour) {
        this.defBackgroundColour = defBackgroundColour;
    }

    public String getDefFont() {
        return defFont;
    }

    public void setDefFont(String defFont) {
        this.defFont = defFont;
    }

    public int getDefFontSize() {
        return defFontSize;
    }

    public void setDefFontSize(int defFontSize) {
        this.defFontSize = defFontSize;
    }

    public String getDefFontColour() {
        return defFontColour;
    }

    public void setDefFontColour(String defFontColour) {
        this.defFontColour = defFontColour;
    }

    public String getDefLineColour() {
        return defLineColour;
    }

    public void setDefLineColour(String defLineColour) {
        this.defLineColour = defLineColour;
    }

    public String getDefFillColour() {
        return defFillColour;
    }

    public void setDefFillColour(String defFillColour) {
        this.defFillColour = defFillColour;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
