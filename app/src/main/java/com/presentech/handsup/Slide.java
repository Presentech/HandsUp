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


    List<Text>  text;
    List<Shape> shape;
    List<Polygon> polygon;
    List<Image> image;
    List<Video> video;
    List<Audio> audio;
    List<Interactable> interactable;



    List<MediaObject> mediaObjects;

    public List<Text> getText() {
        return text;
    }

    public void setText(List<Text> text) {
        this.text = text;
    }

    public List<Shape> getShape() {
        return shape;
    }

    public void setShape(List<Shape> shape) {
        this.shape = shape;
    }

    public List<Polygon> getPolygon() {
        return polygon;
    }

    public void setPolygon(List<Polygon> polygon) {
        this.polygon = polygon;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(List<Audio> audio) {
        this.audio = audio;
    }

    public List<Interactable> getInteractable() {
        return interactable;
    }

    public void setInteractable(List<Interactable> interactable) {
        this.interactable = interactable;
    }

    public List<MediaObject> getMediaObjects() {
        return mediaObjects;
    }

    public void setMediaObjects(List<MediaObject> mediaObjects) {
        this.mediaObjects = mediaObjects;
    }


}
