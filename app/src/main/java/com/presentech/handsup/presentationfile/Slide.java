package com.presentech.handsup.presentationfile;


import java.util.List;

/**
 * Created by Alex Butcher on 22/02/2016.
 */
public class Slide {

    public final static int NULL_INT_ATTR = -2;
    public final static float NULL_FLOAT_ATTR = 2;

    int slideID;
    int nextSlide;
    int duration;

    List<Text>  text;
    List<Shape> shape;
    List<Polygon> polygon;
    List<Image> image;
    List<Video> video;
    List<Audio> audio;
    List<Interactable> interactable;
    List<Question> question;

    public Slide(int slideID, int nextSlide, int duration, List<Text> text, List<Shape> shape,
                 List<Polygon> polygon, List<Image> image, List<Video> video, List<Audio> audio,
                 List<Interactable> interactable, List<Question> question) {
        this.slideID = slideID;
        this.nextSlide = nextSlide;
        this.duration = duration;
        this.text = text;
        this.shape = shape;
        this.polygon = polygon;
        this.image = image;
        this.video = video;
        this.audio = audio;
        this.interactable = interactable;
        this.question = question;
    }

    public int getSlideID() {
        return slideID;
    }

    public int getNextSlide() {
        return nextSlide;
    }

    public int getDuration() {
        return duration;
    }

    public List<Text> getText() {
        return text;
    }

    public List<Shape> getShape() {
        return shape;
    }

    public List<Polygon> getPolygon() {
        return polygon;
    }

    public List<Image> getImage() {
        return image;
    }

    public List<Video> getVideo() {
        return video;
    }

    public List<Audio> getAudio() {
        return audio;
    }

    public List<Interactable> getInteractable() {
        return interactable;
    }

    public List<Question> getQuestion() {
        return question;
    }
}
