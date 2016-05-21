package com.presentech.handsup;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;


public class ViewLiveFeedback extends FragmentActivity {


    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    public PieChart understandingPlot, ABCPlot;
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;
    public int a, b, c;
    stackedBarsFragment ABCBarsFragment;
    public int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_live_feedback);

        pieChartFragment ABCPieFragment = (pieChartFragment) getSupportFragmentManager().findFragmentById(R.id.ABCPieChart);
        pieChartFragment understandingPieFragment = (pieChartFragment) getSupportFragmentManager().findFragmentById(R.id.understandingPieChart);
        ABCBarsFragment = (stackedBarsFragment) getSupportFragmentManager().findFragmentById(R.id.ABCstackedBars);

        ABCPieFragment.setFeedbackArray(feedbackArray);
        understandingPieFragment.setFeedbackArray(feedbackArray);
        ABCBarsFragment.setFeedbackArray(feedbackArray);

        ABCPieFragment.getQuestionNumber();
        ABCPieFragment.calculateQuestionResponse();
        ABCPieFragment.QuestionResponsePlot();

        understandingPieFragment.calculateUnderstandingResponse();
        understandingPieFragment.understandingResponsePlot();


        //Get Screen Size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        ABCBarsFragment.setScreenParams(screenHeight, screenWidth);
        ABCBarsFragment.getQuestionNumber();
        ABCBarsFragment.calculateQuestionResponse();
        ABCBarsFragment.initBar();


    }

    public void updateValues(View view){
        ABCBarsFragment.setFeedbackArray(feedbackArray);
        ABCBarsFragment.updateBarHeight();
    }
    public void setFeedbackArray(SingleFeedback[] feedback){

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
}
