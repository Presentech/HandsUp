package com.presentech.handsup;

import android.support.test.runner.AndroidJUnit4;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alex Butcher on 18/02/2016.
 */


@RunWith(AndroidJUnit4.class)
public class singleFeedbackTEst {
    private SingleFeedback singlefeedback;

    @Before
    //Setup method Instantiates a new parser and retrieves a PresentationFile from it.
    //It provides the parser with a test xml file from a resources folder
    public void setUp()  {


    }

    @Test
    //Check command creates a singlefeedback object
    public void createFeedbackObject() {
        singlefeedback = new SingleFeedback();
        assertNotNull(singlefeedback);
    }

    @Test
    //Check 'blank' feedback ocnstructor works.
    public void simpleConstructor() {
        singlefeedback = new SingleFeedback();

        assertNull(singlefeedback.UUID  );
        assertTrue(singlefeedback.SLIDE == -1);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(singlefeedback.SLIDE_ITERATION == -1);/** Slide iteration - for when a slide is visited multiple times.*/
        assertTrue(singlefeedback.QUESTION == -1);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(singlefeedback.ABC == -1); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(singlefeedback.GOOD_MEH_BAD == -1); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertNull(singlefeedback.TEXT); /** If anything but null the text should be interprted - acts as flag for text input.*/
        assertTrue(singlefeedback.TIME_RECEIVED == -1);
    }


    @Test
    //Check 'blank' feedback ocnstructor works.
    public void gettersAndSetters() {
        singlefeedback = new SingleFeedback();

        //Example UUID
        UUID uuid = UUID.randomUUID();
        singlefeedback.setUUID(uuid.toString());
        int slide = 3;
        singlefeedback.setSLIDE(slide);
        int iteration = 3;
        singlefeedback.setSLIDE_ITERATION(iteration);
        int question = 3;
        singlefeedback.setQUESTION(question);
        int abc = 1;
        singlefeedback.setABC(1);
        int gmb = 2;
        singlefeedback.setGOOD_MEH_BAD(gmb);
        String testText = "String for Test";
        singlefeedback.setTEXT(testText);
        Long exampleTime = 2020203L;
        singlefeedback.setTIME_RECEIVED(exampleTime);

        assertEquals(singlefeedback.getUUID(), uuid.toString() );
        assertTrue(singlefeedback.getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(singlefeedback.getSLIDE_ITERATION() == iteration);/** Slide iteration - for when a slide is visited multiple times.*/
        assertTrue(singlefeedback.getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(singlefeedback.getABC() == abc); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(singlefeedback.getGOOD_MEH_BAD() == gmb); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertEquals(singlefeedback.getTEXT(), testText); /** If anything but null the text should be interprted - acts as flag for text input.*/
        assertTrue(singlefeedback.getTIME_RECEIVED() == exampleTime);
    }
}
