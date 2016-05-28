package com.presentech.handsup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;

import org.junit.Test;


/**
 * Created by Alex on 27/05/2016.
 */
public class InstGraphicsHandlerTest extends ActivityInstrumentationTestCase2<TestActivity> {

    TestActivity activity;
    GraphicsHandler gh, gh2, gh3;

    public InstGraphicsHandlerTest() {
        super(TestActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        /* Retreive the Graphics Handler from the activity */
        gh = (GraphicsHandler) activity.slide.getChildAt(0);
        gh2 =(GraphicsHandler) activity.slide.getChildAt(1);
        gh3 =(GraphicsHandler) activity.slide.getChildAt(2);
    }

    @Test
    /* Check that correct draw function is called for each shape type */
    public void correctDrawFunctionUsedForEachType() {
        GraphicsHandler gh = new GraphicsHandler(activity, null, activity.shape, activity.screenWidth, activity.screenHeight, activity.defaults);
        Canvas canvas = Mockito.mock(Canvas.class);
        gh.draw(canvas);
        verify(canvas,times(1)).drawRect(gh.getRect(), gh.getPaint());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}

