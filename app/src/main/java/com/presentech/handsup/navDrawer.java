package com.presentech.handsup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by Luke on 04/03/2016.
 * class for navigation Drawer object
 */
public class navDrawer {

    private Activity activity;
    private String navDrawerMode;
    public DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    public ActionBarDrawerToggle mDrawerToggle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<DrawerItem> navDrawerItems;
    private DrawerAdapter adapter;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    public void createDrawer(Activity activity, String mode) {
        //Set Activity to local variable
        this.activity = activity;
        navDrawerMode = mode;
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //Set Title
        mTitle = mDrawerTitle = activity.getTitle();
        //Collect Resources for populating drawer
        if (mode.equals("PRESENTER")) {
            navMenuTitles = activity.getResources().getStringArray(R.array.presenterDrawerOptions);
            navMenuIcons = activity.getResources().obtainTypedArray(R.array.presenterDrawerIcons);
        }if (mode.equals("PRESENTER_RO")) {
            navMenuTitles = activity.getResources().getStringArray(R.array.presenterDrawerOptions_RO);
            navMenuIcons = activity.getResources().obtainTypedArray(R.array.presenterDrawerIcons_RO);
        }
        else if (mode.equals("AUDIENCE")){
            navMenuTitles = activity.getResources().getStringArray(R.array.audienceDrawerOptions);
            navMenuIcons = activity.getResources().obtainTypedArray(R.array.audienceDrawerIcons);

        }

        //Create Array List to add collected resources to
        navDrawerItems = new ArrayList<>();
        //Add items to drawer
        //NavMenuIcons is a typed Array use (.getResourceId(i,j) to convert - i is position, j is value to return if icon not there)
        for(int i=0; i< navMenuIcons.length(); i++){
            navDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(i, -1), navMenuTitles[i]));
        }
        //Recycle Typed Array
        navMenuIcons.recycle();

        //Set drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            // Make Changes when the drawer changes from closed to open and vice versa
            // ALWAYS include the "anti-code" in the opposite method to undo what is done

            // Called when a drawer has settled in a completely closed state.
            // Currently does nothing as nothing needs to happen at this point
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            // Called when a drawer has settled in a completely open state.
            // Currently does nothing as nothing needs to happen at this point
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //Will Enable a home button if it is set up - Currently NOT
        //getSupportActionBar().setHomeButtonEnabled(true);

        adapter = new DrawerAdapter(activity.getApplicationContext(), navDrawerItems);

        mDrawerList.setAdapter(adapter);

    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        //Listen for click on an item in the drawer
        //onItemClick(AdapterView where click happened, view provided by adapter within adapter view, position of view in adapter, id of view)
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //On item click
            selectItem(position);
        }
    }
    //Switch case method to determine action based on click position in drawer
    public void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        if (navDrawerMode.equals("AUDIENCE")){
            switch(position) {
                case 0:
                    Intent mainActivityIntent = new Intent(activity, ModeSelectActivity.class);
                    activity.startActivity(mainActivityIntent);
                    break;
                case 1:
                    Intent AudTutorialIntent = new Intent(activity, AudienceTutorial.class);
                    activity.startActivity(AudTutorialIntent);
                    break;
                case 2:
                    Intent SessionSelectIntent = new Intent(activity, AudienceConnect.class);
                    activity.startActivity(SessionSelectIntent);
                    break;
                case 3:
                    Intent aboutIntent = new Intent(activity, aboutActivity.class);
                    activity.startActivity(aboutIntent);
                    break;
                default:
            }
        }
        else if (navDrawerMode.equals("PRESENTER")){
            switch(position) {
                case 0:
                    Intent mainActivityIntent = new Intent(activity, ModeSelectActivity.class);
                    activity.startActivity(mainActivityIntent);
                    break;
                case 1:
                    Intent newSessionIntent = new Intent(activity, HostingWizardActivity.class);
                    activity.startActivity(newSessionIntent);
                    break;
                case 2:
                    Intent settingsIntent = new Intent(activity, SettingsActivity.class);
                    activity.startActivity(settingsIntent);
                    break;
                case 3:
                    Intent tutorialIntent = new Intent(activity, PresentationModeTutorial.class);
                    activity.startActivity(tutorialIntent);
                    break;
                case 4:
                    Intent aboutIntent = new Intent(activity, aboutActivity.class);
                    activity.startActivity(aboutIntent);
                default:
            }
        }
        else if (navDrawerMode.equals("PRESENTER_RO")){
            switch(position) {
                case 0:
                    Intent mainActivityIntent = new Intent(activity, ModeSelectActivity.class);
                    activity.startActivity(mainActivityIntent);
                    break;
                case 1:
                    Intent newSessionIntent = new Intent(activity, HostingWizardActivity.class);
                    activity.startActivity(newSessionIntent);
                    break;
                case 2:
                    Intent tutorialIntent = new Intent(activity, PresentationModeTutorial.class);
                    activity.startActivity(tutorialIntent);
                    break;
                case 3:
                    Intent aboutIntent = new Intent(activity, aboutActivity.class);
                    activity.startActivity(aboutIntent);
                default:
            }
        }
    }


}

