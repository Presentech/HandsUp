package com.presentech.handsup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.test.TouchUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.io.File;

import static org.junit.Assert.*;


/**
 * Created by Lewis on 07/03/2016.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class LoginScreenActivityTest {

    private LoginScreenActivity activity;
    private EditText email;
    private EditText password;


    /* Method to be run before every test*/
    @Before
    public void setup() {
        /*Robolectric method to run our Activity */
        activity = Robolectric.setupActivity(LoginScreenActivity.class);
        email = (EditText) activity.findViewById(R.id.email_addressET);
        password = (EditText) activity.findViewById(R.id.passwordET);
    }

    @Test
    public void loginTest() {
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        email.setText("admin@presentech.com", TextView.BufferType.EDITABLE);
        password.setText("handsup", TextView.BufferType.EDITABLE);
        activity.button_sign_in.performClick();

        assertNotNull(intent);
    }
}

