package com.presentech.handsup;
import static android.support.test.InstrumentationRegistry.getTargetContext;

import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Lewis
 */

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    LoginScreenActivity activity;
    private EditText email;
    private EditText password;

    @Before
    public void setUp() {
        activity = new LoginScreenActivity();
        email = (EditText) activity.findViewById(R.id.email_addressET);
        password = (EditText) activity.findViewById(R.id.passwordET);
    }


    @After
    public void tearDown() throws Exception {
    }

    //Method to test adding user to UserDB
    @Test
    public void testAddingUser(){
        email.setText("admin@presentech.com", TextView.BufferType.EDITABLE);
        password.setText("handsup", TextView.BufferType.EDITABLE);
        activity.button_sign_in.performClick();
    }

}