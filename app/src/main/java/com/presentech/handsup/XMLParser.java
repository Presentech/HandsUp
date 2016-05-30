/**
 * Created by Alex Butcher on 22/02/2016.
 */

package com.presentech.handsup;

import com.presentech.handsup.presentationfile.Audio;
import com.presentech.handsup.presentationfile.Defaults;
import com.presentech.handsup.presentationfile.DocumentInfo;
import com.presentech.handsup.presentationfile.Image;
import com.presentech.handsup.presentationfile.Interactable;
import com.presentech.handsup.presentationfile.Polygon;
import com.presentech.handsup.presentationfile.PresentationFile;
import com.presentech.handsup.presentationfile.Question;
import com.presentech.handsup.presentationfile.Shading;
import com.presentech.handsup.presentationfile.Shape;
import com.presentech.handsup.presentationfile.Slide;
import com.presentech.handsup.presentationfile.Text;
import com.presentech.handsup.presentationfile.Video;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLParser
{
    // Not using namespaces
    String ns = null;

    public PresentationFile getPresentation (InputStream in) throws XmlPullParserException, IOException {
        try {
            // Create a new factory and parser object
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            // Get first tag in XML
            parser.nextTag();

            // Return the parsed presentation file
            return readPresentation(parser);
        } finally {
            in.close();
        }
    }

    private PresentationFile readPresentation (XmlPullParser parser) throws IOException, XmlPullParserException {
        //Instantiate the presentation file contents
        PresentationFile presentationFile;
        DocumentInfo documentInfo = null;
        Defaults defaults = null;
        List<Slide> slides = new ArrayList<Slide>();

        // Ensure that the first tag is "presentation" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "presentation");

        while (parser.next() != XmlPullParser.END_TAG) {
            //Only interested in Start Tags
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            //Call the appropriate method depending on the current start tag
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

        //Return a presentation file with its parsed contents
        return new PresentationFile(documentInfo, defaults, slides);
    }

    private DocumentInfo readDocumentInfo(XmlPullParser parser) throws IOException, XmlPullParserException {
        // Ensure current tag is "documentInfo" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "documentInfo");

        //Instantiate the document info contents
        String title = null;
        String author = null;
        String version = null;
        String comment = null;

        while(parser.next() != XmlPullParser.END_TAG) {
            //Only interested in start tags
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            //Set the contents of documentInfo based on the current xml tag
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
        //Return a new document info object with its parsed contents
        return new DocumentInfo(title, author, version, comment);
    }

    private Defaults readDefaults(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "defaults" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "defaults");

        //Instantiate the Defaults object contents
        String backgroundColour = null;
        String font =  null;
        int fontSize = 0;
        String fontColour = null;
        String lineColour = null;
        String fillColour = null;

        while(parser.next() != XmlPullParser.END_TAG) {
            //Only interested in start tags
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            // Set the contents of the Defaults objects to the contents of the current tag
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
        //ensure current tag is "slide" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "slide");

        //Instantiate the slide object contents
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
        List<Question> question = new ArrayList<Question>();

        // Loop through each attribute and the current attribute contents to the Slide object
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

        // Loop through elements in the between the "slide" tags and the contents of them to the
        // slide object
        while(parser.next() != XmlPullParser.END_TAG) {
            // Only interested in start tags
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
            } else if (name.equals("interactable")) {
                interactable.add(readInteractable(parser));
            }else if (name.equals("question")) {
                question.add(readQuestion(parser));
            } else
                skip(parser);
        }
        //Return a new slide object with the parsed slide contents
        return new Slide(slideID, nextSlide, duration, text, shape, polygon, image, video, audio,
                interactable, question);
    }

    private Question readQuestion(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "text" otherwise throw and exception
        parser.require(XmlPullParser.START_TAG, ns, "question");
        String question = "";

        //Retreive text string from the XML
        question = readInnerText(parser);

        //Return new text object with contents parsed from xml.
        return new Question(question);
    }


    private Interactable readInteractable(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag in "interactable" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "interactable");

        //Instantiate Interactable object contents
        int targetSlideID = Slide.NULL_INT_ATTR;
        Text text = null;
        Shape shape = null;
        Polygon polygon = null;
        Image image = null;
        Video video = null;

        //Get the target slide id from the sole attribute
        targetSlideID = Integer.parseInt(parser.getAttributeValue(null, "targetSlide"));

        //Retrieve the correct element between the "interactable" tags and add it to the
        // Interactable object
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
        //Return new Interactable object with parsed contents
        return new Interactable(targetSlideID, text, shape, polygon, image, video);
    }

    private Audio readAudio(XmlPullParser parser) throws IOException, XmlPullParserException {
        // Esnure current tag is audio, otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "audio");

        // Instantiate Audio object contents
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        String sourceFile = null;
        boolean loop = false;

        // Loop through each attribute in the XML and add it to the Audio object
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
        //Return new audio element with contents parsed form XML.
        return new Audio(sourceFile, startTime, duration, loop);
    }

    private Video readVideo(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is video, otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "video");

        //Instantiate contents of Video Object
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;
        String sourceFile = null;
        boolean loop = false;

        //Loop through each attribute in the XML and add it to the Video object
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

        // Return new Video object with contents parsed form XML
        return new Video(sourceFile, startTime, duration, xStart, yStart, loop);

    }

    private Image readImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "image" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "image");

        //Instantiate image object contents
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;
        float width = Slide.NULL_FLOAT_ATTR;
        float height = Slide.NULL_FLOAT_ATTR;
        String sourceFile = null;

        //Loop through each attribute in xml and add it to Image object
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
        //Return new image object with contents parsed from xml.
        return new Image(sourceFile, startTime, duration, xStart, yStart, width, height);
    }

    private Polygon readPolygon(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "polygon" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "polygon");

        // Instantiate Polygon object contents
        int startTime = Slide.NULL_INT_ATTR;
        int duration =  Slide.NULL_INT_ATTR;
        String lineColour = null;
        String fillColour = null;
        Shading shading = null;
        String sourceFile = null;

        //Loop through each attrubute in xml and add it to Polygon contents
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

        //Look to see if there is a shading element between the polygon tags
        //If there is, parse it
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

        // Return a new polygon object with contents parsed from XML
        return new Polygon(startTime, duration, lineColour ,fillColour ,shading, sourceFile);
    }

    private Shape readShape(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "shape" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "shape");

        //Instantiate Shape object contents
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

        //Loop through each attribute in the xml and add it to the shape object contents
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

        //Look to see if there is a shading element between the shape tags
        //If there is, parse it
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

        //Return enw shape object with contents parsed from the xml.
        return new Shape(startTime, duration, xStart, yStart, type, width, height, lineColour,
                fillColour, shading);
    }

    private Shading readShading(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "shading" otherwise throw an exception
        parser.require(XmlPullParser.START_TAG, ns, "shading");

        //Instantiate shading object contents
        float x1 = Slide.NULL_FLOAT_ATTR;
        float x2 = Slide.NULL_FLOAT_ATTR;
        float y1 = Slide.NULL_FLOAT_ATTR;
        float y2 = Slide.NULL_FLOAT_ATTR;
        String colour1 =  null;
        String colour2 = null;

        //Loop through each attribute and add it to the shaading object contents
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

        //Return new shading object with contents parsed from xml.
        return new Shading(x1, x2, y1, y2, colour1, colour2);
    }

    private Text readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        //Ensure current tag is "text" otherwise throw and exception
        parser.require(XmlPullParser.START_TAG, ns, "text");

        //Instantiate text object contents
        int startTime = Slide.NULL_INT_ATTR;
        int duration = Slide.NULL_INT_ATTR;
        float xStart = Slide.NULL_FLOAT_ATTR;
        float yStart = Slide.NULL_FLOAT_ATTR;
        String font = null;
        int fontSize = Slide.NULL_INT_ATTR;
        String fontColour = null;
        String sourceFile = null;

        String text = "";

        //Loop through each attribute and add it to text object contents
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

        //If there is no source file retreive text string from the XML
        if (sourceFile == null) {
            text = readInnerText(parser);
        }

        //Return new text object with contents parsed from xml.
        return new Text(startTime, duration, xStart, yStart, font, fontSize, fontColour, sourceFile, text);
    }

    private String readInnerText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = "";

        //Used to track if we are inside a bold or italic element
        int depth = 1;

        //While we are not at an the end tag for the text element
        while (depth != 0) {
            switch (parser.next()) {
                // If end tag encountered reduce depth by 1 and add a closing bold or italic tag
                // if a end bold or italic tag was encountered
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
                // If start tag encountered increase depth by 1 and add an opening bold or italic
                // tag if an opening bold or italic tag was encountered
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
                //If XML text was encountered parse the text.
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

        //Parse the contents of text within XML
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Method which skips past an element within the XML in order to ignore it while parsing.
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
