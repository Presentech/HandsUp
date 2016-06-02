package com.presentech.handsup;

//import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;


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

        assertTrue(singlequestion.SLIDE == -1.0);
        assertTrue(singlequestion.QUESTION == -1);
        assertTrue(singlequestion.abc == false);
        assertTrue(singlequestion.goodmehbad == false );
        assertTrue(singlequestion.questionText == null);

    }
    //check setter and getters actually record correct values
    @Test
    public void gettersAndSetters() {
        singlequestion = new SingleQuestion();

        int slide = 3;
        singlequestion.setSLIDE(slide);
        int question = 3;
        singlequestion.setQUESTION(question);
        boolean abc = false;
        singlequestion.setAbc(abc);
        boolean gmb = false;
        singlequestion.setGoodmehbad(gmb);
        String testText = "String for Test";
        singlequestion.setQuestionText(testText);

        assertTrue(singlequestion.getSLIDE() == slide);
        assertTrue(singlequestion.getQUESTION() == question);
        assertTrue(singlequestion.isAbc() == abc);
        assertTrue(singlequestion.isGoodmehbad() == gmb);
        assertEquals(singlequestion.getQuestionText(), testText);
    }

}
