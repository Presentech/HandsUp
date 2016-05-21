package com.presentech.handsup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.presentech.handsup.R;

import java.util.Random;

public class pieChartFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;
    public int a, b, c, good, bad, meh;
    private ViewGroup.LayoutParams PieChartParams;
    public int screenWidth, screenHeight;
    View DummyView;
    LinearLayout pieLayout;    // Root layout for fragment
    ViewGroup viewParent;
    public PieChart pieChartView; // PieChart View within
    SegmentFormatter segmentFormat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("ABCD","Here");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pieChartView = (PieChart) inflater.inflate(R.layout.fragment_pie_chart, container, false);
        //init_bar();
        return pieChartView;
    }
    @Override
     public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("ABCD", "InitialisePieChart");
        initPieChart();
    }

    public void initPieChart(){
        //Create references to views
        //pieChartView = (PieChart) pieLayout.findViewById(R.id.PiePlotFRAG);
        //viewParent = pieLayout;
        //Get Paramater values for each bar section
        PieChartParams = pieChartView.getLayoutParams();

        //Calculate the width of the bar
        double pieHeightDouble = 0.3*screenHeight;

        //Set width to all views
        PieChartParams.height = (int) pieHeightDouble;
        plotPitChart();
    }

    public void setFeedbackArray(SingleFeedback[] feedback){
        Random r = new Random();
        int rV;
        feedbackArray = feedback;
        /*Set some example ojects that would be expected to recieve*/
        for(int i=0; i<10;i++){
            rV = r.nextInt(4 - 1) + 1;
            feedbackArray[i] = new SingleFeedback("abc",1.00,1,1,rV,rV,"abc",123L);
        }
    }

    public void setScreenParams(int height, int width){
        screenHeight = height;
        screenWidth = width;
    }

    public void plotPitChart(){
        setFeedbackArray(feedbackArray);
        getQuestionNumber();
        calculateQuestionResponse();
        QuestionResponsePlot();
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

        //PieChart = (PieChart) pieLayout.findViewById(R.id.PiePlotFRAG);

        segmentFormat = new SegmentFormatter();
        segmentFormat.configure(getActivity().getApplicationContext(), R.xml.segmentformat);

        int currentQuestion = 0;
        pieChartView.setTitle("Understanding Response to Slide " + Integer.toString(currentQuestion+1));

        if (good > 0){
            Segment segA = new Segment("Good", good);
            pieChartView.addSeries(segA, segmentFormat);
        }
        if (meh > 0){
            Segment segB = new Segment("Meh", meh);
            pieChartView.addSeries(segB, segmentFormat);
        }
        if (bad > 0){
            Segment segC = new Segment("Bad", bad);
            pieChartView.addSeries(segC, segmentFormat);
        }
    }

    public void QuestionResponsePlot(){

        int currentQuestion = 0;

        segmentFormat = new SegmentFormatter();

        //segmentFormat.configure(, R.xml.segmentformat);
        pieChartView.setTitle("Answers to question number " + Integer.toString(currentQuestion+1));

        if (answerA[currentQuestion] > 0){
            Segment segA = new Segment("A", answerA[currentQuestion]);
            pieChartView.addSeries(segA, segmentFormat);
        }
        if (answerB[currentQuestion] > 0){
            Segment segB = new Segment("B", answerB[currentQuestion]);
            pieChartView.addSeries(segB, segmentFormat);
        }
        if (answerC[currentQuestion] > 0){
            Segment segC = new Segment("C", answerC[currentQuestion]);
            pieChartView.addSeries(segC, segmentFormat);
        }

    }
}
