package com.presentech.handsup;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLParser
{
    String ns = null;

    public PresentationFile getPresentation (InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            parser.nextTag();

            return readPresentation(parser);
        } finally {
            in.close();
        }
        //return presentationFile;
    }

    private PresentationFile readPresentation (XmlPullParser parser) throws IOException, XmlPullParserException {
        PresentationFile presentationFile;
        DocumentInfo documentInfo = null;
        Defaults defaults = null;
        List<Slide> slides = new ArrayList<Slide>();

        parser.require(XmlPullParser.START_TAG, ns, "presentation");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("documentInfo")) {
                documentInfo = readDocumentInfo(parser);
            } else if (name.equals("defaults")) {
                defaults = readDefaults(parser);
            } else if (name.equals("slide")) {
                slides.add(readSlide(parser));
            } else {
                skip(parser);
            }
        }
        return new PresentationFile(documentInfo, defaults, slides);
    }

    private DocumentInfo readDocumentInfo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "documentInfo");

        String title = null;
        String author = null;
        String version = null;
        String comment = null;

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Title")) {
                title = readContentText(parser);
            } else if (name.equals("Author")) {
                author = readContentText(parser);
            } else if (name.equals("Version")) {
                version = readContentText(parser);
            } else if (name.equals("Comment")) {
                comment = readContentText(parser);
            } else {
                skip(parser);
            }
        }
        return new DocumentInfo(title, author, version, comment);
    }

    private Defaults readDefaults(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "defaults");

        String backgroundColour = null;
        String font =  null;
        int fontSize = 0;
        String fontColour = null;
        String lineColour = null;
        String fillColour = null;

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equalsIgnoreCase("backgroundColour")) {
               backgroundColour = readContentText(parser);
            } else if (name.equalsIgnoreCase("font")) {
                font = readContentText(parser);
            } else if (name.equalsIgnoreCase("fontsize")) {
                fontSize = Integer.parseInt(readContentText(parser));
            } else if (name.equalsIgnoreCase("fontColour")) {
                fontColour = readContentText(parser);
            } else if (name.equalsIgnoreCase("lineColour")) {
               lineColour = readContentText(parser);
            } else if (name.equalsIgnoreCase("fillColour")) {
                fillColour = readContentText(parser);
            } else {
                skip(parser);
            }
        }
        return new Defaults(backgroundColour, font, fontSize, fontColour, lineColour, fillColour);

    }

    private Slide readSlide(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "slide");

        int slideID = Slide.NULL_INT_ATTR;
        int nextSlide = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;

        List<Text> text =  new ArrayList<Text>();
        List<Shape> shape = new ArrayList<Shape>();
        List<Polygon> polygon = new ArrayList<Polygon>();
        List<Image> image = new ArrayList<Image>();
        List<Video> video = new ArrayList<Video>();
        List<Audio> audio = new ArrayList<Audio>();
        List<Interactable> interactable = new ArrayList<Interactable>();

        String attr;
        int val;
        for (int i=0; i < parser.getAttributeCount(); i++) {
            attr = parser.getAttributeName(i);
            val = Integer.parseInt(parser.getAttributeValue(i));
            if (attr.equals("slideID")) {
                slideID= val;
            } else if (attr.equals("nextSlide")) {
                nextSlide = val;
            } else if (attr.equals("duration")) {
                duration = val;
            }
        }

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("text")) {
                text.add(readText(parser));
            } else
                skip(parser);
        }
        return new Slide(slideID, nextSlide, duration, text, shape, polygon, image, video, audio,
                interactable);
    }

    private Text readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "text");

        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;
        String font = null;
        int fontSize = Slide.NULL_INT_ATTR;
        String fontColour = null;
        String sourceFile = null;

        String text = "";

        String attr;
        String val;
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i=0; i < parser.getAttributeCount(); i++) {
            attr = parser.getAttributeName(i);
            val = parser.getAttributeValue(i);
            if (attr.equals("starttime")) {
                startTime = Integer.parseInt(val);
            } else if (attr.equals("duration")) {
                duration = Integer.parseInt(val);
            } else if (attr.equals("xstart")) {
                xStart = Float.parseFloat(val);
            } else if (attr.equals("ystart")) {
                yStart = Float.parseFloat(val);
            } else if (attr.equals("font")) {
                font = val;
            } else if (attr.equals("fontsize")) {
                fontSize = Integer.parseInt(val);
            } else if (attr.equals("fontcolour")) {
                fontColour = val;
            } else if (attr.equals("sourceFile")) {
                sourceFile = val;
            }
        }

        text = readInnerText(parser);

        return new Text(startTime, duration, xStart, yStart, font, fontSize, fontColour, sourceFile, text);
    }

    private String readInnerText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = "";
        int depth = 1;

        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    String temp = parser.getName();
                    if (parser.getName().equals("b")) {
                        text = text + "</b>";
                    }
                    else if (parser.getName().equals("i")) {
                        text = text + "</i>";
                    }
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    String temp1 = parser.getName();
                    if (parser.getName().equals("b")) {
                        text = text + "<b>";
                    }
                    else if (parser.getName().equals("i")) {
                        text = text + "<i>";
                    }
                    break;
                case XmlPullParser.TEXT:
                    text = text + parser.getText();
                    break;
                default:
                    break;
            }
        }
        return text;

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
