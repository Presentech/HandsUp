package com.presentech.handsup;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.presentech.handsup.R;
import com.presentech.handsup.navDrawer;

public class ExampleStackedBars extends AppCompatActivity {
    double A =1, B = 1, C = 1;
    double APercent, BPercent, CPercent, totalInputs, barHeight;
    View AView, BView, CView, DummyView;
    ViewGroup viewParent;
    private ViewGroup.LayoutParams AParams, BParams, CParams;
    public int screenWidth, screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_stacked_bars);

        initBar();
    }

    public void initBar(){
        //Create references to views
        AView = findViewById(R.id.greenLayout);
        BView = findViewById(R.id.yellowLayout);
        CView = findViewById(R.id.redLayout);
        DummyView = findViewById(R.id.dummyBar);
        viewParent = ((ViewGroup)CView.getParent());

        //Get Screen Size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //Get Paramater values for each bar section
        AParams = AView.getLayoutParams();
        BParams = BView.getLayoutParams();
        CParams = CView.getLayoutParams();
        setBar();
        updateBarHeight();

    }

    private void setBar(){
        //Calculate the width of the bar
        double barWidthDouble = 0.1*screenWidth;
        int barWidth = (int) barWidthDouble;
        //Calculate the height of the bar
        barHeight = 0.8*screenHeight;

        //Set width to all views
        AParams.width = barWidth;
        BParams.width = barWidth;
        CParams.width = barWidth;
    }
    private void updateBarHeight(){
        //Calculate the percent of the bar filled by each section
        totalInputs = A+B+C;
        APercent = A/totalInputs;
        BPercent = B/totalInputs;
        CPercent = C/totalInputs;

        //Calculate the height of each bar section
        double AHeightDouble = APercent * barHeight;
        int AHeight = (int) AHeightDouble;
        double BHeightDouble = BPercent * barHeight;
        int BHeight = (int) BHeightDouble;
        double CHeightDouble = CPercent * barHeight;
        int CHeight = (int) CHeightDouble;

        //Set Height
        AParams.height = AHeight;
        BParams.height = BHeight;
        CParams.height = CHeight;
    }
    public void increaseA(View view){
        if (A < 100) A++;
        updateBarHeight();
        onPause();
        Log.d("Bars", "A" + APercent + "N:" + AParams.height + "I:" + totalInputs);
    }
    public void increaseB(View view){
        if (B < 100) B++;
        updateBarHeight();
        Log.d("Bars", "B" + BPercent + "N:" + BParams.height + "I:" + totalInputs);
    }
    public void increaseC(View view){
        if (C < 100) C++;
        updateBarHeight();
        Log.d("Bars", "C" + CPercent + "N:" + CParams.height + "I:" + totalInputs);
    }
    public void decreaseA(View view) {
        if (A > 1) A--;
        updateBarHeight();
        Log.d("Bars", "A" + APercent + "N:" + A + "I:" + totalInputs);
    }
    public void decreaseB(View view) {
        if (B > 1) B--;
        updateBarHeight();
        Log.d("Bars", "B" + BPercent + "N:" + B + "I:" + totalInputs);
    }
    public void decreaseC(View view) {
        if (C > 1) C--;
        updateBarHeight();
        Log.d("Bars", "C" + CPercent + "N:" + C + "I:" + totalInputs);
    }
}
