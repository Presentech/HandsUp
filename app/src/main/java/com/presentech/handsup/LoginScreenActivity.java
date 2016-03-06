package com.presentech.handsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginScreenActivity extends AppCompatActivity {
    public final static String LOGIN_MESSAGE = "com.presentech.handsup.LOGIN_MESSAGE";
    public final static String REGISTRATION_MESSAGE = "com.presentech.handsup.REGISTRATION_MESSAGE";
    public final static int maxloginAttempts = 5;
    public EditText emailAddress;
    public EditText password;
    public Button button_sign_in;
    public String storedEmail;
    public String storedPassword;
    public int loginAttempts = 0;
    public int loginAttemptsRemaining = 5;
    public boolean loginEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginSetup();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*Called when the user clicks the Send button*/
    public void loginAttempt(View view) {
        if (loginEnabled) {
            if ((emailAddress.getText().toString().equals(storedEmail) &&
                    (password.getText().toString().equals(storedPassword)))) {

                Intent submitLoginDetails = new Intent(this, HostingWizardActivity.class);
                emailAddress = (EditText) findViewById(R.id.email_addressET);
                String loginMessage = "Login Successful";
                //submitLoginDetails.putExtra(LOGIN_MESSAGE, loginMessage);
                Toast.makeText(getBaseContext(), loginMessage, Toast.LENGTH_LONG).show();
                startActivity(submitLoginDetails);
                //Clear text field after sending text
                emailAddress.getText().clear();
                password.getText().clear();
            }
            else {
                if (loginAttempts < maxloginAttempts) {
                    Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    loginAttempts++;
                    loginAttemptsRemaining = maxloginAttempts - loginAttempts;
                    Toast.makeText(getBaseContext(), "Attempts Remaining: " +
                            Integer.toString(loginAttemptsRemaining), Toast.LENGTH_LONG).show();
                }
                if (loginAttemptsRemaining == 0) {
                    loginEnabled = false;
                    Toast.makeText(getBaseContext(), "Login Unavailable", Toast.LENGTH_LONG).show();
                }
            }
        }

        else    {
            Toast.makeText(getBaseContext(), "Login Unavailable", Toast.LENGTH_LONG).show();
        }
    }

    public void loginSetup() {
        emailAddress = (EditText) findViewById(R.id.email_addressET);
        password = (EditText) findViewById(R.id.passwordET);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);

        //reading text from file
        storedEmail = "";
        storedPassword = "";

        try {
            InputStream inputStream = getAssets().open("userDB.txt");

            if (inputStream != null) {

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String readEmail = "";
                String readPassword = "";
                StringBuilder emailBuilder = new StringBuilder();
                StringBuilder passwordBuilder = new StringBuilder();

                readEmail = bufferedReader.readLine();
                emailBuilder.append(readEmail);
                readPassword = bufferedReader.readLine();
                passwordBuilder.append(readPassword);

                inputStream.close();

                storedEmail = emailBuilder.toString();
                storedPassword = passwordBuilder.toString();
            }
        }

        catch(FileNotFoundException e) {
            Log.e("login activity", "User Database File not found: " + e.toString());
        }

        catch (IOException e) {
            Log.e("login activity", "Can not read User Database file: " + e.toString());
        }
    }

}