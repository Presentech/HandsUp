package com.presentech.handsup;

/*Created by Jack Bardsley*/
/*Modified by Connor Stoner*/

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;

import java.util.List;


public class ReviewFeedbackGMB extends Activity {

    public double slide;         //Number of slides
    int j = 0;              //currently displayed slide responses
    public PieChart slideGMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback_gmb);

        getPiePlots();



    }


    public void getPiePlots() {
        //get list of objects from database
        MyApplication application = (MyApplication) getApplication();
        feedbackDatabaseHandler database = application.getDB();
        List<SingleFeedback> singleFeedbackList = database.getAllFeedback();

        //convert list of feedback objects to array of feedbackobjects
        SingleFeedback[] feedbackArray = new SingleFeedback[singleFeedbackList.size()];
        singleFeedbackList.toArray(feedbackArray);



        //get number of slides
        slide = feedbackArray[0].getSLIDE();

        for (int i = 0; i < feedbackArray.length; i++) {
            if (feedbackArray[i].getSLIDE() > slide){
                slide = feedbackArray[i].getSLIDE();
            }
        }

        int sNo = (int) slide;

        int a = 0;
        int b = 0;
        int c = 0;

            a = b = c = 0;
            /*iterate through objects to find whether value is A, B or C*/
            for (int k = 0; k < feedbackArray.length; k++) {
                Log.d("ABCD", "" + feedbackArray[k].getQUESTION() );
                if ((int) feedbackArray[k].getSLIDE() == j) {
                    Log.d("ABCD", "got Question");
                    if (feedbackArray[k].getGOOD_MEH_BAD() == 1) {
                        a++;
                    }
                    if (feedbackArray[k].getGOOD_MEH_BAD() == 2) {
                        b++;
                    }
                    if (feedbackArray[k].getGOOD_MEH_BAD() == 3) {
                        c++;;
                    }

                }
            }

        Log.d("ABCD", "a" + a + "b" + b + "c" + c);
        slideGMB = (PieChart) findViewById(R.id.plot); //get pie plot

        SegmentFormatter segmentFormatA = new SegmentFormatter();
        segmentFormatA.configure(this, R.xml.segment_formatter);

        SegmentFormatter segmentFormatB = new SegmentFormatter();
        segmentFormatB.configure(this, R.xml.segment_formatter2);

        SegmentFormatter segmentFormatC = new SegmentFormatter();
        segmentFormatC.configure(this, R.xml.segment_formatter3);


        slideGMB.setTitle("Responses from Slide " + Integer.toString(j + 1));

        slideGMB.getTitleWidget().position(5, XLayoutStyle.ABSOLUTE_FROM_LEFT, 0, YLayoutStyle.ABSOLUTE_FROM_TOP);

        //set button visibilities depending on question number
        if (j == 0) {
            findViewById(R.id.slide_down_button).setVisibility(View.GONE);
        }
        else
            findViewById(R.id.slide_down_button).setVisibility(View.VISIBLE);

        if (j == sNo-1) {
            findViewById(R.id.slide_up_button).setVisibility(View.GONE);
        }
        else
            findViewById(R.id.slide_up_button).setVisibility(View.VISIBLE);


        //add segments
        if (a > 0) {
            Segment segA = new Segment("Good = "+a, a);
            slideGMB.addSeries(segA, segmentFormatA);
        }
        if (b > 0) {
            Segment segB = new Segment("Meh = " +b, b);
            slideGMB.addSeries(segB, segmentFormatB);
        }
        if (c > 0) {
            Segment segC = new Segment("Bad = " +c, c);
            slideGMB.addSeries(segC, segmentFormatC);
        }

    }

    //increment question number (array index "j") and redraw plots
    public void slideNoUp(View v) {
        j++;
        slideGMB.clear();
        getPiePlots();
        slideGMB.redraw();
    }

    public void slideNoDown(View v) {
        j--;
        slideGMB.clear();
        getPiePlots();
        slideGMB.redraw();

    }
}
