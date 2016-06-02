package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AudienceTutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_tutorial);

    }

    //this is called when 'Skip' is clicked, returns to specified activity
    public void skipClicked(View view){
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    //this is called when 'Next' is clicked, loads layout 2 (to be changed to a fragment)
    public void nextClicked(View view) {
        setContentView(R.layout.activity_audience_tutorial2);
    }

    //this is called when the 'Done' button is clicked, returns to specified activity
    public void doneClicked(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    //this is called when the 'Back' button is clicked, returns to layout 1
    public void backClicked(View view) {
        setContentView(R.layout.activity_audience_tutorial);
    }

}
