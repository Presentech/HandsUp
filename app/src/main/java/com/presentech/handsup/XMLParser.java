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
    Slide slide;

    String curText = "";

    public PresentationFile getPresentation (InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("Presentation")) {
                            presentationFile = new PresentationFile();
                            presentationFile.slides = new ArrayList<Slide>();
                        }
                        if (tagname.equalsIgnoreCase("slide")) {
                            slide = new Slide();
                            String attr;
                            int val;
                            for (int i=0; i < parser.getAttributeCount(); i++) {
                                attr = parser.getAttributeName(i);
                                val = Integer.parseInt(parser.getAttributeValue(i));
                                if (attr.equals("slideID")) {
                                    slide.setSlideID(val);
                                }
                                if (attr.equals("nextSlide")) {
                                    slide.setNextSlide(val);
                                }
                                if (attr.equals("duration")) {
                                    slide.setDuration(val);
                                }
                            }
                        }
                        if (tagname.equals("text")) {
                            Text text = new Text();
                            slide.setText(text);
                        }
                        if (tagname.equals("shape")) {
                            Shape shape = new Shape();
                            slide.setShape(shape);
                        }
                        if (tagname.equals("polygon")) {
                            Polygon polygon = new Polygon();
                            slide.setPolygon(polygon);
                        }
                        if (tagname.equals("image")) {
                            Image image = new Image();
                            slide.setImage(image);
                        }
                        if (tagname.equals("video")) {
                            Video video = new Video();
                            slide.setVideo(video);
                        }
                        if (tagname.equals("audio")) {
                            Audio audio = new Audio();
                            slide.setAudio(audio);
                        }
                        if (tagname.equals("interactable")) {
                            Interactable interactable = new Interactable();
                            slide.setInteractable(interactable);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        curText = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equals("Title")) {
                            presentationFile.setTitle(curText);
                        }
                        if (tagname.equals("Author")) {
                            presentationFile.setAuthor(curText);
                        }
                        if (tagname.equals("Version")) {
                            presentationFile.setVersion(curText);
                        }
                        if (tagname.equals("Comment")) {
                            presentationFile.setComment(curText);
                        }
                        if (tagname.equalsIgnoreCase("backgroundColour")) {
                            presentationFile.setDefBackgroundColour(curText);
                        }
                        if (tagname.equalsIgnoreCase("font")) {
                            presentationFile.setDefFont(curText);
                        }
                        if (tagname.equalsIgnoreCase("fontsize")) {
                            presentationFile.setDefFontSize(Integer.parseInt(curText));
                        }
                        if (tagname.equalsIgnoreCase("fontColour")) {
                            presentationFile.setDefFontColour(curText);
                        }
                        if (tagname.equalsIgnoreCase("lineColour")) {
                            presentationFile.setDefLineColour(curText);
                        }
                        if (tagname.equalsIgnoreCase("fillColour")) {
                            presentationFile.setDefFillColour(curText);
                        }
                        if (tagname.equalsIgnoreCase("slide")) {
                            presentationFile.slides.add(slide);
                        }

                        break;
                    default:
                        break;
                }
                eventType = parser.next();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return presentationFile;
    }
}
