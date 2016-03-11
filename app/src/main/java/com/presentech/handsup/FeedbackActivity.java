package com.presentech.handsup;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *Created by Edward Prentice on 9/03/2016
 */
public class FeedbackActivity extends AppCompatActivity {

    String mode = "AUDIENCE";
    private Bitmap background, arrow_left, arrow_right, tick, question, refresh, returnA, returnB, returnC;
    private navDrawer drawer;

    @Override
    protected void onDestroy(){
        super.onDestroy();
        background.recycle();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        changeViewWidths(width);

        background = decodeSampledBitmapFromResource(getResources(), R.drawable.background, width, height);
        ImageView backgroundView = (ImageView) findViewById(R.id.feedbackActivityBackground);

        backgroundView.setImageBitmap(background);

        //NAVIGATION DRAWER
        //Create new presenter drawer object
        drawer = new navDrawer();
        //Pass views to attach drawer to mDrawerLayout is the top level 'DrawerLayout'
        //mDrawerList is the ListView that holds the options
        drawer.mDrawerLayout = (DrawerLayout) findViewById(R.id.hostingWizard_drawerFrame);
        drawer.mDrawerList = (ListView) findViewById(R.id.hostingWizard_leftDrawer);
        drawer.createDrawer(FeedbackActivity.this, mode);

        //I think Action Bar things HAVE to be done inside the activity
        //Enable drawer display button in Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
    public void changeViewWidths(int width){

        double columnWidthDouble = width*0.8;

        LinearLayout feedbackButtons = (LinearLayout) findViewById(R.id.feedbackButtonsLayout);
        feedbackButtons.getLayoutParams().width = (int) columnWidthDouble;

        ImageView backgroundView = (ImageView) findViewById(R.id.feedbackActivityBackground);
        //backgroundView.getLayoutParams().width = width;
        backgroundView.setScaleType(ImageView.ScaleType.FIT_XY);

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
