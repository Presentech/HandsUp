package com.presentech.handsup;

//import android.support.test.runner.AndroidJUnit4;



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
 * Created by Amit on 31/05/2016.
 */


public class SingleQuestionTest {

    private SingleQuestion singlequestion;

/* Method to be run before every test*/

    @Before
    public void setup() {

    }

    @Test
    //Check command for object
    public void createQuestionObject() {
        singlequestion = new SingleQuestion();
        assertNotNull(singlequestion);
    }

    @Test
    public void simpleConstructor() {
    singlequestion = new SingleQuestion();

    assertTrue(singlequestion.SLIDE == -1.0);//** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*//*
    assertTrue(singlequestion.QUESTION == -1);//** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*//*
    assertTrue(singlequestion.abc == false);
    assertTrue(singlequestion.goodmehbad == false );
    assertTrue(singlequestion.questionText == null);

    }

    public void gettersAndSetters() {
        singlequestion = new SingleQuestion();


        int slide = 3;
        singlequestion.setSLIDE(slide);
        //int iteration = 3;
        //singlequestion.setSLIDE_ITERATION(iteration);
        int question = 3;
        singlequestion.setQUESTION(question);
        boolean abc = false;
        singlequestion.setAbc(abc);
        boolean gmb = false;
        singlequestion.setGoodmehbad(gmb);
        String testText = "String for Test";
        singlequestion.setQuestionText(testText);
        Long exampleTime = 2020203L;



        assertTrue(singlequestion.getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        //assertTrue(singlequestion.getSLIDE_ITERATION() == iteration);/** Slide iteration - for when a slide is visited multiple times.*/
        assertTrue(singlequestion.getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(singlequestion.isAbc() == abc); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(singlequestion.isGoodmehbad() == gmb); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertEquals(singlequestion.getQuestionText(), testText); /** If anything but null the text should be interprted - acts as flag for text input.*/

    }

}
