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
 * Created by Amit on 01/06/2016.
 */
@RunWith(AndroidJUnit4.class)
public class QuestionJSONTest {
    private SingleQuestion inputQuestion;
    private SingleQuestion outputQuestion;

    @Before
    //Setup method Instantiates a new parser and retrieves a PresentationFile from it.
    //It provides the parser with a test xml file from a resources folder
    public void setUp()  {
    }

    public void jsonNoNullValues() {
        inputQuestion = new SingleQuestion();
        outputQuestion = new SingleQuestion();
        int slide = 3;
        int iteration = 3;
        int question = 3;
        boolean abc = true;
        boolean gmb = true;
        String testText = "String for Test";
        Long exampleTime = 20L;
        inputQuestion.setSLIDE(slide);
        inputQuestion.setQUESTION(question);
        inputQuestion.setAbc(abc);
        inputQuestion.setGoodmehbad(gmb);
        inputQuestion.setQuestionText(testText);

        String json = questionJSON.questionCreateJSON(inputQuestion);

        outputQuestion = questionJSON.questionParseJSON(json);

        assertTrue(outputQuestion.getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(outputQuestion.getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(outputQuestion.isAbc() == abc); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(outputQuestion.isGoodmehbad() == gmb); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertEquals(outputQuestion.getQuestionText(), testText); /** If anything but null the text should be interprted - acts as flag for text input.*/
    }

    @Test
    //Check FeddbackJSON can create and parse some JSON without any null values
    public void jsonNullValues() {
        inputQuestion = new SingleQuestion();
        outputQuestion = new SingleQuestion();

        int slide = 3;
        int iteration = 3;
        int question = 3;
        boolean abc = true;
        boolean gmb = true;
        String testText = null; // HERE is the importaint bit
        Long exampleTime = 23L;


        inputQuestion.setSLIDE(slide);
        inputQuestion.setQUESTION(question);
        inputQuestion.setAbc(abc);
        inputQuestion.setGoodmehbad(gmb);
        inputQuestion.setQuestionText(testText);

        String json = questionJSON.questionCreateJSON(inputQuestion);

        outputQuestion = questionJSON.questionParseJSON(json);



        assertTrue(outputQuestion.getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(outputQuestion.getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(outputQuestion.isAbc() == abc); /** -1 not sent, 1 a, 2 - b, 3 -c*/
        assertTrue(outputQuestion.isGoodmehbad() == gmb); /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        assertNull(outputQuestion.questionText); /** If anything but null the text should be interprted - acts as flag for text input.*/
    }

}
