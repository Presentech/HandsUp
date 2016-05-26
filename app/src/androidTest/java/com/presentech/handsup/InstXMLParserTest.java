package com.presentech.handsup;

import android.support.test.runner.AndroidJUnit4;

import com.presentech.handsup.presentationfile.Audio;
import com.presentech.handsup.presentationfile.Defaults;
import com.presentech.handsup.presentationfile.DocumentInfo;
import com.presentech.handsup.presentationfile.Image;
import com.presentech.handsup.presentationfile.Interactable;
import com.presentech.handsup.presentationfile.Polygon;
import com.presentech.handsup.presentationfile.PresentationFile;
import com.presentech.handsup.presentationfile.Shading;
import com.presentech.handsup.presentationfile.Shape;
import com.presentech.handsup.presentationfile.Slide;
import com.presentech.handsup.presentationfile.Text;
import com.presentech.handsup.presentationfile.Video;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alex Butcher on 18/02/2016.
 */



@RunWith(AndroidJUnit4.class)
public class InstXMLParserTest {
    private XMLParser parser;
    private PresentationFile presentationFile;
    private DocumentInfo documentInfo;


    @Before
    //Setup method Instantiates a new parser and retrieves a PresentationFile from it.
    //It provides the parser with a test xml file from a resources folder
    public void setUp() throws IOException, XmlPullParserException {
        parser = new XMLParser();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.xml");
        presentationFile = parser.getPresentation(in);
    }

    @Test
    //Check parser creates a Presentation File
    public void createPresentationObject() {
        assertNotNull(presentationFile);

    };

    @Test
    //Check presentation file contains document info
    public void documentInfoContainedinPresentation() {
        DocumentInfo documentInfo = presentationFile.getDocumentInfo();
        assertNotNull(documentInfo.getTitle());
        assertNotNull(documentInfo.getAuthor());
        assertNotNull(documentInfo.getVersion());
        assertNotNull(documentInfo.getComment());
    }

    @Test
    //Check presentation file contains the correct document info.
    public void presentationContainsCorrectData() {
        DocumentInfo documentInfo = presentationFile.getDocumentInfo();
        assertEquals("Example File", documentInfo.getTitle());
        assertEquals("Lexxy", documentInfo.getAuthor());
        assertEquals("1.0", documentInfo.getVersion());
        assertEquals("An example file used to demonstrate the appearance of this format", documentInfo.getComment());
    }

    @Test
    //Check presentation file contains correct Defaults
    public void presentationContainsCorrectDefaults() {
        Defaults defaults = presentationFile.getDefaults();
        assertEquals("FFFFFF", defaults.getBackgroundColour());
        assertEquals("Helvetica", defaults.getFont());
        assertEquals(12, defaults.getFontSize());
        assertEquals("000000", defaults.getFontColour());
        assertEquals("0000CC", defaults.getLineColour());
        assertEquals("2222AA", defaults.getFillColour());
    }

    @Test
    //Check presentation file contains a list of slides
    public void presentationContainsListOfSlides() {
        assertTrue(presentationFile.getSlides() instanceof List);
    }

    @Test
    //Check that the list of slides contains the correct number of slides
    public void presentationContainsCorrectNumberOfSlides(){
        assertEquals(6, presentationFile.getSlides().size());
    }

    @Test
    //Check that the slides contain the correct attributes
    public void slideContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(0);
        assertEquals(0, slide.getSlideID());
        assertEquals(5, slide.getDuration());
        assertEquals(2, slide.getNextSlide());

        slide = presentationFile.getSlides().get(1);
        assertEquals(1, slide.getSlideID());
        assertEquals(slide.NULL_INT_ATTR, slide.getDuration());
        assertEquals(slide.NULL_INT_ATTR, slide.getNextSlide());

