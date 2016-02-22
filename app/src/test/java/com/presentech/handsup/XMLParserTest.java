package com.presentech.handsup;

import org.junit.Before;
import org.junit.Test;

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
    public void setUp(){
        parser = new XMLParser();
        presentationFile = parser.getPresentation("testPresentation.xml");
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
        assertEquals("An example file used to demonstrate the appearance of this format",
                presentationFile.getComment());
    }



}
