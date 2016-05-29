package com.presentech.handsup;

/*Created by Jack Bardsley on 18/05/16*/

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;



public class ReviewFeedback extends Activity {

    public int qNo;         //Number of questions
    int j = 0;              //currently displayed question answers
    public PieChart questionABC;

    /*Example object array*/
    public SingleFeedback[] feedbackArray = new SingleFeedback[10];

    public void setFeedbackArray() {
        /*Set some example objects that would be expected to recieve*/
        feedbackArray[0] = new SingleFeedback("abc", 1.00, 1, 1, 2, 2, "abc", 123L);
        feedbackArray[1] = new SingleFeedback("abc", 1.00, 3, 1, 2, 1, "abc", 123L);
        feedbackArray[2] = new SingleFeedback("abc", 1.00, 1, 1, 1, 2, "abc", 123L);
        feedbackArray[3] = new SingleFeedback("abc", 1.00, 1, 1, 1, 1, "abc", 123L);
        feedbackArray[4] = new SingleFeedback("abc", 1.00, 2, 1, 3, 1, "abc", 123L);
        feedbackArray[5] = new SingleFeedback("abc", 1.00, 1, 2, 2, 2, "abc", 123L);
        feedbackArray[6] = new SingleFeedback("abc", 1.00, 1, 2, 2, 1, "abc", 123L);
        feedbackArray[7] = new SingleFeedback("abc", 1.00, 1, 2, 1, 2, "abc", 123L);
        feedbackArray[8] = new SingleFeedback("abc", 1.00, 3, 2, 1, 1, "abc", 123L);
        feedbackArray[9] = new SingleFeedback("abc", 1.00, 1, 3, 3, 1, "abc", 123L);
    }

    /*Get Number of questions*/
    public void getQuestionNumber() {
        for (int i = 0; i < feedbackArray.length; i++) {
            if (feedbackArray[i].getQUESTION() > 0) {
                qNo = feedbackArray[i].getQUESTION();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback);


        setFeedbackArray();
        getQuestionNumber();
        getPiePlots();

    }


    public void getPiePlots() {
        int[] answerA = new int[qNo];           /*Initialise array for A answers for questions*/
        int[] answerB = new int[qNo];           /*Initialise array for B answers for questions*/
        int[] answerC = new int[qNo];           /*Initialise array for C answers for questions*/

        int a = 0;
        int b = 0;
        int c = 0;

        /*iterate through quesitons*/
        for (int i = 0; i < qNo; i++) {
            a = b = c = 0;
            /*iterate through objects to find whether value is A, B or C*/
            for (int k = 0; k < feedbackArray.length; k++) {
                if (feedbackArray[k].getQUESTION() == i + 1) {
                    if (feedbackArray[k].getABC() == 1) {
                        a++;
                        answerA[i] = a;
                    }
                    if (feedbackArray[k].getABC() == 2) {
                        b++;
                        answerB[i] = b;
                    }
                    if (feedbackArray[k].getABC() == 3) {
                        c++;
                        answerC[i] = c;
                    }

                }
            }
        }

        questionABC = (PieChart) findViewById(R.id.plot); //get pie plot

        SegmentFormatter segmentFormat = new SegmentFormatter();
        segmentFormat.configure(this, R.xml.segment_formatter);


        questionABC.setTitle("Answers to question number " + Integer.toString(j + 1));

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
        if (answerA[j] > 0) {
            Segment segA = new Segment("A = "+answerA[j], answerA[j]);
            questionABC.addSeries(segA, segmentFormat);
        }
        if (answerB[j] > 0) {
            Segment segB = new Segment("B = " +answerB[j], answerB[j]);
            questionABC.addSeries(segB, segmentFormat);
        }
        if (answerC[j] > 0) {
            Segment segC = new Segment("C = " + answerC[j], answerC[j]);
            questionABC.addSeries(segC, segmentFormat);
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
