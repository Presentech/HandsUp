package com.presentech.handsup;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alex on 18/02/2016.
 */
public class XMLParserTest {
    private XMLParser parser;
    private PresentationFile presentationFile;
    private DocumentInfo documentInfo;



    @Before
    public void setUp() throws IOException, XmlPullParserException {
        parser = new XMLParser();

        //File inputFile = new File("test.xml");
       // FileInputStream stream = new FileInputStream(inputFile);
       // InputStream in = this.getClass().getClassLoader().getResourceAsStream("test.xml");
        parser.getPresentation();
    }


    @Test
    public void createPresentationObject() {
        URL url = this.getClass().getClassLoader().getResource("test.xml");
        System.out.println(url.getPath());
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
        assertEquals("An example file used to demonstrate the appearance of this format",
                presentationFile.getComment());
    }



}
