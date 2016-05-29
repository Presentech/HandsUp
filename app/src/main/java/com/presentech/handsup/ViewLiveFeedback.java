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
    public int a, b, c;
    stackedBarsFragment ABCBarsFragment;
    pieChartFragment PieChartFragment;
    public int screenWidth, screenHeight;
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
        if (findViewById(R.id.fragmentContainerFBPieABC) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            PieChartFragment = new pieChartFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerFBPieABC, PieChartFragment).commit();
        }
        //Get Screen Size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        updateBarValues();
        updatePieValues();
    }

    public void updatePieValues(){
        //Draw these and set random feedback values
        PieChartFragment.setFeedbackArray(feedbackArray);

        PieChartFragment.setScreenParams(screenHeight, screenWidth);

        //newFragment.calculateQuestionResponse();

            }

    public void updateBarValues(){
        //Create new fragments to replace the old ones
        stackedBarsFragment newFragment = new stackedBarsFragment();

        //Draw these and set random feedback values
        newFragment.setFeedbackArray(feedbackArray);

        newFragment.setScreenParams(screenHeight, screenWidth);
        newFragment.getQuestionNumber();
        newFragment.calculateQuestionResponse();

        //Replace the fragment currently in the fragment container with the new fragments
        if(newFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBBarABC, newFragment).commit();
        }

    }

    public void updateValues(View view){
        //Create new fragments to replace the old ones
        stackedBarsFragment newFragment = new stackedBarsFragment();
        pieChartFragment newPieFragment = new pieChartFragment();

        //Draw these and set random feedback values
        newFragment.setFeedbackArray(feedbackArray);
        newFragment.setFeedbackArray(feedbackArray);

        newFragment.setScreenParams(screenHeight, screenWidth);
        newFragment.getQuestionNumber();
        newFragment.calculateQuestionResponse();

        newPieFragment.updatePlot();

        //Replace the fragment currently in the fragment container with the new fragments
        if(newFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBBarABC, newFragment).commit();
        }

    }
}
