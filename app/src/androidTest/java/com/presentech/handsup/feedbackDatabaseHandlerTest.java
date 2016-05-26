package com.presentech.handsup;
import static android.support.test.InstrumentationRegistry.getTargetContext;
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
    private SingleFeedback inputFeedback = new SingleFeedback();
    private SingleFeedback outputFeedback = new SingleFeedback() ;
    feedbackDatabaseHandler database;



    @Before
    public void setUp() {
        getTargetContext().deleteDatabase("Test Database");
        database = new feedbackDatabaseHandler(getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }


    @Test
    public void writeThreeInRead3Out() throws Exception {
        this.getSingleFeedbackSetupExample1();
        database.addFeedbackCollumn(inputFeedback);
        this.getSingleFeedbackSetupExample2();
        database.addFeedbackCollumn(inputFeedback);
        this.getSingleFeedbackSetupExample3();
        database.addFeedbackCollumn(inputFeedback);

        List<SingleFeedback> allFeedback;

        allFeedback = database.getAllFeedback();

        this.getSingleFeedbackSetupExample1();
        testAllFieldsEqual(allFeedback.get(0));
        this.getSingleFeedbackSetupExample2();
        testAllFieldsEqual(allFeedback.get(1));
        this.getSingleFeedbackSetupExample3();
        testAllFieldsEqual(allFeedback.get(2));

    }

private void getSingleFeedbackSetupExample1(){
        UUID uuid = UUID.randomUUID();//Example UUID
        int slide = 1;
        int iteration = 1;
        int question = 1;
        int abc = 1;
        int gmb = 1;
        String testText = "1";
        Long exampleTime = 1L;
        inputFeedback.setUUID(uuid.toString());
        inputFeedback.setSLIDE(slide);
        inputFeedback.setSLIDE_ITERATION(iteration);
        inputFeedback.setQUESTION(question);
        inputFeedback.setABC(abc);
        inputFeedback.setGOOD_MEH_BAD(gmb);
        inputFeedback.setTEXT(testText);
        inputFeedback.setTIME_RECEIVED(exampleTime);
    }

private void getSingleFeedbackSetupExample2(){

        UUID uuid = UUID.randomUUID();//Example UUID
        int slide = 2;
        int iteration = 2;
        int question = 2;
        int abc = 2;
        int gmb = 2;
        String testText = "Text2";
        Long exampleTime = 2L;
        inputFeedback.setUUID(uuid.toString());
        inputFeedback.setSLIDE(slide);
        inputFeedback.setSLIDE_ITERATION(iteration);
        inputFeedback.setQUESTION(question);
        inputFeedback.setABC(abc);
        inputFeedback.setGOOD_MEH_BAD(gmb);
        inputFeedback.setTEXT(testText);
        inputFeedback.setTIME_RECEIVED(exampleTime);
    }
private void getSingleFeedbackSetupExample3(){
        UUID uuid = UUID.randomUUID();//Example UUID
        int slide = -1;
        int iteration = -1;
        int question = -1;
        int abc = -1;
        int gmb = -1;
        String testText = null;
        Long exampleTime = 1L;
        inputFeedback.setUUID(uuid.toString());
        inputFeedback.setSLIDE(slide);
        inputFeedback.setSLIDE_ITERATION(iteration);
        inputFeedback.setQUESTION(question);
        inputFeedback.setABC(abc);
        inputFeedback.setGOOD_MEH_BAD(gmb);
        inputFeedback.setTEXT(testText);
        inputFeedback.setTIME_RECEIVED(exampleTime);
    }




     public void testAllFieldsEqual(SingleFeedback feedbackin) {
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