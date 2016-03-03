package com.presentech.handsup;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;


@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)

public class PresentationModeTutorialTest {

    private PresentationModeTutorial activity;

    @Before
    public void setup(){
        activity = Robolectric.setupActivity(PresentationModeTutorial.class);
    }

    @Test
    public void checkIntent(){
        /* Simulate button press */
        activity.findViewById(R.id.skip_button).performClick();

        /*Capture intent*/
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        assertNotNull(intent);

    }

    @Test
    public void checkToggleOpen(){

        activity.findViewById(R.id.load_content).setVisibility(View.GONE);

        /*Simulate pressing title to expand tutorial content*/
        activity.findViewById(R.id.load_title).performClick();

        /*Assert content is visible*/
        assertEquals(activity.findViewById(R.id.load_content).VISIBLE, activity.findViewById(R.id.load_content).getVisibility());

    }

    @Test
    public void checkToggleClose(){
        activity.findViewById(R.id.load_content).setVisibility(View.VISIBLE);

        /*Simulate pressing title to close content*/
        activity.findViewById(R.id.load_title).performClick();


        /*Assert content is not visible*/
        assertEquals(activity.findViewById(R.id.load_content).GONE, activity.findViewById(R.id.load_content).getVisibility());
    }
}