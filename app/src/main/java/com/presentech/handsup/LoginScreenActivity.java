package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class LoginScreenActivity extends AppCompatActivity {
    public final static String LOGIN_MESSAGE = "com.presentech.handsup.LOGIN_MESSAGE";
    public final static String REGISTRATION_MESSAGE = "com.presentech.handsup.REGISTRATION_MESSAGE";
    public EditText emailAddress;
    public EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginSetup();
    }

    /*Called when the user clicks the Send button*/
    public void login(View view) {
        if ((emailAddress.getText().toString().equals("test") &&
                (password.getText().toString().equals("handsup")))) {

            Intent submitLoginDetails = new Intent(this, LoginSuccessActivity.class);
            emailAddress = (EditText) findViewById(R.id.email_addressET);
            String loginMessage = "Login Successful";
            submitLoginDetails.putExtra(LOGIN_MESSAGE, loginMessage);
            startActivity(submitLoginDetails);
            //Clear text field after sending text
            emailAddress.getText().clear();
            password.getText().clear();
        }

        else {
            Intent submitLoginDetails = new Intent(this, LoginFailedActivity.class);
            emailAddress = (EditText) findViewById(R.id.email_addressET);
            String loginMessage = "Login Failed";
            submitLoginDetails.putExtra(LOGIN_MESSAGE, loginMessage);
            startActivity(submitLoginDetails);
            //Clear text field after sending text
            emailAddress.getText().clear();
            password.getText().clear();

        }
    }

        public void registration(View view) {
            EditText editText = (EditText) findViewById(R.id.passwordET);
            String messageRegistration = "Registration is currently unavailable";
            Intent intentPresentation = new Intent(this, RegistrationActivity.class);

            intentPresentation.putExtra(REGISTRATION_MESSAGE, messageRegistration);
            startActivity(intentPresentation);
            //Clear text field after sending text
            editText.getText().clear();
            password.getText().clear();

        }

        public void loginSetup() {
            emailAddress = (EditText) findViewById(R.id.email_addressET);
            password = (EditText) findViewById(R.id.passwordET);
        }
    }

