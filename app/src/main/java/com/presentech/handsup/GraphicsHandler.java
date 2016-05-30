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

import com.presentech.handsup.presentationfile.Defaults;
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

    public final static int ROUNDRECT_RADIUS = 10;


    ArrayList x;
    ArrayList y;

    LinearGradient shade;
    Paint paint;
    Paint stroke;

    int screenWidth;
    int screenHeight;

    float rleft;
    float rright;
    float rbottom;
    float rtop;

    Polygon polygon;
    Shape shape;
    Defaults defaults;
    RectF rect;
    Path path;
    Context context;


    public GraphicsHandler(Context context, Polygon polygon, Shape shape, int screenWidth, int screenHeight, Defaults defaults) {
        super(context);
        this.context = context;
        this.polygon = polygon;
        this.shape = shape;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.defaults = defaults;

        setDrawingParameters();
        setColors();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (shape !=  null) {
            if (shape.getType().equals("circle") || shape.getType().equals("oval")){
                canvas.drawOval(rect, paint);
                canvas.drawOval(rect, stroke);
            }else if (shape.getType().equals("rectangle")){
                canvas.drawRect(rect, paint);
                canvas.drawRect(rect, stroke);
            }else if (shape.getType().equals("roundedRectangle")){
                canvas.drawRoundRect(rect, ROUNDRECT_RADIUS, ROUNDRECT_RADIUS, paint);
                canvas.drawRoundRect(rect, ROUNDRECT_RADIUS, ROUNDRECT_RADIUS, stroke);
            }
        } else {
            canvas.drawPath(path, paint);
            canvas.drawPath(path, stroke);
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
//
//        Paint wallpaint = new Paint();
//        wallpaint.setDither(true);
//        wallpaint.setColor(Color.RED);
//        wallpaint.setStyle(Paint.Style.STROKE);
//        wallpaint.setStrokeWidth(50);
//        Path wallpath = new Path();
//        wallpath.moveTo(1, 1);
//        wallpath.lineTo(1000, 1000);
//        wallpath.close();
//        Path path = new Path();
//        path.moveTo(1000, 1);
//        path.lineTo(1000, 1000);



    }


    public void setDrawingParameters(){

        if (shape != null) {
            rleft = shape.getxStart() * screenWidth;
            rright = rleft + (shape.getWidth() * screenWidth);
            rtop = shape.getyStart() * screenHeight;
            rbottom = rtop + (shape.getHeight() * screenHeight);
            rect = new RectF(rleft, rtop, rright, rbottom);
        } else {

            String next[] = {};
            try {
                CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open(polygon.getSourceFile())));
                while (true) {
                    next = reader.readNext();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            float[] polyPath = new float[next.length];
            for (int i = 0; i<next.length; i++) {
                float f = Float.parseFloat(next[i]);
                polyPath[i] = f;
            }
            /* Move to first coords */
            path = new Path();
            path.moveTo(polyPath[0]*screenWidth, polyPath[1]*screenHeight);

            for (int i=2; i<polyPath.length-1; i = i+2) {
                path.lineTo(polyPath[i]*screenWidth, polyPath[i+1]*screenHeight);
            }
            path.close();

        }
    }

    public void setColors () {
        paint = new Paint();
        stroke = new Paint();
        if (shape != null) {
            if (shape.getShading() != null) {
                shade = new LinearGradient(shape.getShading().getX1(), shape.getShading().getY1(),
                        shape.getShading().getX2(), shape.getShading().getY2(), Color.parseColor("#"
                        + shape.getShading().getColour1()), Color.parseColor("#" +
                        shape.getShading().getColour2()), Shader.TileMode.MIRROR);
                paint.setShader(shade);
            } else {
                if (shape.getFillColour() != null) {
                    paint.setColor(Color.parseColor("#" + shape.getFillColour()));
                }else {
                    paint.setColor(Color.parseColor("#" + defaults.getFillColour()));
                }
            }

            paint.setStyle(Paint.Style.FILL);

            if (shape.getLineColour() != null) {
                stroke.setColor(Color.parseColor("#" + shape.getLineColour()));
            } else {
                stroke.setColor(Color.parseColor("#" + defaults.getLineColour()));
            }
            stroke.setStyle(Paint.Style.STROKE);
            stroke.setStrokeWidth(5);
        }else {
            if (polygon.getShading() != null) {
                shade = new LinearGradient(polygon.getShading().getX1(), polygon.getShading().getY1(),
                        polygon.getShading().getX2(), polygon.getShading().getY2(), Color.parseColor("#"
                        + polygon.getShading().getColour1()), Color.parseColor("#" +
                        polygon.getShading().getColour2()), Shader.TileMode.MIRROR);
                paint.setShader(shade);
            } else {
                if (polygon.getFillColour() != null) {
                    paint.setColor(Color.parseColor("#" + polygon.getFillColour()));
                }else {
                    paint.setColor(Color.parseColor("#" + defaults.getFillColour()));
                }
            }

            paint.setStyle(Paint.Style.FILL);

            if (polygon.getLineColour() != null) {
                stroke.setColor(Color.parseColor("#" + polygon.getLineColour()));
            } else {
                stroke.setColor(Color.parseColor("#" + defaults.getLineColour()));
            }
            stroke.setDither(true);
            stroke.setStyle(Paint.Style.STROKE);
            stroke.setStrokeJoin(Paint.Join.ROUND);
            stroke.setStrokeCap(Paint.Cap.ROUND);
            stroke.setStrokeWidth(5);
        }
    }


    public LinearGradient getShade() {
        return shade;
    }

    public Paint getPaint() {
        return paint;
    }

    public Paint getStroke() {
        return stroke;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public float getRleft() {
        return rleft;
    }

    public float getRright() {
        return rright;
    }

    public float getRbottom() {
        return rbottom;
    }

    public float getRtop() {
        return rtop;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Shape getShape() {
        return shape;
    }

    public RectF getRect() {
        return rect;
    }

    public Path getPath() {
        return path;
    }

}

