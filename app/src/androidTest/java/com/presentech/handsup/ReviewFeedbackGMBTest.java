package com.presentech.handsup;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Jack
 *
 * ReviewFeedbackGMBTest is not unit testable due to objects being created within the getPiePlots()
 * method meaning that they are not mockable. Approved by Testing Manger. See high level integration
 * tests for testing of this module.
 */

public class ReviewFeedbackGMBTest extends ActivityInstrumentationTestCase2<ReviewFeedbackGMB> {

    public ReviewFeedbackGMBTest() {
        super(ReviewFeedbackGMB.class);
    }
    double slide;
    ReviewFeedbackGMB activity;
    feedbackDatabaseHandler database;
    SingleFeedback singleFeedback1, singleFeedback2;

    public void setUp() throws Exception {

        super.setUp();

        activity = getActivity();

        MyApplication application = (MyApplication) activity.getApplication();
        database = application.getDB();

        setSingleFeedback1();
        setSingleFeedback2();

        List<SingleFeedback> singleFeedbackList = database.getAllFeedback();

    }

    @UiThreadTest
    public void testTest() {

        PieChart pie = Mockito.mock(PieChart.class);

        // produce expected a,b, and c
        List<SingleFeedback> singleFeedbackList = activity.singleFeedbackList;

        //get number of slides
        singleFeedbackList.get(0);

        for (int i = 0; i < singleFeedbackList.size(); i++) {
            if (singleFeedbackList.get(i).getSLIDE() > slide){
                slide = singleFeedbackList.get(i).getSLIDE();
            }
        }

        int sNo = (int) slide;

        int a = 0;
        int b = 0;
        int c = 0;
        int j = 0;

        a = b = c = 0;
            /*iterate through objects to find whether value is A, B or C*/
        for (int k = 0; k < singleFeedbackList.size(); k++) {
            Log.d("ABCD", "" + singleFeedbackList.get(k).getQUESTION());
            if ((int) singleFeedbackList.get(k).getSLIDE() == j) {
                Log.d("ABCD", "got Question");
                if (singleFeedbackList.get(k).getGOOD_MEH_BAD() == 1) {
                    a++;
                }
                if (singleFeedbackList.get(k).getGOOD_MEH_BAD() == 2) {
                    b++;
                }
                if (singleFeedbackList.get(k).getGOOD_MEH_BAD() == 3) {
                    c++;;
                }

            }
        }


        Segment expectedSegA = new Segment("Good = " +a, a);
        Segment expectedSegB = new Segment("Meh = " +b, b);
        Segment expectedSegC = new Segment("Bad = " +c, c);


        SegmentFormatter expectedSegmentFormatA = new SegmentFormatter();
        expectedSegmentFormatA.configure(activity, R.xml.segment_formatter);

        SegmentFormatter expectedSegmentFormatB = new SegmentFormatter();
        expectedSegmentFormatA.configure(activity, R.xml.segment_formatter2);

        SegmentFormatter expectedSegmentFormatC = new SegmentFormatter();
        expectedSegmentFormatA.configure(activity, R.xml.segment_formatter3);

        verify(pie,times(1)).addSeries(expectedSegA, expectedSegmentFormatA);
        verify(pie,times(1)).addSeries(expectedSegB, expectedSegmentFormatB);
        verify(pie,times(1)).addSeries(expectedSegC, expectedSegmentFormatC);
    }


    public void setSingleFeedback1(){
        singleFeedback1 = new SingleFeedback();
        UUID uuid = UUID.randomUUID();
        int slide = 0;
        int iteration = 1;
        int question = 1;
        int abc = 1;                    //a
        int gmb = 1;                    //good
        String testText = "test1";
        Long exampleTime =145164900000L; //1/1/16 12:00:00
        singleFeedback1.setUUID(uuid.toString());
        singleFeedback1.setSLIDE(slide);
        singleFeedback1.setSLIDE_ITERATION(iteration);
        singleFeedback1.setQUESTION(question);
        singleFeedback1.setABC(abc);
        singleFeedback1.setGOOD_MEH_BAD(gmb);
        singleFeedback1.setTEXT(testText);
        singleFeedback1.setTIME_RECEIVED(exampleTime);
        database.addFeedbackCollumn(singleFeedback1);
    }

    public void setSingleFeedback2(){
        singleFeedback2 = new SingleFeedback();
        UUID uuid = UUID.randomUUID();
        int slide = 0;
        int iteration = 1;
        int question = 1;
        int abc = 1;                    //a
        int gmb = 1;                    //good
        String testText = "test2";
        Long exampleTime =145164900000L; //1/1/16 12:00:00
        singleFeedback2.setUUID(uuid.toString());
        singleFeedback2.setSLIDE(slide);
        singleFeedback2.setSLIDE_ITERATION(iteration);
        singleFeedback2.setQUESTION(question);
        singleFeedback2.setABC(abc);
        singleFeedback2.setGOOD_MEH_BAD(gmb);
        singleFeedback2.setTEXT(testText);
        singleFeedback2.setTIME_RECEIVED(exampleTime);
        database.addFeedbackCollumn(singleFeedback2);
    }
}
