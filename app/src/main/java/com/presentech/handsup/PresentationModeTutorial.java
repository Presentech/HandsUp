package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PresentationModeTutorial extends AppCompatActivity {

    TextView load_content;
    TextView access_content;
    TextView control_content;
    TextView configure_content;
    TextView exit_content;
    TextView export_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_mode_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        load_content = (TextView) findViewById(R.id.load_content);
        access_content = (TextView) findViewById(R.id.access_content);
        control_content = (TextView) findViewById(R.id.control_content);
        configure_content = (TextView) findViewById(R.id.configure_content);
        exit_content = (TextView) findViewById(R.id.exit_content);
        export_content = (TextView) findViewById(R.id.export_content);

        /*Set content initial visibility*/
        load_content.setVisibility(View.GONE);
        access_content.setVisibility(View.GONE);
        control_content.setVisibility(View.GONE);
        configure_content.setVisibility(View.GONE);
        exit_content.setVisibility(View.GONE);
        export_content.setVisibility(View.GONE);

    }

    /*Skip button intent*/
    public void nextActivity(View view) {
        Intent intent = new Intent();
        startActivity(intent);
    }

    /*Toggle visiblity of content TextViews*/
    public void expandTextLoad(View v) {
        load_content.setVisibility(load_content.isShown() ? View.GONE : View.VISIBLE);
    }

    public void expandTextAccess(View v) {
        access_content.setVisibility(access_content.isShown() ? View.GONE : View.VISIBLE);
    }

    public void expandTextControl(View v) {
        control_content.setVisibility(control_content.isShown() ? View.GONE : View.VISIBLE);
    }

    public void expandTextConfigure(View v) {
        configure_content.setVisibility(configure_content.isShown() ? View.GONE : View.VISIBLE);
    }

    public void expandTextExit(View v) {
        exit_content.setVisibility(exit_content.isShown() ? View.GONE : View.VISIBLE);
    }

    public void expandTextExport(View v) {
        export_content.setVisibility(export_content.isShown() ? View.GONE : View.VISIBLE);
    }


}
