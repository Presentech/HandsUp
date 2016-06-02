package com.presentech.handsup;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
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
    public void setUp()  {
    }

    @Test
    public void jsonNoNullValues() {
        questionJSON questionJSON = new questionJSON();
        inputQuestion = new SingleQuestion();
        outputQuestion = new SingleQuestion();
        int slide = 3;
        int iteration = 3;
        int question = 3;
        boolean abc = true;
        boolean gmb = true;
        String testText = "String for Test";
        inputQuestion.setSLIDE(slide);
        inputQuestion.setQUESTION(question);
        inputQuestion.setAbc(abc);
        inputQuestion.setGoodmehbad(gmb);
        inputQuestion.setQuestionText(testText);
        List<SingleQuestion> singleQuestions = new ArrayList<SingleQuestion>();
        List<SingleQuestion> outputQuestion = new ArrayList<SingleQuestion>();
        singleQuestions.add(inputQuestion);

        String json = questionJSON.questionCreateJSON(singleQuestions);

        outputQuestion = questionJSON.questionParseJSON(json);

        assertTrue(outputQuestion.get(0).getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(outputQuestion.get(0).getQUESTION() == question);
        assertTrue(outputQuestion.get(0).isAbc() == abc);
        assertTrue(outputQuestion.get(0).isGoodmehbad() == gmb);
        assertEquals(outputQuestion.get(0).getQuestionText(), testText);
    }

    @Test
    //Check QuestionJSON can create and parse some JSON without any null values
    public void jsonNullValues() {
        questionJSON questionJSON = new questionJSON();
        inputQuestion = new SingleQuestion();
        outputQuestion = new SingleQuestion();

        int slide = 3;
        int iteration = 3;
        int question = 3;
        boolean abc = true;
        boolean gmb = true;
        String testText = null;


        inputQuestion.setSLIDE(slide);
        inputQuestion.setQUESTION(question);
        inputQuestion.setAbc(abc);
        inputQuestion.setGoodmehbad(gmb);
        inputQuestion.setQuestionText(testText);
        List<SingleQuestion> singleQuestions = new ArrayList<SingleQuestion>();
        List<SingleQuestion> outputQuestion = new ArrayList<SingleQuestion>();
        singleQuestions.add(inputQuestion);

        String json = questionJSON.questionCreateJSON(singleQuestions);

        outputQuestion = questionJSON.questionParseJSON(json);


        assertTrue(outputQuestion.get(0).getSLIDE() == slide);/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        assertTrue(outputQuestion.get(0).getQUESTION() == question);/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        assertTrue(outputQuestion.get(0).isAbc() == abc);
        assertTrue(outputQuestion.get(0).isGoodmehbad() == gmb);
        assertNull(outputQuestion.get(0).getQuestionText());
    }

}
