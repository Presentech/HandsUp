package com.presentech.handsup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lewis on 26/05/2016.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class aboutActivityTest {
    private aboutActivity activity;

    /* Method to be run before every test*/
    @Before
    public void setup() {
        /*Robolectric method to run About Activity */
        activity = Robolectric.setupActivity(aboutActivity.class);
    }

    @Test
    public void checkListVisibility(){
        /*Check list view with activity content is visible*/
        assertEquals(activity.findViewById(R.id.fileListView).VISIBLE,
                activity.findViewById(R.id.fileListView).getVisibility());
    }
}
