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

import java.util.ArrayList;
import java.util.Random;

public class stackedBarsFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[20];
    public int a, b, c, good, bad, meh;
    double A = 1, B = 1, C = 1;
    double APercent, BPercent, CPercent, totalInputs;
    ViewGroup viewParent;
    View AView, BView, CView;
    TextView textView, ATextView, BTextView, CTextView;

    public int barWidth, lastBar, barHeight, i = 0, k = 0, j, l;
    RelativeLayout barLayout;
    public int qNo = 0, numberofPlots = 2;
    public int[] answerA, answerB, answerC;
    ArrayList<SingleFeedback>  questionResponses = new ArrayList<>();
    ArrayList<SingleFeedback>  understandingResponses = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        barLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_stacked_bars, container, false);
        Log.d("ABCD", "HELLO");
        initBar();
        return barLayout;
    }

    public void reset(String bar){
        a = 0;
        b = 0;
        c = 0;
        updateBarHeight(bar);
        ViewGroup.LayoutParams AParams = AView.getLayoutParams();
        ViewGroup.LayoutParams BParams = BView.getLayoutParams();
        ViewGroup.LayoutParams CParams = CView.getLayoutParams();

        AParams.width = 0;
        BParams.width = 0;
        CParams.width = 0;
    }

    public void setFeedbackResponse(SingleFeedback feedback){
        SingleFeedback tempObject;
        int tempIndex = 0; // -1 = new answer no change, 1/2/3 = new answer, 0 new feedback
        //Add User ID
        if (i>99) i = 0; //If looking above 100 start overwriting from oldest first.
        if (k>99) k = 0; // i is question response index k is understanding response index

        int ABC = feedback.getABC();
        int GOODmehBAD = feedback.getGOOD_MEH_BAD();
        int understanding_old_value = 0;
        int ABC_old_value = 0;
        if (feedback.getTEXT() != null) return;
        if (ABC != -1){ // If question Response
            for (j=0; j < questionResponses.size(); j++){//Look through each object in array
                tempObject = questionResponses.get(j);
                if (tempObject.getUUID().equals(feedback.getUUID())){//If same user again!
                    if (tempObject.getABC() != ABC){// If new Answer
                        ABC_old_value = tempObject.getABC();
                        tempIndex = ABC;
                    }
                    else tempIndex = -1;
                    break;
                }
            }
        }
        else if (GOODmehBAD != -1){ // If understanding Response
            for (l=0; l < understandingResponses.size(); l++){//Look through each object in array
                tempObject = understandingResponses.get(l);
                if (tempObject.getUUID().equals(feedback.getUUID())){ //If same user again!
                    if (tempObject.getGOOD_MEH_BAD() != GOODmehBAD) {// If new Answer
                        understanding_old_value = tempObject.getGOOD_MEH_BAD();
                        tempIndex = GOODmehBAD;
                    }
                    else tempIndex = -1;
                    break;
                }

            }
        }

        if (tempIndex == 0 ){ //New Feedback is the same
            if (ABC != -1){ // Question Response
                if (ABC == 1) a++;
                else if (ABC == 2) b++;
                else c++;
                questionResponses.add(i, feedback);
                i++;
            }
            else if (GOODmehBAD != -1){ //Understanding Response
                if (GOODmehBAD == 1) good++;
                else if (GOODmehBAD == 2) meh++;
                else bad++;
                understandingResponses.add(k, feedback);
                k++;
            }
        }
        else if ((tempIndex > 0 ) && (tempIndex<4)){ //New Feedback is different
            if (ABC != -1){
                if (ABC_old_value == 1) a--; //Remove old Value
                else if (ABC_old_value == 2) b--;
                else if (ABC_old_value == 3) c--;

                if (tempIndex == 1) a++; //Add new value
                else if (tempIndex == 2) b++;
                else if (tempIndex == 3) c++;
                questionResponses.set(j, feedback);
            }
            else{
                if (understanding_old_value == 1) good--; //Remove old Value
                else if (understanding_old_value == 2) meh--;
                else if (understanding_old_value == 3) bad--;

                if (tempIndex == 1) good++;
                else if (tempIndex == 2) meh++;
                else if (tempIndex == 3) bad++;
                understandingResponses.set(l,feedback);
            }

        }
    }


    public void setScreenParams(int height, int width){
        barHeight = height;
        barWidth = width-350;
        Log.d("1234","h="+barHeight+"w="+barWidth);
    }

    private void initBar(){
        //Create references to views
        //AView = barLayout.findViewById(R.id.greenLayoutfrag);
        BView = barLayout.findViewById(R.id.yellowLayoutfrag);
        CView = barLayout.findViewById(R.id.redLayoutfrag);
        textView = (TextView) barLayout.findViewById(R.id.FBtextView);
        AView = barLayout.findViewById(R.id.greenLayoutfrag);
        //BTextView = (TextView) BView;
        //CTextView = (TextView) CView;
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
        if (barName.equals("Level of Understanding")){ //Use good meh bad values
            totalInputs = good+meh+bad;
            APercent = good/totalInputs;
            BPercent = meh/totalInputs;
            CPercent = bad/totalInputs;
        }
        if (barName.equals("Question Response")){ //Use good meh bad values
            totalInputs = a+b+c;
            APercent = a/totalInputs;
            BPercent = b/totalInputs;
            CPercent = c/totalInputs;
        }

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
    }
}
