package com.presentech.handsup;

/*Created by Jack*/

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;


@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)

public class HostingWizardActivityTest {

    private HostingWizardActivity activity;

    @Before
    public void setup(){
        activity = Robolectric.setupActivity(HostingWizardActivity.class);
    }

    @Test
    public void checkCheckboxes(){
        /*Simulate pressing all check buttons*/
        activity.findViewById(R.id.HWcheckbox1).performClick();
        activity.findViewById(R.id.HWcheckbox2).performClick();
        activity.findViewById(R.id.HWcheckbox3).performClick();
        activity.findViewById(R.id.HWcheckbox4).performClick();
        activity.findViewById(R.id.HWcheckbox5).performClick();

        /*Retrieve Boolean parameters*/
        boolean understanding = activity.understanding;
        boolean multiChoice = activity.multiChoice;
        boolean messaging = activity.messaging;
        boolean feedbackPerSlide = activity.feedbackPerSlide;
        boolean hideFeedback = activity.hideFeedback;

        /*Assert paramters are correct*/
        assertEquals(true, understanding);
        assertEquals(true, multiChoice);
        assertEquals(true, messaging);
        assertEquals(true, feedbackPerSlide);
        assertEquals(true, hideFeedback);

    }


}