        slide = presentationFile.getSlides().get(3);
        assertEquals(3, slide.getSlideID());
        assertEquals(8, slide.getDuration());
        assertEquals(-1, slide.getNextSlide());
    }

    @Test
    //Check that a slide contains the correct number of text elements
    public void slideContainsCorrectNumberOfTextElements() {
        Slide slide = presentationFile.getSlides().get(0);
        assertEquals(2, slide.getText().size());
    }

    @Test
    //Check that a text element contains the correct attributes
    public void textContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(0);
        Text text = slide.getText().get(0);

        assertEquals(0, text.getStartTime());
        assertEquals(-1, text.getDuration());
        assertEquals(0.1f, text.getxStart());
        assertEquals(1.0f, text.getyStart());
        assertEquals("Impact", text.getFont());
        assertEquals(30, text.getFontSize());
        assertNull(text.getFontColour());
        assertNull(text.getSourceFile());
    }

    @Test
    //Check that a text element contains the correct string contents
    public void textContainsCorrectStringContents() {
        Slide slide = presentationFile.getSlides().get(0);
        Text text = slide.getText().get(0);
        assertEquals("An <b>Exciting</b> piece of text.", text.getText());

        text = slide.getText().get(1);

        assertEquals("View the <i>video</i> ?", text.getText());
    }

    @Test
    //Check that a shape element contains the correct attributes
    public void shapeContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(5);
        Shape shape = slide.getShape().get(0);

        assertEquals(0, shape.getStartTime());
        assertEquals(-1, shape.getDuration());
        assertEquals(0.5f, shape.getxStart());
        assertEquals(0.44f, shape.getyStart());
        assertEquals("rectangle", shape.getType());
        assertEquals(0.2f, shape.getWidth());
        assertEquals(0.2f, shape.getHeight());
        assertEquals("FFFFFF", shape.getLineColour());
        assertEquals("1F6DBD", shape.getFillColour());
    }

    @Test
    //Check that a shape element with shading contains the correct shading attributes
    public void shapeContainsCorrectShading() {
        Slide slide = presentationFile.getSlides().get(5);
        Shape shape = slide.getShape().get(1);
        Shading shading = shape.getShading();

        assertEquals(0.60f, shading.getX1());
        assertEquals(0.80f, shading.getX2());
        assertEquals(0.90f, shading.getY1());
        assertEquals(0.65f, shading.getY2());
        assertEquals("00FF00", shading.getColour1());
        assertEquals("FF0000", shading.getColour2());
    }

    @Test
    //Check that a polygon element contains the correct attributes
    public void polygonContainsCorrectAttributes(){
        Slide slide = presentationFile.getSlides().get(5);
        Polygon polygon = slide.getPolygon().get(0);

        assertEquals(6, polygon.getStartTime());
        assertEquals(300, polygon.getDuration());
        assertEquals("FFFFFE", polygon.getLineColour());
        assertEquals("1C57A8", polygon.getFillColour());
        assertEquals("carrot.csv", polygon.getSourceFile());
    }

    @Test
    //Check that a polygon element with shading contains the correct shading attributes
    public void polygonContainsCorrectShading() {
        Slide slide = presentationFile.getSlides().get(5);
        Polygon polygon = slide.getPolygon().get(1);
        Shading shading = polygon.getShading();

        assertEquals(0.50f, shading.getX1());
        assertEquals(0.70f, shading.getX2());
        assertEquals(0.85f, shading.getY1());
        assertEquals(0.99f, shading.getY2());
        assertEquals("EEEFFF", shading.getColour1());
        assertEquals("ABCDEF", shading.getColour2());
    }

    @Test
    //Check that an image element contains the correct attributes
    public void imageContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(5);
        Image image = slide.getImage().get(0);

        assertEquals("images\\optionA.jpg", image.getSourceFile());
        assertEquals(0, image.getStartTime());
        assertEquals(-1, image.getDuration());
        assertEquals(0.2f, image.getxStart());
        assertEquals(0.3f, image.getyStart());
        assertEquals(0.6f, image.getWidth());
        assertEquals(0.6f, image.getHeight());
    }

    @Test
    //Check that a video element contains the correct attributes
    public void videoContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(1);
        Video video = slide.getVideo().get(0);

        assertEquals("video\\Film.mp4", video.getSourceFile());
        assertEquals(0, video.getStartTime());
        assertEquals(-1, video.getDuration());
        assertEquals(0.3f, video.getxStart());
        assertEquals(0.2f, video.getyStart());
        assertEquals(true, video.isLoop());
    }

    @Test
    //Check that an audio element contains the correct attributes
    public void audioContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(3);
        Audio audio = slide.getAudio().get(0);

        assertEquals("ping.wav", audio.getSourceFile());
        assertEquals(2, audio.getStartTime());
        assertEquals(6, audio.getDuration());
        assertEquals(false, audio.isLoop());
    }

    @Test
    //Check that an interactable element contains the correct media element
    public void correctMediaAssociatedWithInteractable() {
        Slide slide = presentationFile.getSlides().get(2);
        Interactable interactable = slide.getInteractable().get(1);

        assertEquals(4, interactable.getTargetSlideID());
        assertNotNull(interactable.getPolygon());
        assertNull(interactable.getText());
        assertNull(interactable.getShape());
        assertNull(interactable.getVideo());
        assertNull(interactable.getImage());
    }



}
