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

    public void skipClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void nextClicked(View view) {
        TextView tutorialText = (TextView) findViewById(R.id.audienceText);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button skipButton = (Button) findViewById(R.id.skipButton);
        Button doneButton = (Button) findViewById(R.id.doneButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        tutorialText.setText("The button has done a thing");
        nextButton.setVisibility(View.GONE);
        skipButton.setVisibility(View.GONE);
        doneButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
    }

    public void doneClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backClicked(View view) {
        setContentView(R.layout.activity_audience_tutorial);
    }

}
