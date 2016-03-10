package com.presentech.handsup;

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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class HostingWizardActivity extends AppCompatActivity {

    private Bitmap background;
    private navDrawer drawer;
    private boolean understanding, multiChoice, messaging, hideFeedback, feedbackPerSlide;
    String mode = "PRESENTER";
    //String mode = "AUDIENCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting_wizard);
        setTitle(R.string.hosting_wizard_title);

        //INPUT BITMAPS
        //get width and height of screen for background
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        changeViewWidths(width);


        //Map smaller images to views - DO NOT REMOVE APP WILL CRASH
        //background = decodeSampledBitmapFromResource(getResources(), R.drawable.background,width, height);
        //ImageView backgroundView = (ImageView) findViewById(R.id.SessionSelectBackground);
        //backgroundView.setImageBitmap(background);
        //Stretch background view to fill screen
        //backgroundView.setScaleType(ImageView.ScaleType.FIT_XY);

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
    }

    public void createSession(View view){

    }

    public void selectFile(View view){
        Intent Intent = new Intent(this, PresentationFileListActivity.class);
        startActivity(Intent);

    }



    public void changeViewWidths(int width){

        double columnWidthDouble = (width/2);
        int columnWidth = (int) columnWidthDouble;
        int checkBoxWidth;

        LinearLayout inputColumn = (LinearLayout) findViewById(R.id.inputGrid);
        LinearLayout optionsColumn = (LinearLayout) findViewById(R.id.optionsGrid);
        inputColumn.getLayoutParams().width = columnWidth;
        optionsColumn.getLayoutParams().width = columnWidth;

        //Align all checkboxes without using GridLayout
        TextView widestText = (TextView) findViewById(R.id.HideFeedbackDuringPresentation);
        checkBoxWidth = widestText.getLayoutParams().width;
        TextView otherTV1 = (TextView) findViewById(R.id.UnderstandingIcons);
        TextView otherTV2 = (TextView) findViewById(R.id.MultipleChoiceIcons);
        TextView otherTV3 = (TextView) findViewById(R.id.Messaging);
        TextView otherTV4 = (TextView) findViewById(R.id.FeedbackPerSlide);
        otherTV1.getLayoutParams().width = checkBoxWidth;
        otherTV2.getLayoutParams().width = checkBoxWidth;
        otherTV3.getLayoutParams().width = checkBoxWidth;
        otherTV4.getLayoutParams().width = checkBoxWidth;

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



    public static Bitmap decodeSampledBitmapFromResource(Resources res,int id, int reqWidth, int reqHeight){
        // Reset sample Size to 0 every time
        int sampleSize = 0;
        //Create new bitmap options
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        //avoids mem allocation means returns NULL not bitmap sets outwidth outheight and outmimetype
        options.inJustDecodeBounds = true;
        //Get the maximum
        BitmapFactory.decodeResource(res, id, options);

        //calculate sample size for bitmap integer = 2^(n-1) where n is magnitudes smaller
        sampleSize= calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = sampleSize;
        //Set Just decode bounds to false so decode resources returns bitmap not NULL
        options.inJustDecodeBounds = false;

        //Return the bitmap with new bounds set
        return BitmapFactory.decodeResource(res, id , options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
