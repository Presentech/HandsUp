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
    import android.widget.ImageView;
    import android.widget.ListView;

/**
* Created by Luke on 05/03/2016.
*/
    public class ModeSelectActivity extends AppCompatActivity{

        private Bitmap background, appName, appLogo, companyLogo, presenterButton, audienceButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mode_select);
            if (getSupportActionBar() != null){
                getSupportActionBar().hide();
            }

            //INPUT BITMAPS
            //get width and height of screen for background
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;


            //Map smaller images to views - DO NOT REMOVE APP WILL CRASH
            background=decodeSampledBitmapFromResource(getResources(),R.drawable.background,width,height);
            appLogo = decodeSampledBitmapFromResource(getResources(),R.drawable.app_logo,130,130);
            appName = decodeSampledBitmapFromResource(getResources(),R.drawable.app_name,150,50);
            companyLogo = decodeSampledBitmapFromResource(getResources(),R.drawable.company_logo,70,70);
            presenterButton = decodeSampledBitmapFromResource(getResources(),R.drawable.presenter_button,150,25);
            audienceButton = decodeSampledBitmapFromResource(getResources(),R.drawable.audience_button,150,25);



            ImageView backgroundView = (ImageView) findViewById(R.id.ModeSelectBackground);
            ImageView appLogoView = (ImageView) findViewById(R.id.ModeSelectAppLogo);
            ImageView appNameView = (ImageView) findViewById(R.id.ModeSelectAppName);
            ImageView presenterButtonView = (ImageView) findViewById(R.id.PresenterSelect);
            ImageView audienceButtonView = (ImageView) findViewById(R.id.AudienceSelect);
            //ImageView companyLogoView = (ImageView) findViewById(R.id.ModeSelectCompanyLogo);

            backgroundView.setImageBitmap(background);
            appLogoView.setImageBitmap(appLogo);
            appNameView.setImageBitmap(appName);
            presenterButtonView.setImageBitmap(presenterButton);
            audienceButtonView.setImageBitmap(audienceButton);
            //Stretch background view to fill screen
            backgroundView.setScaleType(ImageView.ScaleType.FIT_XY);

        }
        public void goToAudienceMode(View view){
            //Intent AudienceIntent = new Intent(this, SessionSelectActivity.this);
        }
        public void goToPresenterMode(View view){
            //Intent PresenterIntent = new Intent(this, ModeSelectActivity.this);
            //startActivity(intent);
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
