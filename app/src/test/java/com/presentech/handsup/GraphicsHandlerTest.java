package com.presentech.handsup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Alex on 27/05/2016.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class GraphicsHandlerTest {
    TestActivity activity;
    GraphicsHandler gh, gh2, gh3;


    @Before
    public void setup() {
        /* Create the fake activity to hold the Graphics Handler View */
        activity = Robolectric.buildActivity(TestActivity.class).create().get();

        /* Retreive the Graphics Handler from the activity */
        gh = (GraphicsHandler) activity.slide.getChildAt(0);
        gh2 =(GraphicsHandler) activity.slide.getChildAt(1);
        gh3 =(GraphicsHandler) activity.slide.getChildAt(2);
    }

    /* Check that Rect object to be drawn is set with the correct parameters */
    @Test
    public void rectObjecthasCorrectParameters(){
        /* Calculate expected parameters from the activity test variables */
        float left = activity.xStart * activity.screenWidth;
        float right = left + (activity.width * activity.screenWidth);
        float top = activity.yStart * activity.screenHeight;
        float bottom = top + (activity.height * activity.screenHeight);
        RectF expectedRect = new RectF(left, top, right, bottom);

        assertEquals(expectedRect, gh.getRect());
    }

    /* Check that the correct colours are used */
    @Test
    public void correctColoursAreUsed (){
        Paint expectedFillColour = new Paint();
        Paint expectedLineColour = new Paint();

        /* Get Expected Colours from parameters of input Shape */
        expectedFillColour.setColor(Color.parseColor("#" + activity.fillColour));
        expectedLineColour.setColor(Color.parseColor("#" + activity.lineColour));

        /* Set other expected parameters */
        expectedFillColour.setStyle(Paint.Style.FILL);
        expectedLineColour.setStyle(Paint.Style.STROKE);
        expectedLineColour.setStrokeWidth(5);

        assertEquals(expectedFillColour.getColor(), gh.getPaint().getColor());
        assertEquals(expectedLineColour.getColor(), gh.getStroke().getColor());
        assertEquals(expectedFillColour.getStyle(), gh.getPaint().getStyle());
        assertEquals(expectedLineColour.getStyle(), gh.getStroke().getStyle());
        assertEquals(expectedLineColour.getStrokeWidth(), gh.getStroke().getStrokeWidth());
    }
    @Test
    /* Check Default colours are used when parameters are null */
    public void checkDefaultColoursAreUsedWhenParametersAreNull() {
        Paint expectedFillColour = new Paint();
        Paint expectedLineColour = new Paint();

        /* Expected colours are the presentation defaults */
        expectedFillColour.setColor(Color.parseColor("#" + activity.defaults.getFillColour()));
        expectedLineColour.setColor(Color.parseColor("#" +activity.defaults.getLineColour()));

        assertEquals(expectedFillColour.getColor(), gh2.getPaint().getColor());
        assertEquals(expectedLineColour.getColor(), gh2.getStroke().getColor());
    }

    @Test
    /* Check that shading is used when parameter not null */
    public void checkShadingValuesAreCorrect() {
        assertNotNull(gh3.getShade());
        assertEquals(gh3.getPaint().getShader(), gh3.getShade());
    }

    @Test
    /* Check that correct draw function is called for each shape type */
    public void correctDrawFunctionUsedForEachType() {
        GraphicsHandler gh = new GraphicsHandler(activity, null, activity.shape, activity.screenWidth, activity.screenHeight, activity.defaults);
        Canvas canvas = Mockito.mock(Canvas.class);
        gh.draw(canvas);
        verify(canvas,times(1)).drawRect(gh.getRect(), gh.getPaint());
    }


//    TestActivity activity;
//
//    public GraphicsHandlerTest() {
//        super(TestActivity.class);
//    }
//
//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//        Context context = getInstrumentation().getTargetContext();
//        Intent intent = new Intent(context, TestActivity.class);
//        startActivity(intent,null, null);
//        activity = getActivity();
//    }
//
//    @Test
//    public void testSomething() {
//        assertEquals(0, activity.slide.getChildAt(0));
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//    }
}
