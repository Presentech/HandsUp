package com.presentech.handsup;

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
import android.widget.Button;
import android.widget.LinearLayout;

import com.presentech.handsup.R;

import java.util.Random;

public class stackedBarsFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    public int a, b, c, good, bad, meh;
    double A =1, B = 1, C = 1;
    double APercent, BPercent, CPercent, totalInputs, barHeight;
    View AView, BView, CView, DummyView;
    ViewGroup viewParent;
    private ViewGroup.LayoutParams AParams, BParams, CParams;
    public int screenWidth, screenHeight;
    LinearLayout barLayout;
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        barLayout = (LinearLayout) inflater.inflate(R.layout.fragment_stacked_bars, container, false);
        initBar();
        return barLayout;
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

    /*Get Number of questions*/
    public void getQuestionNumber() {
        for (int i = 0; i < feedbackArray.length; i++) {
            qNo = feedbackArray[i].getQUESTION();
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
    public void initBar(){
        //Create references to views
        AView = barLayout.findViewById(R.id.greenLayoutfrag);
        BView = barLayout.findViewById(R.id.yellowLayoutfrag);
        CView = barLayout.findViewById(R.id.redLayoutfrag);
        DummyView = barLayout.findViewById(R.id.dummyBar);
        viewParent = barLayout;

        //Get Paramater values for each bar section
        AParams = AView.getLayoutParams();
        BParams = BView.getLayoutParams();
        CParams = CView.getLayoutParams();
        setBar();
        updateBarHeight();

    }

    private void setBar(){
        //Calculate the width of the bar
        double barWidthDouble = 0.1*screenWidth;
        int barWidth = (int) barWidthDouble;
        //Calculate the height of the bar
        barHeight = 0.8*screenHeight;

        //Set width to all views
        AParams.width = barWidth;
        BParams.width = barWidth;
        CParams.width = barWidth;
    }
    public void updateBarHeight(){
        //Calculate the percent of the bar filled by each section
        totalInputs = a+b+c;
        APercent = a/totalInputs;
        BPercent = b/totalInputs;
        CPercent = c/totalInputs;

        //Calculate the height of each bar section
        double AHeightDouble = APercent * barHeight;
        int AHeight = (int) AHeightDouble;
        double BHeightDouble = BPercent * barHeight;
        int BHeight = (int) BHeightDouble;
        double CHeightDouble = CPercent * barHeight;
        int CHeight = (int) CHeightDouble;

        //Set Height
        AParams.height = AHeight;
        BParams.height = BHeight;
        CParams.height = CHeight;
    }

    public void updateBarHeight(View v){
        //Calculate the percent of the bar filled by each section
        totalInputs = A+B+C;
        APercent = A/totalInputs;
        BPercent = B/totalInputs;
        CPercent = C/totalInputs;

        //Calculate the height of each bar section
        double AHeightDouble = APercent * barHeight;
        int AHeight = (int) AHeightDouble;
        double BHeightDouble = BPercent * barHeight;
        int BHeight = (int) BHeightDouble;
        double CHeightDouble = CPercent * barHeight;
        int CHeight = (int) CHeightDouble;

        //Set Height
        AParams.height = AHeight;
        BParams.height = BHeight;
        CParams.height = CHeight;
        updateBars();
    }

    private void updateBars(){
        viewParent.removeView(DummyView);
        DummyView = new View(getActivity());
        viewParent.addView(DummyView, 4);
    }
}
