package com.presentech.handsup;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.presentech.handsup.presentationfile.Polygon;
import com.presentech.handsup.presentationfile.Shading;
import com.presentech.handsup.presentationfile.Shape;

public class TestActivity extends Activity {
    Shape shape;
    Polygon polygon;

    /*Test Variables */
    int startTime = 0;
    int duration = -1;
    float xStart = 0.5f;
    float yStart = 0.4f;
    String type = "square";
    float width = 0.25f;
    float height = 0.125f;
    String lineColour = "000000";
    String fillColour = "FFFFFF";
    Shading shading = null;
    Canvas canvas = new Canvas();
    RelativeLayout slide = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        shape = new Shape(startTime, duration, xStart, yStart, type, width, height, lineColour, fillColour, shading);

        GraphicsHandler gh = new GraphicsHandler(this,null, shape,800, 450);

        gh.draw(canvas);

        slide.addView(gh);
    }
}
