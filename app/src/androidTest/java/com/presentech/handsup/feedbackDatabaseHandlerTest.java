package com.presentech.handsup;
import static android.support.test.InstrumentationRegistry.getTargetContext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by epren on 17/05/2016.
 */


@RunWith(AndroidJUnit4.class)
public class feedbackDatabaseHandlerTest {
    private SingleFeedback inputFeedback1 = new SingleFeedback();
    private SingleFeedback inputFeedback2 = new SingleFeedback();
    private SingleFeedback inputFeedback3 = new SingleFeedback();

    feedbackDatabaseHandler database;

    UUID uuid1;
    UUID uuid2;
    UUID uuid3;

    @Before
    public void setUp() {
        getTargetContext().deleteDatabase("Test Database");
        database = new feedbackDatabaseHandler(getTargetContext(), "name");
        database.deleteTable();
    }


    @After
    public void tearDown() throws Exception {
        database.close();
    }


    @Test
    public void writeThreeInRead3Out() throws Exception {
        this.getSingleFeedbackSetupExample1();
        this.getSingleFeedbackSetupExample2();
        this.getSingleFeedbackSetupExample3();

        database.addFeedbackCollumn(inputFeedback1);
        database.addFeedbackCollumn(inputFeedback2);
        database.addFeedbackCollumn(inputFeedback3);


        List<SingleFeedback> allFeedback;

        allFeedback = database.getAllFeedback();

        testAllFieldsEqual(allFeedback.get(0), inputFeedback1);
        testAllFieldsEqual(allFeedback.get(1), inputFeedback2);
        testAllFieldsEqual(allFeedback.get(2), inputFeedback3);

    }

private void getSingleFeedbackSetupExample1(){
    uuid1 = UUID.randomUUID();//Example UUID
        int slide = 51;
        int iteration = 6;
        int question = 7;
        int abc = 8;
        int gmb = 9;
        String testText = "blah";
        Long exampleTime = 20L;
        inputFeedback1.setUUID(uuid1.toString());
        inputFeedback1.setSLIDE(slide);
        inputFeedback1.setSLIDE_ITERATION(iteration);
        inputFeedback1.setQUESTION(question);
        inputFeedback1.setABC(abc);
        inputFeedback1.setGOOD_MEH_BAD(gmb);
        inputFeedback1.setTEXT(testText);
        inputFeedback1.setTIME_RECEIVED(exampleTime);
    }

private void getSingleFeedbackSetupExample2(){
        uuid2 = UUID.randomUUID();//Example UUID
        int slide = 2;
        int iteration = 2;
        int question = 2;
        int abc = 2;
        int gmb = 2;
        String testText = "Text2";
        Long exampleTime = 2L;
        inputFeedback2.setUUID(uuid2.toString());
        inputFeedback2.setSLIDE(slide);
        inputFeedback2.setSLIDE_ITERATION(iteration);
        inputFeedback2.setQUESTION(question);
        inputFeedback2.setABC(abc);
        inputFeedback2.setGOOD_MEH_BAD(gmb);
        inputFeedback2.setTEXT(testText);
        inputFeedback2.setTIME_RECEIVED(exampleTime);
    }
private void getSingleFeedbackSetupExample3(){
        uuid3 = UUID.randomUUID();//Example UUID
        int slide = -1;
        int iteration = -1;
        int question = -1;
        int abc = -1;
        int gmb = -1;
        String testText = null;
        Long exampleTime = 1L;
        inputFeedback3.setUUID(uuid3.toString());
        inputFeedback3.setSLIDE(slide);
        inputFeedback3.setSLIDE_ITERATION(iteration);
        inputFeedback3.setQUESTION(question);
        inputFeedback3.setABC(abc);
        inputFeedback3.setGOOD_MEH_BAD(gmb);
        inputFeedback3.setTEXT(testText);
        inputFeedback3.setTIME_RECEIVED(exampleTime);
    }




     public void testAllFieldsEqual(SingleFeedback feedbackin, SingleFeedback inputFeedback) {
         assertEquals(feedbackin.getUUID(), inputFeedback.getUUID());
         assertTrue(feedbackin.getSLIDE() == inputFeedback.getSLIDE());/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
         assertTrue(feedbackin.getSLIDE_ITERATION() == inputFeedback.getSLIDE_ITERATION());/** Slide iteration - for when a slide is visited multiple times.*/
         assertTrue(feedbackin.getQUESTION() == inputFeedback.getQUESTION());/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
         assertTrue(feedbackin.getABC() == inputFeedback.getABC()); /** -1 not sent, 1 a, 2 - b, 3 -c*/
         assertTrue(feedbackin.getGOOD_MEH_BAD() == inputFeedback.getGOOD_MEH_BAD()); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
         assertEquals(feedbackin.getTEXT(), inputFeedback.getTEXT()); /** If anything but null the text should be interprted - acts as flag for text input.*/
         assertTrue(feedbackin.getTIME_RECEIVED() == inputFeedback.getTIME_RECEIVED());
    }


    }