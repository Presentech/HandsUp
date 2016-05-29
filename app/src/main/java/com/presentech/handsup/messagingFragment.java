package com.presentech.handsup;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class messagingFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    String[] messagesTemp = {"Woo Electronics!", "Boo Electronics!", "SwEng was so much fun OMFG xox", "See you in October!"};

    public int a, b, c, good, bad, meh;
    double A =1, B = 1, C = 1;
    double APercent, BPercent, CPercent, totalInputs, barHeight;
    ViewGroup viewParent;
    View AView, BView, CView, DummyView;
    private ViewGroup.LayoutParams AParams, BParams, CParams;
    public int screenWidth, screenHeight, lastBar;
    LinearLayout messagingLayout;
    ListView listView;
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
        messagingLayout = (LinearLayout) inflater.inflate(R.layout.fragment_messaging, container, false);

        Log.d("ABCD", "Hello");
        ArrayList<String> messageList = Getlist();
        listView = (ListView) messagingLayout.findViewById(R.id.messagingList);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(), R.layout.message_layout, messageList);
        listView.setAdapter(listAdapter);
        initMessaging();
        return messagingLayout;
    }

    private ArrayList<String> Getlist(){

        ArrayList<String> messageList = new ArrayList<>();
        messageList.add(messagesTemp[0]);
        messageList.add(messagesTemp[1]);
        messageList.add(messagesTemp[2]);
        messageList.add(messagesTemp[3]);
        return messageList;
    }

    public void updateMessages() {
    }
    private void initMessaging(){

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
}
