package com.presentech.handsup;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.presentech.handsup.presentationfile.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewFeedbackMessages extends ListActivity  {

    ArrayList<String> messageList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    int j = 0;                      //current slide displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback_messages);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,messageList);
        setListAdapter(adapter);

        displayMessages();
    }


    public void displayMessages() {
        //get list of objects from database
        feedbackDatabaseHandler database = new feedbackDatabaseHandler(getApplicationContext(), "TestFeedbackList", null, 1, "FP");
        List<SingleFeedback> singleFeedbackList = database.getAllFeedback();

        //convert list of feedback objects to object array
        SingleFeedback[] feedbackArray = new SingleFeedback[singleFeedbackList.size()];
        singleFeedbackList.toArray(feedbackArray);


        //get number of slides
        double slide = feedbackArray[0].getSLIDE();

        for (int i = 0; i < feedbackArray.length; i++) {
            if (feedbackArray[i].getSLIDE() > slide){
                slide = feedbackArray[i].getSLIDE();
            }
        }

        int sNo = (int) slide;

        for (int i = 0; i < feedbackArray.length; i++) {
            if ((int)feedbackArray[i].getSLIDE() == j + 1) {
                if (feedbackArray[i].getTEXT() != null) {
                    String convertedTime = timeConvert(feedbackArray[i].getTIME_RECEIVED());
                    messageList.add(feedbackArray[i].getTEXT()+ "\n" + convertedTime);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        //set button visibilities depending on question number
        if (j == 0) {
            findViewById(R.id.slidedown).setVisibility(View.GONE);
        }
        else
            findViewById(R.id.slidedown).setVisibility(View.VISIBLE);

        if (j == sNo-1) {
            findViewById(R.id.slideup).setVisibility(View.GONE);
        }
        else
            findViewById(R.id.slideup).setVisibility(View.VISIBLE);

        TextView messageTitle = (TextView) findViewById(R.id.messageTitle);

        messageTitle.setText("Messages From Slide Number: " + (j+1));

    }

    public void slideUp (View v){
        messageList.clear();
        j++;
        displayMessages();
        adapter.notifyDataSetChanged();
    }

    public void slideDown (View v){
        messageList.clear();
        j--;
        displayMessages();
        adapter.notifyDataSetChanged();
    }

    public String timeConvert(long time_recieved) {
        Date dt = new Date(time_recieved);
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss");
        String time = fmt.format(dt);

        return time;
    }

}
