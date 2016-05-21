package com.presentech.handsup;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
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
    pieChartFragment ABCPieFragment, understandingPieFragment;
    public int screenWidth, screenHeight;
    @Override
    protected void onStart(){
        super.onStart();

        ABCPieFragment.setFeedbackArray(feedbackArray);
        //understandingPieFragment.setFeedbackArray(feedbackArray);
        ABCBarsFragment.setFeedbackArray(feedbackArray);


        ABCBarsFragment.setScreenParams(screenHeight, screenWidth);
        ABCBarsFragment.getQuestionNumber();
        ABCBarsFragment.calculateQuestionResponse();

        ABCPieFragment.setScreenParams(screenHeight, screenWidth);
        ABCPieFragment.getQuestionNumber();
        ABCPieFragment.calculateQuestionResponse();
        ABCPieFragment.QuestionResponsePlot();

        //understandingPieFragment.setScreenParams(screenHeight, screenWidth);
        //understandingPieFragment.calculateUnderstandingResponse();
        //understandingPieFragment.understandingResponsePlot();




        //ABCBarsFragment.initBar();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_live_feedback);
        if (findViewById(R.id.fragmentContainerFBBarABC) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ABCBarsFragment = new stackedBarsFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerFBBarABC, ABCBarsFragment).commit();
        }
        /*if (findViewById(R.id.fragmentContainerFBPieUnderstanding) != null) {
            if (savedInstanceState != null) return;
            // Create a new Fragment to be placed in the activity layout
            understandingPieFragment = new pieChartFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerFBPieUnderstanding, understandingPieFragment).commit();
        }*/
        if (findViewById(R.id.fragmentContainerFBPieABC) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            ABCPieFragment = new pieChartFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerFBPieABC, ABCPieFragment).commit();
        }

        //Get Screen Size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    public void updateValues(View view){
        // Create fragment and give it an argument specifying the article it should show
        ABCPieFragment.onDestroy();
        stackedBarsFragment newFragment = new stackedBarsFragment();
        pieChartFragment newFragment1 = new pieChartFragment();
        //pieChartFragment newFragment2 = new pieChartFragment();

        newFragment.setFeedbackArray(feedbackArray);
        //newFragment2.setFeedbackArray(feedbackArray);

        newFragment.setScreenParams(screenHeight, screenWidth);
        //newFragment.getQuestionNumber();
        //newFragment.calculateQuestionResponse();


        //newFragment1.plotPitChart();

        //newFragment2.calculateUnderstandingResponse();
        //newFragment2.understandingResponsePlot();

        if(newFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBBarABC, newFragment).commit();
        }
        if(newFragment1 != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBPieABC, newFragment1).commit();
        }
        //if(newFragment2 != null){
        //    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBPieUnderstanding, newFragment2).commit();
        //}

        newFragment1.setScreenParams(screenHeight, screenWidth);
        newFragment1.plotPitChart();

        Log.d("REMOVEFRAGMENT", "Removed");
    }
}
