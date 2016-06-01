package com.presentech.handsup;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.audiofx.BassBoost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/* Created by Jay on 10-03-2016 (and edited by Luke) */

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Toolbar toolBar;
    private ListView lvSetting;
    /*adapter with data*/
    private SettingAdapter mAdapter;
    private List<String> settingList;
    private navDrawer drawer;
    String mode = "PRESENTER";

    @Override
    /*called when the activity is first created*/
    /*calls onCreate to load a saved instance from the bundle to regenerate activity*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);

        bindComponents();
        init();
        addListeners();

        //NAVIGATION DRAWER. Create new presenter drawer object
        drawer = new navDrawer();
        //Pass views to attach drawer to mDrawerLayout is the top level 'DrawerLayout'
        //mDrawerList is the ListView that holds the options
        drawer.mDrawerLayout = (DrawerLayout) findViewById(R.id.settings_drawerFrame);
        drawer.mDrawerList = (ListView) findViewById(R.id.settings_leftDrawer);
        drawer.createDrawer(SettingsActivity.this, mode);

        //Enable drawer display button in Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init() {
        /*get array list from strings xml*/
        settingList = Arrays.asList(getResources().getStringArray(R.array.setting_entries));
        /*adapter to pull content from array list*/
        mAdapter = new SettingAdapter(settingList);
        lvSetting.setAdapter(mAdapter);
    }

    private void addListeners() {
        lvSetting.setOnItemClickListener(this);
    }
    private void bindComponents() {
        lvSetting = (ListView) findViewById(R.id.listView);
    }

    @Override
    /*generic adapter view*/
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*handling the new states*/
        switch (position){
            case 0 :
                Intent accessibility_intent = new Intent(this, SettingsAccessibilityActivity.class);
                startActivity(accessibility_intent);
                break;
            case 1 :
                Intent account_intent = new Intent(this, SettingsAccountActivity.class);
                startActivity(account_intent);
                break;

            case 2 :
                Intent presentation_intent = new Intent(this, SettingsPresentationFeedbackActivity.class);
                startActivity(presentation_intent);
                break;

            case 3:
                Intent file_intent = new Intent(this, SettingsFileStorageActivity.class);
                startActivity(file_intent);
                break;

            case 4 :
                Intent intent = new Intent(this, SettingsDefaultSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    /*custom adapter inherits base adaptor. Inflates the items with xml layout */
    class SettingAdapter extends BaseAdapter {
        private List<String> settingNames;
        SettingAdapter(List<String> settingList) {
            settingNames = settingList;
        }

        @Override
        /*size of data in the adaptor*/
        public int getCount() {
            return settingNames.size();
        }

        @Override
        /*Get the data set corresponding to the index of data items*/
        public Object getItem(int position) {
            return settingNames.get(position);
        }

        @Override
        /*get the ID corresponding to the specified row*/
        public long getItemId(int position) {
            return 0;
        }

        /*get the contents of each display item*/
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*if convertView is not already initialised, then inflate the listview item layout*/
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_settings_custom_listview, parent,false);
            }
            /*convert the list view*/
            /*declare object TextView*/
            TextView tvTitle = (TextView) convertView.findViewById(R.id.lvTitle);
            tvTitle.setText(settingNames.get(position));
            return convertView;
        }
    }

    // Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer.mDrawerLayout.isDrawerOpen(drawer.mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawer.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //This handles action bar events
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawer.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    //This toggles the image on the action bar when the drawer is open
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawer.mDrawerToggle.syncState();
    }
}