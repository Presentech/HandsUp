package com.presentech.handsup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PresentationModeTutorial extends AppCompatActivity {

    private TextView load_content;
    private TextView access_content;
    private TextView control_content;
    private TextView configure_content;
    private TextView exit_content;
    private TextView export_content;
    private Button load_title;
    private Button access_title;
    private Button control_title;
    private Button configure_title;
    private Button exit_title;
    private Button export_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_mode_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Set content textviews and title buttons*/
        load_content = (TextView) findViewById(R.id.load_content);
        access_content = (TextView) findViewById(R.id.access_content);
        control_content = (TextView) findViewById(R.id.control_content);
        configure_content = (TextView) findViewById(R.id.configure_content);
        exit_content = (TextView) findViewById(R.id.exit_content);
        export_content = (TextView) findViewById(R.id.export_content);

        load_title = (Button) findViewById(R.id.load_title);
        access_title = (Button) findViewById(R.id.access_title);
        control_title = (Button) findViewById(R.id.control_title);
        configure_title = (Button) findViewById(R.id.configure_title);
        exit_title = (Button) findViewById(R.id.exit_title);
        export_title = (Button) findViewById(R.id.export_title);

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
        Intent intent = new Intent(this, HostingWizardActivity.class);
        startActivity(intent);
    }

    public void expandTextLoad(View v) {
        /*Toggle visibility of content TextViews*/
        load_content.setVisibility(load_content.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (load_content.getVisibility() == View.VISIBLE)
            load_title.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowndown), null);
        else
            load_title.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowright), null);

    }

    public void expandTextAccess(View v) {
        access_content.setVisibility(access_content.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (access_content.getVisibility() == View.VISIBLE)
            access_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this, R.mipmap.arrowndown),null);
        else
            access_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this,R.mipmap.arrowright),null);

    }

    public void expandTextControl(View v) {
        control_content.setVisibility(control_content.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (control_content.getVisibility() == View.VISIBLE)
            control_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this, R.mipmap.arrowndown),null);
        else
            control_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this,R.mipmap.arrowright),null);

    }

    public void expandTextConfigure(View v) {
        configure_content.setVisibility(configure_content.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (configure_content.getVisibility() == View.VISIBLE)
            configure_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this, R.mipmap.arrowndown),null);
        else
            configure_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this,R.mipmap.arrowright),null);

    }

    public void expandTextExit(View v) {
        exit_content.setVisibility(exit_content.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (exit_content.getVisibility() == View.VISIBLE)
            exit_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this, R.mipmap.arrowndown),null);
        else
            exit_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this,R.mipmap.arrowright),null);

    }

    public void expandTextExport(View v) {
        export_content.setVisibility(export_content.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (export_content.getVisibility() == View.VISIBLE)
            export_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this, R.mipmap.arrowndown),null);
        else
            export_title.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(this,R.mipmap.arrowright),null);

    }


}
