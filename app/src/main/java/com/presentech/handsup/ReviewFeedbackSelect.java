package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ReviewFeedbackSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback_select);
        setTitle("Select Feedback Type");
    }

    public  void doABC(View v){
        Intent ABCIntent = new Intent(this, ReviewFeedback.class);
        startActivity(ABCIntent);
    }

    public void doGMB(View v){
        Intent GMBIntent = new Intent(this, ReviewFeedbackGMB.class);
        startActivity(GMBIntent);
    }

    public void doMessages(View v){
        Intent MessageIntent = new Intent(this, ReviewFeedbackMessages.class);
        startActivity(MessageIntent);
    }

}
