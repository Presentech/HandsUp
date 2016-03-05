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
            } else if (name.equals("shape")){
                shape.add(readShape(parser));
            } else if (name.equals("polygon")){
                polygon.add(readPolygon(parser));
            } else if (name.equals("image")){
                image.add(readImage(parser));
            } else if (name.equals("video")){
                video.add(readVideo(parser));
            } else if (name.equals("audio")){
                audio.add(readAudio(parser));
            } else if (name.equals("interactable")){
                interactable.add(readInteractable(parser));
            } else
                skip(parser);
        }
        return new Slide(slideID, nextSlide, duration, text, shape, polygon, image, video, audio,
                interactable);
    }

    private Interactable readInteractable(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "interactable");
        int targetSlideID = Slide.NULL_INT_ATTR;
        Text text = null;
        Shape shape = null;
        Polygon polygon = null;
        Image image = null;
        Video video = null;


        targetSlideID = Integer.parseInt(parser.getAttributeValue(null, "targetSlide"));

        while((parser.next() != XmlPullParser.END_TAG)) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("text")) {
                text = readText(parser);
            } else if (name.equals("shape")){
                shape = readShape(parser);
            } else if (name.equals("polygon")){
                polygon= readPolygon(parser);
            } else if (name.equals("image")){
                image = readImage(parser);
            } else if (name.equals("video")) {
                video = readVideo(parser);
            } else
                skip(parser);
        }
        return new Interactable(targetSlideID, text, shape, polygon, image, video);
    }

    private Audio readAudio(XmlPullParser parser) throws IOException, XmlPullParserException {
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        String sourceFile = null;
        boolean loop = false;

        String attr;
        String val;
        for (int i=0; i < parser.getAttributeCount(); i++) {
            attr = parser.getAttributeName(i);
            val = parser.getAttributeValue(i);
            if (attr.equals("starttime")) {
                startTime = Integer.parseInt(val);
            } else if (attr.equals("duration")) {
                duration = Integer.parseInt(val);
            } else if (attr.equals("sourceFile")) {
                sourceFile = val;
            } else if (attr.equals("loop")) {
                loop = Boolean.parseBoolean(val);
            }
        }

        parser.nextTag();
        return new Audio(sourceFile, startTime, duration, loop);
    }

    private Video readVideo(XmlPullParser parser) throws IOException, XmlPullParserException {
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;
        String sourceFile = null;
        boolean loop = false;

        String attr;
        String val;
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
            } else if (attr.equals("sourceFile")) {
                sourceFile = val;
            } else if (attr.equals("loop")) {
                loop = Boolean.parseBoolean(val);
            }
        }

        parser.nextTag();
        return new Video(sourceFile, startTime, duration, xStart, yStart, loop);

    }

    private Image readImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;
        float width = Slide.NULL_FLOAT_ATTR;
        float height = Slide.NULL_FLOAT_ATTR;
        String sourceFile = null;

        String attr;
        String val;
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
            } else if (attr.equals("width")) {
                width = Float.parseFloat(val);
            } else if (attr.equals("height")) {
                height = Float.parseFloat(val);
            } else if (attr.equals("sourceFile")) {
                sourceFile = val;
            }
        }

        parser.nextTag();
        return new Image(sourceFile, startTime, duration, xStart, yStart, width, height);
    }

    private Polygon readPolygon(XmlPullParser parser) throws IOException, XmlPullParserException {
        int startTime = Slide.NULL_INT_ATTR;
        int duration =  Slide.NULL_INT_ATTR;
        String lineColour = null;
        String fillColour = null;
        Shading shading = null;
        String sourceFile = null;

        String attr;
        String val;
        for (int i=0; i < parser.getAttributeCount(); i++) {
            attr = parser.getAttributeName(i);
            val = parser.getAttributeValue(i);
            if (attr.equals("starttime")) {
                startTime = Integer.parseInt(val);
            } else if (attr.equals("duration")) {
                duration = Integer.parseInt(val);
            } else if (attr.equals("lineColour")) {
                lineColour = val;
            } else if (attr.equals("fillColour")) {
                fillColour = val;
            } else if (attr.equals("sourceFile")) {
                sourceFile = val;
            }
        }

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("shading")) {
                shading = readShading(parser);
            } else
                skip(parser);
        }

        return new Polygon(startTime, duration, lineColour ,fillColour ,shading, sourceFile);
    }

    private Shape readShape(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "shape");
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;

        String type = null;

        float width = Slide.NULL_FLOAT_ATTR;
        float height = Slide.NULL_FLOAT_ATTR;
        String lineColour =  null;
        String fillColour = null;

        Shading shading = null;

        String attr;
        String val;
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
            } else if (attr.equals("type")) {
                type = val;
            } else if (attr.equals("width")) {
                width = Float.parseFloat(val);
            } else if (attr.equals("height")) {
                height = Float.parseFloat(val);
            } else if (attr.equals("lineColour")) {
                lineColour = val;
            } else if (attr.equals("fillColour")) {
                fillColour = val;
            }
        }

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("shading")) {
                shading = readShading(parser);
            } else
                skip(parser);
        }

        return new Shape(startTime, duration, xStart, yStart, type, width, height, lineColour,
                fillColour, shading);
    }

    private Shading readShading(XmlPullParser parser) throws IOException, XmlPullParserException {
        float x1 = Slide.NULL_FLOAT_ATTR;
        float x2 = Slide.NULL_FLOAT_ATTR;
        float y1 = Slide.NULL_FLOAT_ATTR;
        float y2 = Slide.NULL_FLOAT_ATTR;
        String colour1 =  null;
        String colour2 = null;

        String attr;
        String val;
        for (int i=0; i < parser.getAttributeCount(); i++) {
            attr = parser.getAttributeName(i);
            val = parser.getAttributeValue(i);
            if (attr.equals("x1")) {
                x1 = Float.parseFloat(val);
            } else if (attr.equals("x2")) {
                x2 = Float.parseFloat(val);
            } else if (attr.equals("y1")) {
                y1 = Float.parseFloat(val);
            } else if (attr.equals("y2")) {
                y2 = Float.parseFloat(val);
            } else if (attr.equals("colour1")) {
                colour1 = val;
            } else if (attr.equals("colour2")) {
                colour2 = val;
            }
        }

        parser.nextTag();
        return new Shading(x1, x2, y1, y2, colour1, colour2);
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
