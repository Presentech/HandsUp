package com.presentech.handsup;

import android.content.Intent;
import android.test.TouchUtils;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import static org.junit.Assert.*;


/**
 * Created by Lewis on 07/03/2016.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class LoginScreenActivityTest {

    private LoginScreenActivity activity;
    public EditText emailAddress;
    public EditText password;


    /* Method to be run before every test*/
    @Before
    public void setup() {
        /*Robolectric method to run our Activity */
        activity = Robolectric.setupActivity(LoginScreenActivity.class);

    }

    @Test
    public void checkUserDBReader(){
        assertEquals("presentech", activity.storedEmail);
        assertEquals("handsup", activity.storedPassword);
    }

}

