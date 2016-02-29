package com.presentech.handsup;


import java.util.List;

/**
 * Created by Alex on 22/02/2016.
 */
public class Slide {

    final static int NULL_ATTR = -2;

    int slideID;
    int nextSlide = NULL_ATTR;
    int duration = NULL_ATTR;

    Text text;
    Shape shape;
    Polygon polygon;
    Image image;
    Video video;
    Audio audio;
    Interactable interactable;



    List<MediaObjects> mediaObjects;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Interactable getInteractable() {
        return interactable;
    }

    public void setInteractable(Interactable interactable) {
        this.interactable = interactable;
    }

    public int getSlideID() {
        return slideID;
    }

    public void setSlideID(int slideID) {
        this.slideID = slideID;
    }

    public int getNextSlide() {
        return nextSlide;
    }

    public void setNextSlide(int nextSlide) {
        this.nextSlide = nextSlide;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public List<MediaObjects> getMediaObjects() {
        return mediaObjects;
    }

    public void setMediaObjects(List<MediaObjects> mediaObjects) {
        this.mediaObjects = mediaObjects;
    }


}
