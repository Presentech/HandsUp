package com.presentech.handsup;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created by Lewis on 17/05/2016.
 */
@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {
    private UserDBHandler dbHandler;

    @Before
    public void setup(){
        dbHandler = new UserDBHandler(this, null, null, 1, getFilesDir().getAbsolutePath());
    }


}
