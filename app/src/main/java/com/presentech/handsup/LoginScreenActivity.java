package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginScreenActivity extends AppCompatActivity {
    public final static String LOGIN_MESSAGE = "com.presentech.handsup.LOGIN_MESSAGE";
    public final static String REGISTRATION_MESSAGE = "com.presentech.handsup.REGISTRATION_MESSAGE";
    public final static int maxloginAttempts = 5;
    public EditText emailAddress;
    public EditText password;
    public Button button_sign_in;
    public CheckBox saveCredentials;
    public String storedEmail;
    public String storedPassword;
    private int loginAttempts = 0;
    private int loginAttemptsRemaining = 5;
    private static boolean loginEnabled = true;
    private static boolean loginCompleted = false;
    private static boolean saveLoginDetails = false;
    public Intent submitLoginDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginSetup();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (loginCompleted) {
            setContentView(R.layout.activity_hosting_wizard);
            startActivity(submitLoginDetails);
        }
    }

    public void loginSetup() {
        emailAddress = (EditText) findViewById(R.id.email_addressET);
        password = (EditText) findViewById(R.id.passwordET);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        saveCredentials = (CheckBox) findViewById(R.id.saveLoginDetails);
        submitLoginDetails = new Intent(this, HostingWizardActivity.class);

        //Read stored usernames and passwords from userDB text file
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

    /*Called when the user clicks the Login button*/
    public void loginAttempt(View view) {
        if (!loginCompleted) {
            if (loginEnabled) {
                if ((emailAddress.getText().toString().equals(storedEmail) &&
                        (password.getText().toString().equals(storedPassword)))) {

                    emailAddress = (EditText) findViewById(R.id.email_addressET);
                    String loginMessage = "Login Successful";
                    //Clear text field after sending text
                    emailAddress.getText().clear();
                    password.getText().clear();
                    if (saveLoginDetails)   {
                        loginCompleted = true;
                    }
                    Toast.makeText(getBaseContext(), loginMessage, Toast.LENGTH_SHORT).show();
                    startActivity(submitLoginDetails);

                } else {
                    if (loginAttempts < maxloginAttempts) {
                        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        loginAttempts++;
                        loginAttemptsRemaining = maxloginAttempts - loginAttempts;
                        Toast.makeText(getBaseContext(), "Attempts Remaining: " +
                                Integer.toString(loginAttemptsRemaining), Toast.LENGTH_SHORT).show();
                    }
                    if (loginAttemptsRemaining == 0) {
                        loginEnabled = false;
                        Toast.makeText(getBaseContext(), "Login Unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getBaseContext(), "Login Unavailable", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            startActivity(submitLoginDetails);
        }
    }

    public void saveLoginDetails(View view)   {
        saveLoginDetails = !saveLoginDetails;
    }

}