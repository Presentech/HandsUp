package com.presentech.handsup;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.presentech.handsup.presentationfile.Defaults;
import com.presentech.handsup.presentationfile.Polygon;
import com.presentech.handsup.presentationfile.Shading;
import com.presentech.handsup.presentationfile.Shape;

public class TestActivity extends Activity {
    Shape shape, shape2, shape3;
    Polygon polygon;
    float[] polyPath = {0.4f,0.1f,0.6f,0.1f,0.65f,0.2f,0.5f,0.9f,0.35f,0.2f,0.4f,0.1f};

    /*Test Variables */
    int startTime = 0;
    int duration = -1;
    float xStart = 0.5f;
    float yStart = 0.4f;
    String type = "rectangle";
    float width = 0.25f;
    float height = 0.125f;
    String lineColour = "000000";
    String fillColour = "FFFFFF";
    Shading shading = new Shading(0.3f,0.7f,0.5f, 0.8f, "BE7391", "5C81A4");
    Canvas canvas = new Canvas();
    RelativeLayout slide = null;

    int screenWidth = 800;
    int screenHeight = 450;

    /* Online interested in Default Fille and Line Colour */
    Defaults defaults = new Defaults(null, null, 0, null, "FE5432", "A649BD");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        shape = new Shape(startTime, duration, xStart, yStart, type, width, height, lineColour, fillColour, null);
        shape2 = new Shape(startTime, duration, xStart, yStart, type, width, height, null, null, null);
        shape3 = new Shape(startTime, duration, xStart, yStart, type, width, height, null, null, shading);

        polygon = new Polygon(startTime, duration,lineColour,fillColour, null, "diamond.csv");

        GraphicsHandler gh = new GraphicsHandler(this,null, shape, screenWidth, screenHeight, defaults);
        GraphicsHandler gh2 = new GraphicsHandler(this,null, shape2 ,screenWidth, screenHeight, defaults);
        GraphicsHandler gh3 = new GraphicsHandler(this,null, shape3 ,screenWidth, screenHeight, defaults);

        GraphicsHandler gh4 = new GraphicsHandler(this,polygon, null ,screenWidth, screenHeight, defaults);


        gh.draw(canvas);
        gh2.draw(canvas);
        gh3.draw(canvas);
        gh4.draw(canvas);

        slide = new RelativeLayout(this);
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        slide.setLayoutParams(llp);

        slide.addView(gh, 0);
        slide.addView(gh2, 1);
        slide.addView(gh3, 2);
        slide.addView(gh4, 3);
    }
}
