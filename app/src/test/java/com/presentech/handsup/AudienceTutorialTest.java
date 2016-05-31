package com.presentech.handsup;

import android.content.Intent;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.internal.Shadow;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Alex on 02/05/2016.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class AudienceTutorialTest {
    private AudienceTutorial activity;

    /* Method to be run before every test*/
    @Before
    public void setup() {
        /*Robolectric method to run our Activity */
        activity = Robolectric.setupActivity(AudienceTutorial.class);
    }

    /*Test to verify skip button */
    @Test
    public void checkSkipButton(){
        /* Simulate button press */
        activity.findViewById(R.id.skipButton).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        /*Check intent is not null*/
        assertNotNull(intent);
    }

    @Test
    public void checkSkipButtonIntent() {
    /* Simulate our button being clicked */
        activity.findViewById(R.id.skipButton).performClick();

    /* Create the Intent that we would expect */
        Intent expectedIntent = new Intent(activity, MainActivity.class);

    /*Capture the Intent created by MyActivity by using Roboelectric Shadows*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

    /*Check Intent is as expected */
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }


    /* Test to verify back button */
    @Test
    public void checkBackButton() {
        /* Simulate button press */
        activity.findViewById(R.id.backButton).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        View view = shadowActivity.findViewById(android.R.id.content);

        //assertEquals(R.id.content, shadowActivity.getContentView().getId());

        /*Check intent is not null*/
        assertNotNull(view);

    }



    /*Test to verify next button */
    @Test
    public void checkNextButton(){
        /* Simulate button press */
        activity.findViewById(R.id.nextButton).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        View view = shadowActivity.findViewById(android.R.id.content);

        /*Check intent is not null*/
        assertNotNull(view);
    }

    /*Test to verify done button */
    @Test
    public void checkDoneButton() {
        /* Simulate button press */
        activity.findViewById(R.id.doneButton).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        /*Check intent is not null*/
        assertNotNull(intent);
    }
    @Test
    public void checkDoneButtonIntent() {
    /* Simulate our button being clicked */
        activity.findViewById(R.id.doneButton).performClick();

    /* Create the Intent that we would expect */
        Intent expectedIntent = new Intent(activity, AudienceSessionSelect.class);

    /*Capture the Intent created by MyActivity by using Roboelectric Shadows*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

    /*Check Intent is as expected */
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

}
