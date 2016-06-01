package com.presentech.handsup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/*
 * Created by Lewis on 17/04/2016.
 */

//This activity is is used to handle user logins for Presenter Mode
public class LoginScreenActivity extends AppCompatActivity {
    //Maximum number of incorrect login attempts before lockout occurs
    private final static int maxloginAttempts = 5;
    public static boolean loginCompleted = false;
    public static boolean loginDetailsSaved = false;
    public static boolean saveLoginDetails = false;
    public EditText emailAddress;
    public EditText password;
    public Button button_sign_in;
    public CheckBox saveCredentials;
    public String storedEmail;
    public String storedPassword;
    public String lockoutState;
    public Intent submitLoginDetails;
    public Intent showHome;
    private int loginAttempts = 0;
    private int loginAttemptsRemaining = 5;
    private Toast currentToast;
    private final static String TAG = "DatabaseHandler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginSetup();
        //Create new database handler
        UserDBHandler dbHandler = new UserDBHandler(this, null, null, 1,
                getFilesDir().getAbsolutePath());

        //Check if userDB file exists on device
        if (dbHandler.checkUserDB()) {
        }

        //Copy UserDB file from assets folder to app databases folder if not found
        if (!dbHandler.checkUserDB()) {
            try {
                dbHandler.copyDataBase();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                currentToast("User database not copied to correct location");
            }
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Start Hosting Wizard if login details have been saved due to previous login
        if (loginDetailsSaved) {
            startActivity(submitLoginDetails);
        }
    }

    //Method for setup of login screen variables
    public void loginSetup() {
        emailAddress = (EditText) findViewById(R.id.email_addressET);
        password = (EditText) findViewById(R.id.passwordET);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        saveCredentials = (CheckBox) findViewById(R.id.saveLoginDetails);
        submitLoginDetails = new Intent(this, HostingWizardActivity.class);
        showHome = new Intent(this, ModeSelectActivity.class);

        //Create databases folder if one does not exist
        String databasesPath = "/data/data/com.presentech.handsup/" + File.separator + "databases/";
        File databasesDir = new File(databasesPath);
        databasesDir.mkdir();
    }

    //Toast handler to prevent toasts stacking
    void currentToast(String text) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    /*Called when the user clicks the login button*/
    public void loginAttempt(View view) {
        //Get user entered information from edit texts
        String enteredEmail = emailAddress.getText().toString();
        String enteredPassword = password.getText().toString();

        UserDBHandler dbHandler = new UserDBHandler(this, null, null, 1, null);
        User user = dbHandler.findUser(enteredEmail);
        if (!loginDetailsSaved) {
            //Completed when login has not been disabled due to too many incorrect login attempts
            //Check if username of password fields are blank
            if ((enteredEmail.equals("") || (enteredPassword.equals("")))) {
                currentToast("Details not entered");
            }
            //Check if entered email address is in a valid format
            else if (!isEmailValid(enteredEmail)) {
                currentToast("Please enter a valid E-mail Address");
            }
            //Search for entered user details in UserDB
            else {
                if (lookupUser(enteredEmail)) {
                    //Completed when user details have been entered and found in UserDB
                    //Read user lockout state from userDB
                    lockoutState = user.getLockout();

                    if (lockoutState.equals("1")) {
                        //User is locked out from presenter mode
                        //They are unable to login due to too many previous login attempts
                        //Lockout state must be reset in userDB by admin
                        currentToast("You are locked out, please contact your system " +
                                "administrator");
                    } else {
                        if ((enteredEmail.equals(storedEmail) &&
                                (enteredPassword.equals(storedPassword)))) {
                            //Clear text field after sending text
                            emailAddress.getText().clear();
                            password.getText().clear();

                            //Saving user credentials
                            SharedPreferences sharedPreferences = getSharedPreferences(HandsUpApplication.PREF_NAME,MODE_PRIVATE);
                            sharedPreferences.edit().putString(HandsUpApplication.PREF_USERNAME, storedEmail).commit();
                            sharedPreferences.edit().putString(HandsUpApplication.PREF_PASSWORD, storedPassword).commit();

                            //Completed if Save Login Details check box is selected
                            if (saveLoginDetails) {
                                loginDetailsSaved = true;
                            }
                            loginCompleted = true;
                            String loginMessage = "Login Successful";
                            currentToast(loginMessage);
                            startActivity(submitLoginDetails);
                        }
                        //Completed when an incorrect login attempt has been made
                        else {
                            if (loginAttempts < maxloginAttempts) {
                                Toast.makeText(getBaseContext(), "Incorrect Details",
                                        Toast.LENGTH_SHORT).show();
                                loginAttempts++;
                                loginAttemptsRemaining = maxloginAttempts - loginAttempts;
                                //Show user number of login attempts remeining
                                Toast.makeText(getBaseContext(), "Attempts Remaining: " +
                                                Integer.toString(loginAttemptsRemaining),
                                        Toast.LENGTH_SHORT).show();
                            }
                            //If too many login attempts have been completed disable login
                            //Lockout user and update lockout field for user in userDB
                            if (loginAttemptsRemaining == 0) {
                                lockoutState = "1";
                                //Update lockout state for user in userDB
                                dbHandler.updateUser(storedEmail, lockoutState);
                                currentToast("Login Unavailable, User Lockout Out");
                            }
                        }
                    }
                }
            }
        }
        else {
            startActivity(submitLoginDetails);
        }
    }

