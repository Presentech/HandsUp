package com.presentech.handsup;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lewis on 26/05/2016.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class mainActivityTest {
    private MainActivity activity;

    /* Method to be run before every test*/
    @Before
    public void setup() {
        /*Robolectric method to run Activity */
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    /* Method to test intent created when Go To Mode Select Button Pressed*/
    @Test
    public void checkIntentCreated(){
        /* Simulate button press */
        activity.findViewById(R.id.ModeSelectButton).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        /*Check intent is not null*/
        assertNotNull(intent);
    }

    /* Method to test created intent content when Go To Mode Select Button Pressed*/
    @Test
    public void checkIntentContent() {
        /* Simulate mode select button being pressed */
        activity.findViewById(R.id.ModeSelectButton).performClick();
        /* Create the expected Intent */
        Intent expectedIntent = new Intent(activity, ModeSelectActivity.class);
        /*Capture the Intent created by MainActivity*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        Intent actualIntent = shadowActivity.getNextStartedActivity();

        /*Check Intent is as expected */
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}
