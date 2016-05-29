package com.presentech.handsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReviewFeedbackSelect extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_feedback_select);
    }

    public  void doABC(View v){
        Intent ABCIntent = new Intent(this, ReviewFeedback.class);
        startActivity(ABCIntent);
    }

    public void doGMB(View v){
        Intent GMBIntent = new Intent(this, ReviewFeedbackGMB.class);
        startActivity(GMBIntent);
    }
}
