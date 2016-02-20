package com.presentech.handsup;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alex on 18/02/2016.
 */
public class XMLParserTest {
    private XMLParser parser;
    private Slideshow slideshow;

    @Test
    public void createSlideshowObject() {
        parser = new XMLParser();
        slideshow = parser.getSlideshow();

        assertNotNull(slideshow);


    };



}
