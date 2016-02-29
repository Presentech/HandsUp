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
    List<MediaObjects> mediaObjects;
    Slide slide;

    String tagname;
    int eventType;
    String curText = "";

    public PresentationFile getPresentation (InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("Presentation")) {
                            presentationFile = new PresentationFile();
                            slides = new ArrayList<Slide>();
                        }
                        if (tagname.equalsIgnoreCase("slide")) {
                            readSlide(parser);
                        }
                        if (tagname.equals("documentInfo")) {
                            readDocumentInfo(parser);
                        }
                        if (tagname.equals("defaults")) {
                            readDefaults(parser);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        curText = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("slide")) {
                            slides.add(slide);
                            slide.setMediaObjects(mediaObjects);
                        }
                        if (tagname.equalsIgnoreCase("presentation")) {
                            presentationFile.setSlides(slides);
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

    private void readDefaults(XmlPullParser parser) throws IOException, XmlPullParserException {
        eventType = parser.nextTag();
        tagname = parser.getName();
        boolean defaultsParsed = false;

        while (defaultsParsed == false) {
            tagname = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    break;
                case XmlPullParser.TEXT:
                    curText = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
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
                    if (tagname.equals("defaults")) {
                        defaultsParsed = true;
                    }

                    break;
                default:
                    break;
            }
            eventType = parser.next();

        }
    }

    private void readDocumentInfo(XmlPullParser parser) throws IOException, XmlPullParserException {
        eventType = parser.nextTag();
        tagname = parser.getName();
        boolean docInfoParsed = false;

        while (docInfoParsed == false) {
            tagname = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
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
                    if (tagname.equals("documentInfo")) {
                        docInfoParsed = true;
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();

        }

    }

    private void readSlide(XmlPullParser parser) {
        slide = new Slide();
        mediaObjects = new ArrayList<MediaObjects>();
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
}
