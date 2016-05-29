package com.presentech.handsup;

/*Created by Jack Bardsley on 18/05/16*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.util.List;


public class ReviewFeedback extends Activity {

    public int qNo;         //Number of questions
    public int sNo;          //Number of slides
    int j = 0;              //currently displayed question answers
    int h = 0;              //currently displayed slide responses
    public PieChart questionABC;
    //feedbackDatabaseHandler database;
    List<SingleFeedback> singleFeedbackList;

    /*public List<SingleFeedback> getDatabase(){
        feedbackDatabaseHandler database = new feedbackDatabaseHandler(getApplicationContext(), "TestFeedbackList", null, 1, "FP");
        List<SingleFeedback> singleFeedbackList = database.getAllFeedback();

        return singleFeedbackList;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback);
        //getDatabase();
        getPiePlots();

    }


    public void getPiePlots() {

        //get list of objects from database
        feedbackDatabaseHandler database = new feedbackDatabaseHandler(getApplicationContext(), "TestFeedbackList", null, 1, "FP");
        List<SingleFeedback> singleFeedbackList = database.getAllFeedback();

        //convert list of feedback objects to object array
        SingleFeedback[] feedbackArray = new SingleFeedback[singleFeedbackList.size()];
        singleFeedbackList.toArray(feedbackArray);

        //get number of questions
        qNo = feedbackArray[0].getQUESTION();

        for (int i = 0; i < feedbackArray.length; i++) {
            if (feedbackArray[i].getQUESTION() > qNo){
                qNo = feedbackArray[i].getQUESTION();
            }
        }

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
