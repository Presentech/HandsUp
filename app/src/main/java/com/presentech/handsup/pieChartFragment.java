package com.presentech.handsup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.util.Random;

public class pieChartFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    public int a, b, c, good, bad, meh;
    double A =1, B = 1, C = 1;
    double APercent, BPercent, CPercent, totalInputs;
    ViewGroup viewParent;
    private ViewGroup.LayoutParams pieParams;
    public int screenWidth, screenHeight;
    LinearLayout pieLayout;
    PieChart pieChart;
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;
    SegmentFormatter segmentFormat;
    public Segment segA, segB, segC;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("ABCD", "CreateSB");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pieLayout = (LinearLayout) inflater.inflate(R.layout.fragment_pie_chart, container, false);
        initPieChart();
        return pieLayout;
    }

    public void initPieChart(){
        pieChart = (PieChart) pieLayout.findViewById(R.id.piePlotFrag);
        pieParams = pieLayout.getLayoutParams();
        //Calculate the height of the bar
        double pieSizeDouble = (0.8*screenHeight)/numberofPlots;
        int pieSize = (int) pieSizeDouble;
        //Set width to all views
        pieParams.height = pieSize;
        pieParams.width = pieSize;
        calculateQuestionResponse();
        QuestionResponsePlot();
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

    public void QuestionResponsePlot(){

        int currentQuestion = 0;
        //Log.d("ABCD", "QRP");
        segmentFormat = new SegmentFormatter();
        //ERROR HERE It accesses this method BEFORE initPieChart and I can't figure out WHY.
        //segmentFormat.configure(, R.xml.segmentformat);
        pieChart.setTitle("Answers to question number " + Integer.toString(currentQuestion+1));

        if (answerA[currentQuestion] > 0){
            segA = new Segment("A", answerA[currentQuestion]);
            pieChart.addSeries(segA, segmentFormat);
        }
        if (answerB[currentQuestion] > 0){
            segB = new Segment("B", answerB[currentQuestion]);
            pieChart.addSeries(segB, segmentFormat);
        }
        if (answerC[currentQuestion] > 0){
            segC = new Segment("C", answerC[currentQuestion]);
            pieChart.addSeries(segC, segmentFormat);
        }

    }


    /*Get Number of questions*/
    public void getQuestionNumber() {
        for (int i = 0; i < feedbackArray.length; i++) {
            qNo = feedbackArray[i].getQUESTION();
        }
    }

}