package com.presentech.handsup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.provider.SyncStateContract;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.presentech.handsup.presentationfile.PresentationFile;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HostingWizardActivity extends AppCompatActivity {

    private Bitmap background;
    public boolean understanding, multiChoice, messaging, hideFeedback, feedbackPerSlide;
    private navDrawer drawer;
    String mode = "PRESENTER_RO";
    //String mode = "AUDIENCE";
    public static final String FILE_PATH_NAME = "path name";
    String pathName;
    private LinearLayout inputColumn;
    private LinearLayout optionsColumn;
    private LinearLayout walkthrough1, walkthrough2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_wizard);
        setTitle(R.string.hosting_wizard_title);

        //Get presntation filePath if returning from PresentationFileListActivity
        Intent intent = getIntent();
        if (intent.getStringExtra(FILE_PATH_NAME) != null){
            pathName = intent.getStringExtra(FILE_PATH_NAME);
        }
        pathName = "test.xml";
        //get width and height of screen for views
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        changeViewWidths(width, height);


        //NAVIGATION DRAWER
        //Create new presenter drawer object
        drawer = new navDrawer();
        //Pass views to attach drawer to mDrawerLayout is the top level 'DrawerLayout'
        //mDrawerList is the ListView that holds the options
        drawer.mDrawerLayout = (DrawerLayout) findViewById(R.id.hostingWizard_drawerFrame);
        drawer.mDrawerList = (ListView) findViewById(R.id.hostingWizard_leftDrawer);
        drawer.createDrawer(HostingWizardActivity.this, mode);

        //I think Action Bar things HAVE to be done inside the activity
        //Enable drawer display button in Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Tutorial
        walkthrough1.setVisibility(View.INVISIBLE);
        walkthrough2.setVisibility(View.INVISIBLE);
        checkFirstRun();
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;
        // Get current version code
        int currentVersionCode;
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            // handle exception
            e.printStackTrace();
            return;
        }

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            // This is just a normal run
            return;
        } else if (savedVersionCode == DOESNT_EXIST) {
            // Show the Tutorial Here
            // Update this tutorial with new features added
            runWalkthrough();
        } else if (currentVersionCode > savedVersionCode) {
            // If something new is added to this code in an update inform users here.
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

    }

    private void runWalkthrough() {
        walkthrough1.setVisibility(View.VISIBLE);
        optionsColumn.setVisibility(View.INVISIBLE);
    }

    public void goToWalkthroughState2(View view) {
        walkthrough1.setVisibility(View.INVISIBLE);
        optionsColumn.setVisibility(View.VISIBLE);
        inputColumn.setVisibility(View.INVISIBLE);
        walkthrough2.setVisibility(View.VISIBLE);
    }

    public void finishWalkthrough(View view) {
        inputColumn.setVisibility(View.VISIBLE);
        walkthrough2.setVisibility(View.INVISIBLE);

    }

    public void createSession(View view) throws IOException, XmlPullParserException {
        PresentationFile presentationFile = getPresentation(pathName);
        //Go to Presentation with options set
        Intent intent = new Intent(this, PresentationActivity.class);
        Bundle b = new Bundle();
        //Add options to Presentation
        //Intent.putExtra(PresentationActivity.BOOLEAN_NAME1, understanding);
        //Intent.putExtra(PresentationActivity.BOOLEAN_NAME2, multiChoice);
        //Intent.putExtra(PresentationActivity.BOOLEAN_NAME3, messaging);
        //Intent.putExtra(PresentationActivity.BOOLEAN_NAME4, hideFeedback);
        //Intent.putExtra(PresentationActivity.BOOLEAN_NAME5, feedbackPerSlide);
        EditText session_name_view = (EditText) findViewById(R.id.SessionTitle);
        EditText session_password_view = (EditText) findViewById(R.id.SessionPassword);
        EditText session_location_view = (EditText) findViewById(R.id.SessionLocation);

        String session_name = session_name_view.getText().toString();
        String session_password = session_name_view.getText().toString();
        String session_location = session_name_view.getText().toString();
        //Intent.putExtra(PresentationActivity.SESSION_NAME, session_name);
        //Intent.putExtra(PresentationActivity.SESSION_PASSWORD, session_password);
        //Intent.putExtra(PresentationActivity.SESSION_LOCATION, session_location);


       // b.putParcelable(SyncStateContract.Constants.CUSTOM_LISTING, presentationFile);
        //intent.putExtra("pF", presentationFile);

        startActivity(intent);
    }
    public PresentationFile getPresentation(String pathName) throws IOException, XmlPullParserException {
        XMLParser parser = new XMLParser();
        InputStream in = null;
        in = getAssets().open(pathName);
        return parser.getPresentation(in);
    }

    //Start activity to select a presentation file
    public void selectFile(View view){
        Intent Intent = new Intent(this, PresentationFileListActivity.class);
        startActivity(Intent);
    }

    public void changeViewWidths(int width, int height){

        double columnWidthDouble = width*0.5;
        int columnWidth = (int) columnWidthDouble;
        double Walk2WidthDouble = width*0.46;
        int Walk2Width = (int) Walk2WidthDouble;

        double walkthroughHeightDouble = height * 0.75;
        int walkthroughHeight = (int) walkthroughHeightDouble;

        inputColumn = (LinearLayout) findViewById(R.id.inputGrid);
        optionsColumn = (LinearLayout) findViewById(R.id.optionsGrid);
        walkthrough1 = (LinearLayout) findViewById(R.id.HWwalkthrough1);
        walkthrough2 = (LinearLayout) findViewById(R.id.HWwalkthrough2);

        inputColumn.getLayoutParams().width = columnWidth;
        optionsColumn.getLayoutParams().width = columnWidth;
        walkthrough1.getLayoutParams().width = columnWidth;
        walkthrough1.getLayoutParams().height = walkthroughHeight;
        walkthrough2.getLayoutParams().width = Walk2Width;
        walkthrough2.getLayoutParams().height = walkthroughHeight;

    }

    public void onCheckboxClicked(View view){
        //Check if checkBox is checked! Check check check
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        // Update presentation object to
        switch(view.getId()) {
            case R.id.HWcheckbox1:
                if (checked)
                    understanding = true;
                else
                    understanding = false;
                break;
            case R.id.HWcheckbox2:
                if (checked)
                    multiChoice = true;
                else
                    multiChoice = false;
                break;
            case R.id.HWcheckbox3:
                if (checked)
                    messaging = true;
                else
                    messaging = false;
                break;
            case R.id.HWcheckbox4:
                if (checked)
                    feedbackPerSlide = true;
                else
                    feedbackPerSlide = false;
                break;
            case R.id.HWcheckbox5:
                if (checked)
                    hideFeedback = true;
                else
                    hideFeedback = false;
                break;
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
