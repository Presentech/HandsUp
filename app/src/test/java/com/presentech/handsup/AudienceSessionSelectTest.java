package com.presentech.handsup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDrawable;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Alex on 02/05/2016.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class AudienceSessionSelectTest {
    private AudienceSessionSelect activity;
    int i;

    /* Method to be run before every test*/
    @Before
    public void setup() {
        /*Robolectric method to run our Activity */
        activity = Robolectric.setupActivity(AudienceSessionSelect.class);
    }
    /* Checks that Textview and connect button  visibility is toggled on every click */
    @Test
    public void textViewIsExpandedOnButtonClick() {

        /*Instantiate Buttons and TextViews */

        Button button1 = (Button) activity.findViewById(R.id.button1);
        Button button2 = (Button) activity.findViewById(R.id.button2);
        Button button3 = (Button) activity.findViewById(R.id.button3);
        Button button4 = (Button) activity.findViewById(R.id.button4);
        Button button5 = (Button) activity.findViewById(R.id.button5);

        Button connectButton1 = (Button) activity.findViewById(R.id.connectButton1);
        Button connectButton2 = (Button) activity.findViewById(R.id.connectButton2);
        Button connectButton3 = (Button) activity.findViewById(R.id.connectButton3);
        Button connectButton4 = (Button) activity.findViewById(R.id.connectButton4);
        Button connectButton5 = (Button) activity.findViewById(R.id.connectButton5);

        TextView textView1 = (TextView) activity.findViewById(R.id.textView1);
        TextView textView2 = (TextView) activity.findViewById(R.id.slideTitleTextView);
        TextView textView3 = (TextView) activity.findViewById(R.id.textView3);
        TextView textView4 = (TextView) activity.findViewById(R.id.textView4);
        TextView textView5 = (TextView) activity.findViewById(R.id.textView5);

        /* Check TextViews and connect buttons are initially hidden */
        assertEquals(View.GONE, textView1.getVisibility());
        assertEquals(View.GONE, textView2.getVisibility());
        assertEquals(View.GONE, textView3.getVisibility());
        assertEquals(View.GONE, textView4.getVisibility());
        assertEquals(View.GONE, textView5.getVisibility());
        assertEquals(View.GONE, connectButton1.getVisibility());
        assertEquals(View.GONE, connectButton2.getVisibility());
        assertEquals(View.GONE, connectButton3.getVisibility());
        assertEquals(View.GONE, connectButton4.getVisibility());
        assertEquals(View.GONE, connectButton5.getVisibility());


        /* Perform click of button 1 and check that textview and connect button 1 are expanded but all others
         * remain invisible */
        button1.performClick();
        assertEquals(View.VISIBLE, textView1.getVisibility());
        assertEquals(View.GONE, textView2.getVisibility());
        assertEquals(View.GONE, textView3.getVisibility());
        assertEquals(View.GONE, textView4.getVisibility());
        assertEquals(View.GONE, textView5.getVisibility());
        assertEquals(View.VISIBLE, connectButton1.getVisibility());
        assertEquals(View.GONE, connectButton2.getVisibility());
        assertEquals(View.GONE, connectButton3.getVisibility());
        assertEquals(View.GONE, connectButton4.getVisibility());
        assertEquals(View.GONE, connectButton5.getVisibility());

        /* Perform remaining clicks and check that all textviews and connect buttons are visible */
        button2.performClick();
        button3.performClick();
        button4.performClick();
        button5.performClick();
        assertEquals(View.VISIBLE, textView1.getVisibility());
        assertEquals(View.VISIBLE, textView2.getVisibility());
        assertEquals(View.VISIBLE, textView3.getVisibility());
        assertEquals(View.VISIBLE, textView4.getVisibility());
        assertEquals(View.VISIBLE, textView5.getVisibility());
        assertEquals(View.VISIBLE, connectButton1.getVisibility());
        assertEquals(View.VISIBLE, connectButton2.getVisibility());
        assertEquals(View.VISIBLE, connectButton3.getVisibility());
        assertEquals(View.VISIBLE, connectButton4.getVisibility());
        assertEquals(View.VISIBLE, connectButton5.getVisibility());

        /* Click the buttons again and check that they are all invisible */
        button1.performClick();
        button2.performClick();
        button3.performClick();
        button4.performClick();
        button5.performClick();
        assertEquals(View.GONE, textView1.getVisibility());
        assertEquals(View.GONE, textView2.getVisibility());
        assertEquals(View.GONE, textView3.getVisibility());
        assertEquals(View.GONE, textView4.getVisibility());
        assertEquals(View.GONE, textView5.getVisibility());
        assertEquals(View.GONE, connectButton1.getVisibility());
        assertEquals(View.GONE, connectButton2.getVisibility());
        assertEquals(View.GONE, connectButton3.getVisibility());
        assertEquals(View.GONE, connectButton4.getVisibility());
        assertEquals(View.GONE, connectButton5.getVisibility());
    }

    /*Check that arrow on button toggles between right facing and downward facing */
    @Test
    public void checkArrowTogglesOnClick(){
        /*Instantiate Buttons */

        Button button1 = (Button) activity.findViewById(R.id.button1);
        Button button2 = (Button) activity.findViewById(R.id.button2);
        Button button3 = (Button) activity.findViewById(R.id.button3);
        Button button4 = (Button) activity.findViewById(R.id.button4);
        Button button5 = (Button) activity.findViewById(R.id.button5);

        /* Get RIGHT drawables from buttons */
        Drawable[] drawable1 = button1.getCompoundDrawables();
        Drawable[] drawable2 = button2.getCompoundDrawables();
        Drawable[] drawable3 = button3.getCompoundDrawables();
        Drawable[] drawable4 = button4.getCompoundDrawables();
        Drawable[] drawable5 = button5.getCompoundDrawables();

        ShadowDrawable shadowDrawable1 = Shadows.shadowOf(drawable1[2]);
        ShadowDrawable shadowDrawable2 = Shadows.shadowOf(drawable2[2]);
        ShadowDrawable shadowDrawable3 = Shadows.shadowOf(drawable3[2]);
        ShadowDrawable shadowDrawable4 = Shadows.shadowOf(drawable4[2]);
        ShadowDrawable shadowDrawable5 = Shadows.shadowOf(drawable5[2]);

        /* Check arrow icon is initially right facing */
        assertEquals(R.mipmap.arrowright, shadowDrawable1.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable2.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable3.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable4.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable5.getCreatedFromResId());

        /* Click buttons and check arrows are down facing */
        button1.performClick();
        button2.performClick();
        button3.performClick();
        button4.performClick();
        button5.performClick();
        drawable1 = button1.getCompoundDrawables();
        drawable2 = button2.getCompoundDrawables();
        drawable3 = button3.getCompoundDrawables();
        drawable4 = button4.getCompoundDrawables();
        drawable5 = button5.getCompoundDrawables();
        shadowDrawable1 = Shadows.shadowOf(drawable1[2]);
        shadowDrawable2 = Shadows.shadowOf(drawable2[2]);
        shadowDrawable3 = Shadows.shadowOf(drawable3[2]);
        shadowDrawable4 = Shadows.shadowOf(drawable4[2]);
        shadowDrawable5 = Shadows.shadowOf(drawable5[2]);
        assertEquals(R.mipmap.arrowndown, shadowDrawable1.getCreatedFromResId());
        assertEquals(R.mipmap.arrowndown, shadowDrawable2.getCreatedFromResId());
        assertEquals(R.mipmap.arrowndown, shadowDrawable3.getCreatedFromResId());
        assertEquals(R.mipmap.arrowndown, shadowDrawable4.getCreatedFromResId());
        assertEquals(R.mipmap.arrowndown, shadowDrawable5.getCreatedFromResId());

        /* Click buttons and check arrows are right facing */
        button1.performClick();
        button2.performClick();
        button3.performClick();
        button4.performClick();
        button5.performClick();
        drawable1 = button1.getCompoundDrawables();
        drawable2 = button2.getCompoundDrawables();
        drawable3 = button3.getCompoundDrawables();
        drawable4 = button4.getCompoundDrawables();
        drawable5 = button5.getCompoundDrawables();
        shadowDrawable1 = Shadows.shadowOf(drawable1[2]);
        shadowDrawable2 = Shadows.shadowOf(drawable2[2]);
        shadowDrawable3 = Shadows.shadowOf(drawable3[2]);
        shadowDrawable4 = Shadows.shadowOf(drawable4[2]);
        shadowDrawable5 = Shadows.shadowOf(drawable5[2]);
        assertEquals(R.mipmap.arrowright, shadowDrawable1.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable2.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable3.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable4.getCreatedFromResId());
        assertEquals(R.mipmap.arrowright, shadowDrawable5.getCreatedFromResId());

    }

    /* Check that feedback activity is called when connect button 1 is pressed */
    @Test
    public void feedbackActivityCalledOnConnect1Click() {
        /* Press COnenct Button 1 */
        activity.findViewById(R.id.connectButton1).performClick();

        /*Check Expected intent is Called */
        Intent expectedIntent = new Intent(activity, FeedbackActivity.class);
        Intent actualIntent = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expectedIntent,actualIntent);
    }

    /* Check that feedback activity is called when connect button 2 is pressed */
    @Test
    public void feedbackActivityCalledOnConnect2Click() {
        /* Press COnenct Button 1 */
        activity.findViewById(R.id.connectButton2).performClick();

        /*Check Expected intent is Called */
        Intent expectedIntent = new Intent(activity, FeedbackActivity.class);
        Intent actualIntent = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expectedIntent,actualIntent);
    }

    /* Check that feedback activity is called when connect button 3 is pressed */
    @Test
    public void feedbackActivityCalledOnConnect3Click() {
        /* Press COnenct Button 1 */
        activity.findViewById(R.id.connectButton3).performClick();

        /*Check Expected intent is Called */
        Intent expectedIntent = new Intent(activity, FeedbackActivity.class);
        Intent actualIntent = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expectedIntent,actualIntent);
    }

    /* Check that feedback activity is called when connect button 4 is pressed */
    @Test
    public void feedbackActivityCalledOnConnect4Click() {
        /* Press COnenct Button 1 */
        activity.findViewById(R.id.connectButton4).performClick();

        /*Check Expected intent is Called */
        Intent expectedIntent = new Intent(activity, FeedbackActivity.class);
        Intent actualIntent = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expectedIntent,actualIntent);
    }

    /* Check that feedback activity is called when connect button 5 is pressed */
    @Test
    public void feedbackActivityCalledOnConnect5Click() {
        /* Press COnenct Button 1 */
        activity.findViewById(R.id.connectButton5).performClick();

        /*Check Expected intent is Called */
        Intent expectedIntent = new Intent(activity, FeedbackActivity.class);
        Intent actualIntent = Shadows.shadowOf(activity).getNextStartedActivity();

        assertEquals(expectedIntent,actualIntent);
    }

}