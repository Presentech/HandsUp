package com.presentech.handsup;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.presentech.handsup.R;

public class pieChartFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    public PieChart understandingPlot, ABCPlot;
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;
    public int a, b, c, good, bad, meh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pie_chart, container, false);
    }

    public void calculateUnderstandingResponse(){
        good = 0;
        bad = 0;
        meh = 0;
            for (int i = 0; i < feedbackArray.length; i++){

                if (feedbackArray[i].getGOOD_MEH_BAD() == 1) {
                    good++;
                }
                if (feedbackArray[i].getGOOD_MEH_BAD() == 3) {
                    bad++;
                }
                if (feedbackArray[i].getGOOD_MEH_BAD() == 2) {
                    meh++;
                }
                else if (feedbackArray[i].getGOOD_MEH_BAD() == -1) {
                    return;
                }
            }

    }

    public void calculateQuestionResponse(){
        answerA = new int[qNo];           /*Initialise array for A answers for questions*/
        answerB = new int[qNo];           /*Initialise array for B answers for questions*/
        answerC = new int[qNo];           /*Initialise array for C answers for questions*/

        a = 0;
        b = 0;
        c = 0;

        /*iterate through questions*/
        for (int i = 0; i < qNo; i++) {
            /*iterate through objects to find whether value is A, B or C*/
            for (int k = 0; k < feedbackArray.length; k++) {
                while (feedbackArray[k].getQUESTION() == i+1) {
                    if (feedbackArray[k].getABC() == 1) {
                        a++;
                        answerA[i] = a;
                        break;
                        /*answerA[i] = answerA[i] + 1;*/
                    }
                    if (feedbackArray[k].getABC() == 2) {
                        b++;
                        answerB[i] = b;
                        break;
                       /* answerB[i] = answerB[i] + 1;*/
                    }if (feedbackArray[k].getABC() == 3) {
                        c++;
                        answerC[i] = c;
                        break;
                        /*answerC[i] = answerC[i] + 1;*/
                    }
                }
            }
        }

    }

    public void setFeedbackArray(SingleFeedback[] feedback){

        feedbackArray = feedback;
        /*Set some example objects that would be expected to recieve*/
        feedbackArray[0] = new SingleFeedback("abc",1.00,1,1,2,2,"abc",123L);
        feedbackArray[1] = new SingleFeedback("abc",1.00,1,1,2,1,"abc",123L);
        feedbackArray[2] = new SingleFeedback("abc",1.00,1,1,1,2,"abc",123L);
        feedbackArray[3] = new SingleFeedback("abc",1.00,1,1,1,3,"abc",123L);
        feedbackArray[4] = new SingleFeedback("abc",1.00,1,1,3,1,"abc",123L);
        feedbackArray[5] = new SingleFeedback("abc",1.00,1,1,2,2,"abc",123L);
        feedbackArray[6] = new SingleFeedback("abc",1.00,1,1,2,3,"abc",123L);
        feedbackArray[7] = new SingleFeedback("abc",1.00,1,1,1,2,"abc",123L);
        feedbackArray[8] = new SingleFeedback("abc",1.00,1,1,1,3,"abc",123L);
        feedbackArray[9] = new SingleFeedback("abc",1.00,1,1,3,1,"abc",123L);
    }


    /*Get Number of questions*/
    public void getQuestionNumber() {
        for (int i = 0; i < feedbackArray.length; i++) {
            qNo = feedbackArray[i].getQUESTION();
        }
    }
    /*Get good/meh/bad values*/
    public void understandingResponsePlot() {
        int good = 0;
        int meh = 0;
        int bad = 0;
            /*iterate through objects to find whether value is good, meh or bad*/
        for (int k = 0; k < feedbackArray.length; k++){

            if (feedbackArray[k].getGOOD_MEH_BAD() == 1) {
                good++;
            }
            if (feedbackArray[k].getGOOD_MEH_BAD() == 3) {
                bad++;
            }
            if (feedbackArray[k].getGOOD_MEH_BAD() == 2) {
                meh++;
            }
            else if (feedbackArray[k].getGOOD_MEH_BAD() == -1) {
                return;
            }
        }

        understandingPlot = (PieChart) getView().findViewById(R.id.PiePlotFRAG);

        SegmentFormatter segmentFormat = new SegmentFormatter();
        segmentFormat.configure(getActivity().getApplicationContext(), R.xml.segmentformat);


        int currentQuestion = 0;
        understandingPlot.setTitle("Understanding Response to Slide " + Integer.toString(currentQuestion+1));

        if (good > 0){
            Segment segA = new Segment("Good", good);
            understandingPlot.addSeries(segA, segmentFormat);
        }
        if (meh > 0){
            Segment segB = new Segment("Meh", meh);
            understandingPlot.addSeries(segB, segmentFormat);
        }
        if (bad > 0){
            Segment segC = new Segment("Bad", bad);
            understandingPlot.addSeries(segC, segmentFormat);
        }
    }

    public void QuestionResponsePlot(){

        ABCPlot = (PieChart) getView().findViewById(R.id.PiePlotFRAG);

        SegmentFormatter segmentFormat = new SegmentFormatter();
        segmentFormat.configure(getActivity().getApplicationContext(), R.xml.segmentformat);


        int currentQuestion = 0;
        ABCPlot.setTitle("Answers to question number " + Integer.toString(currentQuestion+1));

        if (answerA[currentQuestion] > 0){
            Segment segA = new Segment("A", answerA[currentQuestion]);
            ABCPlot.addSeries(segA, segmentFormat);
        }
        if (answerB[currentQuestion] > 0){
            Segment segB = new Segment("B", answerB[currentQuestion]);
            ABCPlot.addSeries(segB, segmentFormat);
        }
        if (answerC[currentQuestion] > 0){
            Segment segC = new Segment("C", answerC[currentQuestion]);
            ABCPlot.addSeries(segC, segmentFormat);
        }

    }
}
