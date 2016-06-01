package com.presentech.handsup;

/*Created by Jack Bardsley on 18/05/16*/

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


public class ReviewFeedback extends Activity {

    public int qNo;         //Number of questions
    int j = 0;              //currently displayed question answers
    public PieChart questionABC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback);
        getPiePlots();

    }


    public void getPiePlots() {

        //get list of objects from database
        MyApplication application = (MyApplication) getApplication();
        feedbackDatabaseHandler database = application.getDB();
        List<SingleFeedback> singleFeedbackList = database.getAllFeedback();

        //get number of questions
        qNo = singleFeedbackList.get(0).getQUESTION();

        for (int i = 0; i < singleFeedbackList.size(); i++) {
            if (singleFeedbackList.get(i).getQUESTION() > qNo){
                qNo = singleFeedbackList.get(i).getQUESTION();
            }
        }

        int a;
        int b;
        int c;

            a = b = c = 0;
            /*iterate through objects to find whether value is A, B or C*/
            for (int k = 0; k < singleFeedbackList.size(); k++) {
                if (singleFeedbackList.get(k).getQUESTION() == j + 1) {
                    if (singleFeedbackList.get(k).getABC() == 1) {
                        a++;
                    }
                    if (singleFeedbackList.get(k).getABC() == 2) {
                        b++;
                    }
                    if (singleFeedbackList.get(k).getABC() == 3) {
                        c++;
                    }

                }
            }
        Log.d("ABCD", "a" + a + "b" + b + "c" + c);
        questionABC = (PieChart) findViewById(R.id.plot); //get pie plot

        SegmentFormatter segmentFormatA = new SegmentFormatter();
        segmentFormatA.configure(this, R.xml.segment_formatter);

        SegmentFormatter segmentFormatB = new SegmentFormatter();
        segmentFormatB.configure(this, R.xml.segment_formatter2);

        SegmentFormatter segmentFormatC = new SegmentFormatter();
        segmentFormatC.configure(this, R.xml.segment_formatter3);




        questionABC.setTitle("Answers to question " + Integer.toString(j + 1));

        questionABC.getTitleWidget().position(5, XLayoutStyle.ABSOLUTE_FROM_LEFT, 0, YLayoutStyle.ABSOLUTE_FROM_TOP);

        //set button visibilities depending on question number
        if (j == 0) {
            findViewById(R.id.down_button).setVisibility(View.GONE);
        }
        else
            findViewById(R.id.down_button).setVisibility(View.VISIBLE);

        if (j == qNo-1) {
            findViewById(R.id.up_button).setVisibility(View.GONE);
        }
        else
            findViewById(R.id.up_button).setVisibility(View.VISIBLE);


        //add segments
        if (a > 0) {
            Segment segA = new Segment("A = "+ a,a);
            questionABC.addSeries(segA, segmentFormatA);
        }
        if (b > 0) {
            Segment segB = new Segment("B = " +b,b);
            questionABC.addSeries(segB, segmentFormatB);
        }
        if (c > 0) {
            Segment segC = new Segment("C = " + c,c);
            questionABC.addSeries(segC, segmentFormatC);
        }

    }

    //increment question number (array index "j") and redraw plots
    public void questionNoUp(View v) {
        j++;
        questionABC.clear();
        getPiePlots();
        questionABC.redraw();
    }

    public void questionNoDown(View v) {
        j--;
        questionABC.clear();
        getPiePlots();
        questionABC.redraw();

    }
}
