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

public class UserTest {


    /* Method to be run before every test*/
    @Before
    public void setup() {
    }

    //Test User Setters and Getters
    @Test
    public void UserSettersGettersTest() {
        User user = new User();
        user.setID(1);
        user.setEmail("email");
        user.setPassword("password");
        user.setName("name");
        user.setLockout("lock");

        assertEquals(1, user.getID());
        assertEquals("email", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("name", user.getName());
        assertEquals("lock", user.getLockout());
    }
}

