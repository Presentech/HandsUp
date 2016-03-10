package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get intent and assign it to a local variable
        Intent intent = getIntent();
        //Get string from Activity: MyActivity
        String message = intent.getStringExtra(LoginScreenActivity.LOGIN_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(message);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content_login_success);
        layout.addView(textView);
    }

}
