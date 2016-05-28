package com.presentech.handsup;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by epren on 17/05/2016.
 */

@RunWith(AndroidJUnit4.class)
public class FeedbackJSONTest {
    private SingleFeedback inputFeedback;
    private SingleFeedback outputFeedback;

    @Before
    //Setup method Instantiates a new parser and retrieves a PresentationFile from it.
    //It provides the parser with a test xml file from a resources folder
    public void setUp()  {
    }


    @Test
    //Check FeddbackJSON can create and parse some JSON without any null values
    public void jsonNoNullValues() {
        inputFeedback = new SingleFeedback();
        outputFeedback = new SingleFeedback();
        UUID uuid = UUID.randomUUID();//Example UUID
        int slide = 3;
        int iteration = 3;
        int question = 3;
        int abc = 1;
        int gmb = 2;
        String testText = "String for Test";
        Long exampleTime = 20L;
        inputFeedback.setUUID(uuid.toString());
        inputFeedback.setSLIDE(slide);
        inputFeedback.setSLIDE_ITERATION(iteration);
        inputFeedback.setQUESTION(question);
        inputFeedback.setABC(abc);
        inputFeedback.setGOOD_MEH_BAD(gmb);
        inputFeedback.setTEXT(testText);
        inputFeedback.setTIME_RECEIVED(exampleTime);


         String json = FeedbackJSON.FeedbackJSONGenerate(inputFeedback);

         outputFeedback = FeedbackJSON.FeedbackJSONParse(json);


        assertEquals(outputFeedback.getUUID(), uuid.toString() );
        assertTrue(outputFeedback.getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(outputFeedback.getSLIDE_ITERATION() == iteration);/** Slide iteration - for when a slide is visited multiple times.*/
        assertTrue(outputFeedback.getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(outputFeedback.getABC() == abc); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(outputFeedback.getGOOD_MEH_BAD() == gmb); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertEquals(outputFeedback.getTEXT(), testText); /** If anything but null the text should be interprted - acts as flag for text input.*/
        assertTrue(outputFeedback.getTIME_RECEIVED() == exampleTime);
    }




    @Test
    //Check FeddbackJSON can create and parse some JSON without any null values
    public void jsonNullValues() {
        inputFeedback = new SingleFeedback();
        outputFeedback = new SingleFeedback();
        UUID uuid = UUID.randomUUID();//Example UUID
        int slide = 3;
        int iteration = 3;
        int question = 3;
        int abc = 1;
        int gmb = 2;
        String testText = null; // HERE is the importaint bit
        Long exampleTime = 23L;

        inputFeedback.setUUID(uuid.toString());
        inputFeedback.setSLIDE(slide);
        inputFeedback.setSLIDE_ITERATION(iteration);
        inputFeedback.setQUESTION(question);
        inputFeedback.setABC(1);
        inputFeedback.setGOOD_MEH_BAD(gmb);
        inputFeedback.setTEXT(testText);
        inputFeedback.setTIME_RECEIVED(exampleTime);


        String json = FeedbackJSON.FeedbackJSONGenerate(inputFeedback);

        outputFeedback = FeedbackJSON.FeedbackJSONParse(json);


        assertEquals(outputFeedback.getUUID(), uuid.toString() );
        assertTrue(outputFeedback.getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(outputFeedback.getSLIDE_ITERATION() == iteration);/** Slide iteration - for when a slide is visited multiple times.*/
        assertTrue(outputFeedback.getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(outputFeedback.getABC() == abc); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(outputFeedback.getGOOD_MEH_BAD() == gmb); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertNull(outputFeedback.TEXT); /** If anything but null the text should be interprted - acts as flag for text input.*/
        assertTrue(outputFeedback.getTIME_RECEIVED() == exampleTime);
    }

}
