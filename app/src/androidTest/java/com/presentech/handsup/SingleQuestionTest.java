package com.presentech.handsup;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**

 * Created by Amit Ramjali on 29/05/2016.

 */

@RunWith(AndroidJUnit4.class)
public class SingleQuestionTest {
    private SingleQuestion singlequestion;

    /* Method to be run before every test*/

    @Before

    public void setUp() {

    }

    @Test
    //Check command for object
    public void createQuestionObject() {
        singlequestion = new SingleQuestion();
        assertNotNull(singlequestion);
    }

/*    @Test
    public void simpleConstructor() {
        singlequestion = new SingleQuestion();

        assertTrue(singlequestion.SLIDE == -1);*//** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*//*
        assertTrue(singlequestion.QUESTION == -1);*//** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*//*
        assertTrue(singlequestion.abc == false);
        assertTrue(singlequestion.goodmehbad == false );
        assertTrue(singlequestion.TIME_RECEIVED == -1);
    }*/


}