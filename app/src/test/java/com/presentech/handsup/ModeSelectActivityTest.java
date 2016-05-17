package com.presentech.handsup;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;

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
 * Created by *Jack Bardsley* on *17/05/16*.
 */

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)

public class ModeSelectActivityTest {
    private ModeSelectActivity activity;

    /* Method to be run before every test*/
    @Before
    public void setup() {

/*Robolectric method to run our Activity */
        activity = Robolectric.setupActivity(ModeSelectActivity.class);
    }

    @Test
    public void checkAudienceSelect(){
        /*Simulate pressing title to expand tutorial content*/
        activity.findViewById(R.id.AudienceSelect).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        /*Create expected intent*/
        Intent expectedIntent = new Intent(activity, AudienceSessionSelect.class);

        /*Check correct next activity selected*/
        assertEquals(expectedIntent, intent);
    }

    @Test
    public void checkPresenterSelect(){
        /*Simulate pressing title to expand tutorial content*/
        activity.findViewById(R.id.PresenterSelect).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        /*Create expected intent*/
        Intent expectedIntent = new Intent(activity, LoginScreenActivity.class);

        /*Check correct next activity selected*/
        assertEquals(expectedIntent, intent);
    }

}