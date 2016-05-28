package com.presentech.handsup;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.presentech.handsup.R;

import java.util.Random;

public class stackedBarsFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    public int a, b, c, good, bad, meh;
    double A = 1, B = 1, C = 1;
    double APercent, BPercent, CPercent, totalInputs;
    ViewGroup viewParent;
    View DummyView, AView, BView, CView;
    TextView textView;

    public int barWidth, lastBar, barHeight;
    RelativeLayout barLayout;
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        barLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_stacked_bars, container, false);
        Log.d("ABCD", "HELLO");
        initBar();
        return barLayout;
    }

    public void setFeedbackArray(SingleFeedback[] feedback){
        Random r = new Random();
        int rV;
        feedbackArray = feedback;
        /*Set some example objects that would be expected to recieve*/
        for(int i=0; i<10;i++){
            rV = r.nextInt(4 - 1) + 1;
            feedbackArray[i] = new SingleFeedback("abc",1.00,1,1,rV,rV,"abc",123L);
        }
    }

    public void setScreenParams(int height, int width){
        barHeight = height;
        barWidth = width-350;
        Log.d("1234","h="+barHeight+"w="+barWidth);
    }

    private void initBar(){
        //Create references to views
        AView = barLayout.findViewById(R.id.greenLayoutfrag);
        BView = barLayout.findViewById(R.id.yellowLayoutfrag);
        CView = barLayout.findViewById(R.id.redLayoutfrag);
        textView = (TextView) barLayout.findViewById(R.id.FBtextView);
        viewParent = barLayout;

        updateBarHeight(" ");
    }
    public void setName(String name){
        textView.setText(name);
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

    public void updateBarHeight(String barName){
        Log.d("ABCD", "UBH");
        //Calculate the percent of the bar filled by each section
        totalInputs = a+b+c;
        APercent = a/totalInputs;
        BPercent = b/totalInputs;
        CPercent = c/totalInputs;

        //Calculate the height of each bar section
        double AHeightDouble = APercent * barWidth;
        int AWidth = (int) AHeightDouble;
        double BHeightDouble = BPercent * barWidth;
        int BWidth = (int) BHeightDouble;
        double CHeightDouble = CPercent * barWidth;
        int CWidth = (int) CHeightDouble;


        ViewGroup.LayoutParams AParams = AView.getLayoutParams();
        ViewGroup.LayoutParams BParams = BView.getLayoutParams();
        ViewGroup.LayoutParams CParams = CView.getLayoutParams();
        ViewGroup.LayoutParams tvParams = textView.getLayoutParams();

        AParams.height = barHeight;
        AParams.width = AWidth;
        BParams.height = barHeight;
        BParams.width = BWidth;
        CParams.height = barHeight;
        CParams.width = CWidth;
        tvParams.height = barHeight;
        tvParams.width = 300;

        AView.setLayoutParams(AParams);
        BView.setLayoutParams(BParams);
        CView.setLayoutParams(CParams);
        textView.setLayoutParams(tvParams);
        textView.setText(barName);

        Log.d("1234","" + barWidth);
        Log.d("1234", "" + a);
        Log.d("1234", "" + b);
        Log.d("1234", "" + c);
    }
}
