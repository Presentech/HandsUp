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

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolBar;
    private ListView lvSetting;
    private SettingAdapter mAdapter;

    private List<String> settingList;

    private navDrawer drawer;
    String mode = "PRESENTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);


        bindComponents();
        init();
        addListeners();

        //NAVIGATION DRAWER
        //Create new presenter drawer object
        drawer = new navDrawer();
        //Pass views to attach drawer to mDrawerLayout is the top level 'DrawerLayout'
        //mDrawerList is the ListView that holds the options
        drawer.mDrawerLayout = (DrawerLayout) findViewById(R.id.settings_drawerFrame);
        drawer.mDrawerList = (ListView) findViewById(R.id.settings_leftDrawer);
        drawer.createDrawer(SettingsActivity.this, mode);

        //I think Action Bar things HAVE to be done inside the activity
        //Enable drawer display button in Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init() {

        settingList = Arrays.asList(getResources().getStringArray(R.array.setting_entries));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    class SettingAdapter extends BaseAdapter {

        private List<String> settingNames;

        SettingAdapter(List<String> settingList) {

            settingNames = settingList;
        }

        @Override
        public int getCount() {
            return settingNames.size();
        }

        @Override
        public Object getItem(int position) {
            return settingNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.activity_settings_custom_listview, parent,false);
            }

            TextView tvTitle = (TextView) convertView.findViewById(R.id.lvTitle);
            tvTitle.setText(settingNames.get(position));


            return convertView;
        }
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        // Nothing to hide currently!
        boolean drawerOpen = drawer.mDrawerLayout.isDrawerOpen(drawer.mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
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
        // Handle your other action bar items...
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