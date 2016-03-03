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
    public Button button_register;
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
    }

    /*Called when the user clicks the Send button*/
    public void loginAttempt(View view) {
        if (loginEnabled == true) {
            if ((emailAddress.getText().toString().equals(storedEmail) &&
                    (password.getText().toString().equals(storedPassword)))) {

                Intent submitLoginDetails = new Intent(this, LoginSuccessActivity.class);
                emailAddress = (EditText) findViewById(R.id.email_addressET);
                String loginMessage = "Login Successful";
                submitLoginDetails.putExtra(LOGIN_MESSAGE, loginMessage);
                startActivity(submitLoginDetails);
                //Clear text field after sending text
                emailAddress.getText().clear();
                password.getText().clear();
            } else {
            /*
            Intent submitLoginDetails = new Intent(this, LoginFailedActivity.class);
            emailAddress = (EditText) findViewById(R.id.email_addressET);
            String loginMessage = "Login Failed";
            submitLoginDetails.putExtra(LOGIN_MESSAGE, loginMessage);
            startActivity(submitLoginDetails);
            //Clear text field after sending text
            emailAddress.getText().clear();
            password.getText().clear();
            */
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

    public void registration(View view) {
        /*EditText editText = (EditText) findViewById(R.id.passwordET);
        String messageRegistration = "Registration is currently unavailable";
        Intent intentPresentation = new Intent(this, RegistrationActivity.class);

        intentPresentation.putExtra(REGISTRATION_MESSAGE, messageRegistration);
        startActivity(intentPresentation);
        //Clear text field after sending text
        editText.getText().clear();
        password.getText().clear();
        */

        Toast.makeText(getBaseContext(),"Registration is currently unavailable", Toast.LENGTH_LONG).show();
    }

    public void loginSetup() {
        emailAddress = (EditText) findViewById(R.id.email_addressET);
        password = (EditText) findViewById(R.id.passwordET);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        button_register = (Button) findViewById(R.id.button_register);

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

        catch(IOException e){
            Log.e("login activity", "Can not read User Database file: " + e.toString());
        }
    }

    public void writeToFile()   {
        try {
            FileOutputStream fileout=openFileOutput("userDB.txt", MODE_PRIVATE);
            Toast.makeText(getBaseContext(), LoginScreenActivity.this.getFilesDir().getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write("Email:" + emailAddress.getText().toString());
            outputWriter.write("\n");
            outputWriter.write("Password:" + password.getText().toString());
            outputWriter.write("\n");
            outputWriter.close();
            emailAddress.getText().clear();
            password.getText().clear();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}