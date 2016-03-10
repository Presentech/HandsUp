package com.presentech.handsup;

/**
 * Created by Alex on 25/02/2016.
 */
public class Interactable {
    int targetSlideID;

    Text text;
    Shape shape;
    Polygon polygon;
    Image image;
    Video video;

    public Interactable(int targetSlideID, Text text, Shape shape, Polygon polygon, Image image, Video video) {
        this.targetSlideID = targetSlideID;
        this.text = text;
        this.shape = shape;
        this.polygon = polygon;
        this.image = image;
        this.video = video;
    }

    public int getTargetSlideID() {
        return targetSlideID;
    }

    public Text getText() {
        return text;
    }

    public Shape getShape() {
        return shape;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Image getImage() {
        return image;
    }

    public Video getVideo() {
        return video;
    }
}
