package com.presentech.handsup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.View;

import com.presentech.handsup.presentationfile.Polygon;
import com.presentech.handsup.presentationfile.Shape;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noor on 09/03/2016.
 */
public class GraphicsHandler extends View {

    ArrayList x;
    ArrayList y;

    LinearGradient shade;
    Paint paint;
    Paint stroke;

    int screenWidth;
    int screenHeight;

    float left;
    float right;
    float bottom;
    float top;

    Polygon polygon;
    Shape shape;
    RectF rect;


    public GraphicsHandler(Context context, Polygon polygon, Shape shape, int screenWidth, int screenHeight) {
        super(context);
        this.polygon = polygon;
        this.shape = shape;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        setDrawingParameters();
        setColors();
    }


    public void onDraw(Canvas canvas) {

        if (shape !=  null) {
            if (shape.getType().equals("circle") || shape.getType().equals("oval")){
                canvas.drawOval(rect, paint);
                if (shape.getLineColour() != null) {
                    canvas.drawOval(rect, stroke);
                }
            }else if (shape.getType().equals("rectangle")){
                canvas.drawRect(rect, paint);
                if (shape.getLineColour() != null) {
                    canvas.drawRect(rect, stroke);
                }
            }else if (shape.getType().equals("triangle")){
                Log.d("TAG shape handler", "it's a triangle!");
            }
        }
//
//        paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.parseColor("#" + polygon.lineColour));

//        Path polyPath = new Path();
//        polyPath.moveTo( Integer.parseInt(x.toArray()[0].toString()), Integer.parseInt(x.toArray()[0].toString())); // used for first point
//        for (int i = 0; i < x.size(); i++) {
//            polyPath.lineTo(Integer.parseInt(x.toArray()[i].toString()), Integer.parseInt(x.toArray()[i].toString()));;
//        }
//        canvas.drawPath(polyPath, paint);

        Paint wallpaint = new Paint();
        wallpaint.setDither(true);
        wallpaint.setColor(Color.RED);
        wallpaint.setStyle(Paint.Style.STROKE);
        wallpaint.setStrokeWidth(50);
        Path wallpath = new Path();
        wallpath.moveTo(1, 1);
        wallpath.lineTo(1000, 1000);
        wallpath.close();
        Path path = new Path();
        path.moveTo(1000, 1);
        path.lineTo(1000, 1000);


    }


    public void setDrawingParameters(){

        if (shape != null) {
            left = shape.getxStart() * screenWidth;
            right = left + (shape.getWidth() * screenWidth);
            top = shape.getyStart() * screenHeight;
            bottom = top + (shape.getHeight() * screenHeight);
            rect = new RectF(left, top, right, bottom);
        }
    }

    public void setColors () {
        paint = new Paint();
        if (shape != null) {
            if (shape.getShading() != null) {
                shade = new LinearGradient(0, right, 0, bottom, Color.parseColor("#" + shape.getShading().getColour1()),
                        Color.parseColor("#" + shape.getShading().getColour2()), Shader.TileMode.MIRROR);
                paint.setShader(shade);
            } else {
                paint.setColor(Color.parseColor("#" + shape.getFillColour()));
            }

            paint.setStyle(Paint.Style.FILL);
            ;
            if (shape.getLineColour() != null) {
                stroke = new Paint();
                stroke.setColor(Color.parseColor("#" + shape.getLineColour()));
                stroke.setStyle(Paint.Style.STROKE);
                stroke.setStrokeWidth(5);
            }
        }
    }

}
