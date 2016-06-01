package com.presentech.handsup;
import static android.support.test.InstrumentationRegistry.getTargetContext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import com.androidplot.pie.PieChart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class ReviewFeedbackTest {
    private SingleFeedback inputFeedback1 = new SingleFeedback();
    private SingleFeedback inputFeedback2 = new SingleFeedback();
    private SingleFeedback inputFeedback3 = new SingleFeedback();
    private SingleFeedback inputFeedback4 = new SingleFeedback();
    private SingleFeedback inputFeedback5 = new SingleFeedback();
    private SingleFeedback inputFeedback6 = new SingleFeedback();


    feedbackDatabaseHandler database;

    UUID uuid1;
    UUID uuid2;
    UUID uuid3;
    UUID uuid4;
    UUID uuid5;
    UUID uuid6;

    @Before
    public void setUp() {
        getTargetContext().deleteDatabase("TestFeedbackList");
        database = new feedbackDatabaseHandler(getTargetContext(), "TestFeedbackList", null, 1, "FP");
        //database.deleteTable();

        PieChart pie = Mockito.mock(PieChart.class);
    }


    @After
    public void tearDown() throws Exception {
        database.close();
    }


    @Test
    public void writeThreeInRead3Out() throws Exception {
        //this.getSingleFeedbackSetupExample1();
        //this.getSingleFeedbackSetupExample2();
        //this.getSingleFeedbackSetupExample3();
        //this.getSingleFeedbackSetupExample4();
        //this.getSingleFeedbackSetupExample5();
        //this.getSingleFeedbackSetupExample6();

        getSingleFeedbackSetupExample1();
        getSingleFeedbackSetupExample2();
        getSingleFeedbackSetupExample3();
        getSingleFeedbackSetupExample4();
        getSingleFeedbackSetupExample5();
        getSingleFeedbackSetupExample6();

        /*database.addFeedbackCollumn(inputFeedback1);
        database.addFeedbackCollumn(inputFeedback2);
        database.addFeedbackCollumn(inputFeedback3);
        database.addFeedbackCollumn(inputFeedback4);
        database.addFeedbackCollumn(inputFeedback5);
        database.addFeedbackCollumn(inputFeedback6);*/


        //List<SingleFeedback> allFeedback;

        //allFeedback = database.getAllFeedback();

        //testAllFieldsEqual(allFeedback.get(0), inputFeedback1);
        //testAllFieldsEqual(allFeedback.get(1), inputFeedback2);
        //testAllFieldsEqual(allFeedback.get(2), inputFeedback3);

    }

    private void getSingleFeedbackSetupExample1(){
        uuid1 = UUID.randomUUID();//Example UUID
        int slide = 3;
        int iteration = 6;
        int question = 1;
        int abc = 2;
        int gmb = 1;
        String testText = "eeeThe Bartered Bride is a comic opera in three acts by the Czech composer Bedřich Smetana, first performed at the Provisional Theatre, Prague, on 30 May 1866. Set in a country village with realistic characters, it tells the story of how true love prevails over the combined efforts of ambitious parents and a scheming marriage broker. Originally presented in a two-act format with spoken dialogue, the opera was not immediately successful, but it gained rapid popularity after numerous revisions. Smetana's musical treatment made considerable use of traditional Bohemian dance forms such as the polka and furiant, creating music which was accurately folk-like, and considered to be quintessentially Czech in spirit. After a performance in Vienna in 1892 the opera achieved international recognition. It reached Chicago in 1893, London in 1895 and New York in 1909, becoming the first, and for many years the only, Czech opera in the general repertory. Many of these early international performances were in German, under the title Die verkaufte BraThe Bartered Bride is a comic opera in three acts by the Czech composer Bedřich Smetana, first performed at the Provisional Theatre, Prague, on 30 May 1866. Set in a country village with realistic characters, it tells the story of how true love prevails over the combined efforts of ambitious parents and a scheming marriage broker. Originally presented in a two-act format with spoken dialogue, the opera was not immediately successful, but it gained rapid popularity after numerous revisions. Smetana's musical treatment made considerable use of traditional Bohemian dance forms such as the polka and furiant, creating music which was accurately folk-like, and considered to be quintessentially Czech in spirit. After a performance in Vienna in 1892 the opera achieved international recognition. It reached Chicago in 1893, London in 1895 and New York in 1909, becoming the first, and for many years the only, Czech opera in the general repertory. Many of these early international performances were in German, under the title Die verkaufte Bra";
        Long exampleTime = 20L;
        inputFeedback1.setUUID(uuid1.toString());
        inputFeedback1.setSLIDE(slide);
        inputFeedback1.setSLIDE_ITERATION(iteration);
        inputFeedback1.setQUESTION(question);
        inputFeedback1.setABC(abc);
        inputFeedback1.setGOOD_MEH_BAD(gmb);
        inputFeedback1.setTEXT(testText);
        inputFeedback1.setTIME_RECEIVED(exampleTime);

        database.addFeedbackCollumn(inputFeedback1);
    }

    private void getSingleFeedbackSetupExample2(){
        uuid2 = UUID.randomUUID();//Example UUID
        int slide = 1;
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

        database.addFeedbackCollumn(inputFeedback2);

    }
    private void getSingleFeedbackSetupExample3(){
        uuid3 = UUID.randomUUID();//Example UUID
        int slide = 2;
        int iteration = -1;
        int question = 2;
        int abc = 1;
        int gmb = 3;
        String testText = "mmmm";
        Long exampleTime = 1340934830L;
        inputFeedback3.setUUID(uuid3.toString());
        inputFeedback3.setSLIDE(slide);
        inputFeedback3.setSLIDE_ITERATION(iteration);
        inputFeedback3.setQUESTION(question);
        inputFeedback3.setABC(abc);
        inputFeedback3.setGOOD_MEH_BAD(gmb);
        inputFeedback3.setTEXT(testText);
        inputFeedback3.setTIME_RECEIVED(exampleTime);

        database.addFeedbackCollumn(inputFeedback3);
    }

    private void getSingleFeedbackSetupExample4(){
        uuid4 = UUID.randomUUID();//Example UUID
        int slide = 2;
        int iteration = -1;
        int question = 1;
        int abc = 1;
        int gmb = 3;
        String testText = "ddddd";
        Long exampleTime = 15409853049L;
        inputFeedback4.setUUID(uuid3.toString());
        inputFeedback4.setSLIDE(slide);
        inputFeedback4.setSLIDE_ITERATION(iteration);
        inputFeedback4.setQUESTION(question);
        inputFeedback4.setABC(abc);
        inputFeedback4.setGOOD_MEH_BAD(gmb);
        inputFeedback4.setTEXT(testText);
        inputFeedback4.setTIME_RECEIVED(exampleTime);

        database.addFeedbackCollumn(inputFeedback4);
    }

    private void getSingleFeedbackSetupExample5(){
        uuid5 = UUID.randomUUID();//Example UUID
        int slide = 2;
        int iteration = -1;
        int question = 2;
        int abc = 1;
        int gmb = 2;
        String testText = "hhhhh";
        Long exampleTime = 1464638402L;
        inputFeedback5.setUUID(uuid3.toString());
        inputFeedback5.setSLIDE(slide);
        inputFeedback5.setSLIDE_ITERATION(iteration);
        inputFeedback5.setQUESTION(question);
        inputFeedback5.setABC(abc);
        inputFeedback5.setGOOD_MEH_BAD(gmb);
        inputFeedback5.setTEXT(testText);
        inputFeedback5.setTIME_RECEIVED(exampleTime);

        database.addFeedbackCollumn(inputFeedback5);
    }

    private void getSingleFeedbackSetupExample6(){
        uuid6 = UUID.randomUUID();//Example UUID
        int slide = 3;
        int iteration = -1;
        int question = 2;
        int abc = 2;
        int gmb = 1;
        String testText = null;
        Long exampleTime = 154093485309834L;
        inputFeedback6.setUUID(uuid3.toString());
        inputFeedback6.setSLIDE(slide);
        inputFeedback6.setSLIDE_ITERATION(iteration);
        inputFeedback6.setQUESTION(question);
        inputFeedback6.setABC(abc);
        inputFeedback6.setGOOD_MEH_BAD(gmb);
        inputFeedback6.setTEXT(testText);
        inputFeedback6.setTIME_RECEIVED(exampleTime);

        database.addFeedbackCollumn(inputFeedback6);
    }
}