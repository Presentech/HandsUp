package com.presentech.handsup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import com.presentech.handsup.presentationfile.Shape;


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
    }


    /* Check that correct draw function is called for each shape type */
    public void testcorrectDrawFunctionUsedForEachType() {
        Shape circle = new Shape(0, -1, 0, 0, "circle", 0.5f, 0.5f, "FFFFFF", "000000", null);
        Shape rectangle = new Shape(0, -1, 0, 0, "rectangle", 0.5f, 0.5f, "FFFFFF", "000000", null);
        Shape roundedRect = new Shape(0, -1, 0, 0, "roundedRectangle", 0.5f, 0.5f, "FFFFFF", "000000", null);

        GraphicsHandler ghCircle = new GraphicsHandler(activity, null, circle, activity.screenWidth, activity.screenHeight, activity.defaults);
        GraphicsHandler ghRect = new GraphicsHandler(activity, null, rectangle, activity.screenWidth, activity.screenHeight, activity.defaults);
        GraphicsHandler ghRoundedRect = new GraphicsHandler(activity, null, roundedRect, activity.screenWidth, activity.screenHeight, activity.defaults);

        Canvas canvasCircle = Mockito.mock(Canvas.class);
        Canvas canvasRect = Mockito.mock(Canvas.class);
        Canvas canvasRoundedRect = Mockito.mock(Canvas.class);

        ghCircle.draw(canvasCircle);

        verify(canvasCircle,times(1)).drawOval(ghCircle.getRect(), ghCircle.getPaint());
        verify(canvasCircle,times(1)).drawOval(ghCircle.getRect(), ghCircle.getStroke());

        ghRect.draw(canvasRect);
        verify(canvasRect,times(1)).drawRect(ghRect.getRect(), ghRect.getPaint());
        verify(canvasRect,times(1)).drawRect(ghRect.getRect(), ghRect.getStroke());

        ghRoundedRect.draw(canvasRoundedRect);
        verify(canvasRoundedRect,times(1)).drawRoundRect(ghRoundedRect.getRect(), GraphicsHandler.ROUNDRECT_RADIUS, GraphicsHandler.ROUNDRECT_RADIUS, ghRoundedRect.getPaint());
        verify(canvasRoundedRect,times(1)).drawRoundRect(ghRoundedRect.getRect(), GraphicsHandler.ROUNDRECT_RADIUS, GraphicsHandler.ROUNDRECT_RADIUS, ghRoundedRect.getPaint());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}

