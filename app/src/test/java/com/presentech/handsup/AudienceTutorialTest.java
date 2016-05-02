package com.presentech.handsup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

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

    /*Test to verify next button */

    /* Test to verify back button */

    /*Test to verify skip button */
}
