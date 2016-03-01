package com.presentech.handsup;

import android.util.Log;
import android.util.Xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLParser
{
    PresentationFile presentationFile;
    List<Slide> slides;
    List<MediaObject> mediaObjects;
    Slide slide;
    Text text;

    String tagname;
    int eventType;
    String curText = "";
    String ns = null;

    public PresentationFile getPresentation (InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            parser.nextTag();

            readPresentation(parser);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return presentationFile;
    }

    private void readPresentation (XmlPullParser parser) throws IOException, XmlPullParserException {
        presentationFile = new PresentationFile();
        slides = new ArrayList<Slide>();

        parser.require(XmlPullParser.START_TAG, ns, "presentation");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("documentInfo")) {
                readDocumentInfo(parser);
            } else if (name.equals("defaults")) {
                readDefaults(parser);
            } else if (name.equals("slide")) {
                readSlide(parser);
            } else {
                skip(parser);
            }
        }
        presentationFile.setSlides(slides);
    }

    private void readDocumentInfo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "documentInfo");

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Title")) {
                presentationFile.setTitle(readContentText(parser));
            } else if (name.equals("Author")) {
                presentationFile.setAuthor(readContentText(parser));
            } else if (name.equals("Version")) {
                presentationFile.setVersion(readContentText(parser));
            } else if (name.equals("Comment")) {
                presentationFile.setComment(readContentText(parser));
            } else {
                skip(parser);
            }
        }
    }

    private void readDefaults(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "defaults");

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equalsIgnoreCase("backgroundColour")) {
                presentationFile.setDefBackgroundColour(readContentText(parser));
            } else if (name.equalsIgnoreCase("font")) {
                presentationFile.setDefFont(readContentText(parser));
            } else if (name.equalsIgnoreCase("fontsize")) {
                presentationFile.setDefFontSize(Integer.parseInt(readContentText(parser)));
            } else if (name.equalsIgnoreCase("fontColour")) {
                presentationFile.setDefFontColour(readContentText(parser));
            } else if (name.equalsIgnoreCase("lineColour")) {
                presentationFile.setDefLineColour(readContentText(parser));
            } else if (name.equalsIgnoreCase("fillColour")) {
                presentationFile.setDefFillColour(readContentText(parser));
            } else {
                skip(parser);
            }
        }

    }

    private void readSlide(XmlPullParser parser) throws IOException, XmlPullParserException {
        slide = new Slide();
        mediaObjects = new ArrayList<MediaObject>();

        String attr;

        Text text;
        Shape shape;
        Polygon polygon;
        Image image;
        Video video;
        Audio audio;
        Interactable interactable;

        parser.require(XmlPullParser.START_TAG, ns, "slide");

        int val;
        for (int i=0; i < parser.getAttributeCount(); i++) {
            attr = parser.getAttributeName(i);
            val = Integer.parseInt(parser.getAttributeValue(i));
            if (attr.equals("slideID")) {
                slide.setSlideID(val);
            } else if (attr.equals("nextSlide")) {
                slide.setNextSlide(val);
            } else if (attr.equals("duration")) {
                slide.setDuration(val);
            }
        }

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
//            if (name.equals("text")) {
//                text = new Text();
//                mediaObjects.add(text);
//                parser.nextTag();
//            } else if (name.equals("shape")) {
//                shape = new Shape();
//                mediaObjects.add(shape);
//                parser.nextTag();
//            } else if (name.equals("polygon")) {
//                polygon = new Polygon();
//                mediaObjects.add(polygon);
//                parser.nextTag();
//            } else if (name.equals("image")) {
//                image = new Image();
//                mediaObjects.add(image);
//                parser.nextTag();
//            } else if (name.equals("video")) {
//                video = new Video();
//                mediaObjects.add(video);
//                parser.nextTag();
//            } else if (name.equals("audio")) {
//                audio = new Audio();
//                mediaObjects.add(audio);
//                parser.nextTag();
//            } else if (name.equals("interactable")) {
//                interactable = new Interactable();
//                readInteractable(parser);
//                mediaObjects.add(interactable);
//                parser.nextTag();
//
//            } else {
                skip(parser);
        }
        slide.setMediaObjects(mediaObjects);
        slides.add(slide);
    }

    private void readInteractable(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "interactable");

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            skip(parser);
        }

    }

    private String readContentText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }

    }
}
