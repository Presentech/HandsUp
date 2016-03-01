package com.presentech.handsup;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alex on 18/02/2016.
 */
@RunWith(AndroidJUnit4.class)
public class InstXMLParserTest {
    private XMLParser parser;
    private PresentationFile presentationFile;
    private DocumentInfo documentInfo;



    @Before
    public void setUp() throws IOException, XmlPullParserException {
        parser = new XMLParser();

        //File inputFile = new File("test.xml");
       // FileInputStream stream = new FileInputStream(inputFile);
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.xml");
        presentationFile = parser.getPresentation(in);
    }

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    @Test
    public void fileObjectShouldNotBeNull() throws Exception {
        File file = getFileFromPath(this, "test.xml");
        assertNotNull(file);
    }


    @Test
    public void createPresentationObject() {
        assertNotNull(presentationFile);

    };

    @Test
    public void documentInfoContainedinPresentation() {
        assertNotNull(presentationFile.getTitle());
        assertNotNull(presentationFile.getAuthor());
        assertNotNull(presentationFile.getVersion());
        assertNotNull(presentationFile.getComment());
    }

    @Test
    public void presentationContainsCorrectData() {
        assertEquals("Example File", presentationFile.getTitle());
        assertEquals("Lexxy", presentationFile.getAuthor());
        assertEquals("1.0", presentationFile.getVersion());
        assertEquals("" +
                "An example file used to demonstrate the appearance of this format" +
                "", presentationFile.getComment());
    }

    @Test
    public void presentationContainsCorrectDefaults() {
        assertEquals("FFFFFF", presentationFile.getDefBackgroundColour());
        assertEquals("Helvetica", presentationFile.getDefFont());
        assertEquals(12, presentationFile.getDefFontSize());
        assertEquals("000000", presentationFile.getDefFontColour());
        assertEquals("0000CC", presentationFile.getDefLineColour());
        assertEquals("2222AA", presentationFile.getDefFillColour());
    }

    @Test
    public void presentationContainsListOfSlides() {
        assertTrue(presentationFile.getSlides() instanceof List);
    }

    @Test
    public void presentationContainsCorrectNumberOfSlides(){
        assertEquals(5, presentationFile.getSlides().size());
    }

    @Test
    public void slideContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(0);
        assertEquals(0, slide.getSlideID());
        assertEquals(5, slide.getDuration());
        assertEquals(2, slide.getNextSlide());

        slide = presentationFile.getSlides().get(1);
        assertEquals(1, slide.getSlideID());
        assertEquals(slide.NULL_ATTR, slide.getDuration());
        assertEquals(slide.NULL_ATTR, slide.getNextSlide());

        slide = presentationFile.getSlides().get(3);
        assertEquals(3, slide.getSlideID());
        assertEquals(8, slide.getDuration());
        assertEquals(-1, slide.getNextSlide());
    }

    @Test
    public void slideContainsListOfMediaObjects() {
        Slide slide = presentationFile.getSlides().get(0);
        assertTrue(slide.getMediaObjects() instanceof List);
    }

    @Test
    public void textContainsCorrectAttributes() {
        Slide slide = presentationFile.getSlides().get(0);
        Text text = slide.getText().get(0);
    }



}