    //Toggle Save Login Details status using check box
    public void saveLoginDetails(View view)   {
        saveLoginDetails = !saveLoginDetails;
    }

    //Method to check email address validity
    private boolean isEmailValid(String enteredEmail) {
        return (enteredEmail.contains("@") && enteredEmail.contains("."));
    }

    /*
    Used to prevent login screen being shown to user when login has been completed and the login
    details have been saved. This occurs when user presses back button on hosting wizard screen
    */
    @Override
    public void onResume() {
        super.onResume();
        if (loginCompleted && saveLoginDetails) {
            startActivity(submitLoginDetails);
        }
    }

    //Method to add new user to UserDB...not currently used
    public void newUser(View view) {
        UserDBHandler dbHandler = new UserDBHandler(this, null, null, 1, null);
        User user = new User(emailAddress.getText().toString(), password.getText().toString(),
                "test name", "0");

        //Add user to UserDB using dbHandler
        dbHandler.addUser(user);
        //Clear email address and password fields
        emailAddress.setText("");
        password.setText("");
    }

    //Method to search for specified user in UserDB using email address
    public boolean lookupUser(String email) {
        UserDBHandler dbHandler = new UserDBHandler(this, null, null, 1, null);
        User user = dbHandler.findUser(email);
        if (user != null) {
            //Completed if User is found in DB
            //Store returned email address and password
            storedEmail = String.valueOf(user.getEmail());
            storedPassword = String.valueOf(user.getPassword());
            lockoutState = String.valueOf(user.getLockout());
            return true;
        } else {
            //Completed when User is not found in DB
            currentToast("User Not Found");
            return false;
        }
    }

    //Method to search for and remove specified user from UserDB
    //Not currently used in app
    public void removeUser(View view) {
        UserDBHandler dbHandler = new UserDBHandler(this, null, null, 1, null);
        boolean result = dbHandler.deleteUser(emailAddress.getText().toString());
        if (result) {
            currentToast("User Removed from Database");
            //Clear email address and password fields
            emailAddress.setText("");
            password.setText("");
        } else {
            //Completed when User is not found in DB
            currentToast("User Not Found");
        }
    }

    //Method to lockout user from app after too many login attempts
    public void lockoutUser(String email) {
        UserDBHandler dbHandler = new UserDBHandler(this, null, null, 1, null);
        User user = dbHandler.findUser(email);
        if (user != null) {
            //Completed if User is found in DB
            user.setLockout("1");
        } else {
            //Completed when User is not found in DB
            currentToast("User Not Found, unable to complete lockout");
        }
    }
}