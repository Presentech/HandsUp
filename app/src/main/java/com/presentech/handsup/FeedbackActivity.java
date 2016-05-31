package com.presentech.handsup;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.preference.PreferenceActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Edward Prentice on 9/03/2016
 * Modified by Noor Mansure on 18/05/2016:
 * Adding functionality to the activity, including connectivity
 */
//public class FeedbackActivity extends AppCompatActivity  implements View.OnClickListener {
public class FeedbackActivity extends AppCompatActivity {

    String mode = "AUDIENCE";
    private Bitmap background, arrow_left, arrow_right, tick, question, refresh, returnA, returnB, returnC;
    private navDrawer drawer;

    //Text box for sending comments/questions
    EditText messageInput;

    //Text views for slide title and content
    TextView slideTitleTextView;
    TextView slideContentTextView;

    //Buttons
    ImageButton nextButton;
    ImageButton backButton;
    ImageButton aButton;
    ImageButton bButton;
    ImageButton cButton;
    ImageButton goodButton;
    ImageButton mehButton;
    ImageButton badButton;
    ImageButton sendButton;

    int count = 0;
    String UUID = "";
    int x; //current slide

    //Connectivity requirements
    MyApplication application;
    Client client;

    //Creating JSON objects for sending
    FeedbackJSON feedbackJSON = new FeedbackJSON();
    questionJSON questionJSON = new questionJSON();
    List<SingleQuestion> singleQuestionList = new ArrayList<>();
    List<SingleQuestion> availableQuestionList = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        background.recycle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        messageInput = (EditText) findViewById(R.id.commentEditText);
        //nextButton = (ImageButton)findViewById(R.id.nextButton);
        //backButton = (ImageButton) findViewById(R.id.backButton);
        aButton = (ImageButton) findViewById(R.id.aButton);
        bButton = (ImageButton) findViewById(R.id.bButton);
        cButton = (ImageButton) findViewById(R.id.cButton);
        goodButton = (ImageButton) findViewById(R.id.goodButton);
        mehButton = (ImageButton) findViewById(R.id.mehButton);
        badButton = (ImageButton) findViewById(R.id.badButton);
        sendButton = (ImageButton) findViewById(R.id.sendButton);

        slideContentTextView = (TextView) findViewById(R.id.slideContentTextView);
        slideTitleTextView = (TextView) findViewById(R.id.slideTitleTextView);

        //add button click listeners to all buttons
        addListenerOnButton();
        application = (MyApplication) getApplication();
        client = application.getClient();
        Identifier id = new Identifier();
        UUID = id.id(this);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        changeViewWidths(width);

        background = decodeSampledBitmapFromResource(getResources(), R.drawable.background, width, height);
        ImageView backgroundView = (ImageView) findViewById(R.id.feedbackActivityBackground);

        //NAVIGATION DRAWER
        //Create new presenter drawer object
        drawer = new navDrawer();
        //Pass views to attach drawer to mDrawerLayout is the top level 'DrawerLayout'
        //mDrawerList is the ListView that holds the options
        drawer.mDrawerLayout = (DrawerLayout) findViewById(R.id.hostingWizard_drawerFrame);
        drawer.mDrawerList = (ListView) findViewById(R.id.hostingWizard_leftDrawer);
        drawer.createDrawer(FeedbackActivity.this, mode);

        //Enable drawer display button in Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        client.setCustomObjectListener(new Client.onMessageListener() {
            @Override
            public void onObjectReady(String title) {
                // Code to handle object ready
            }

            @Override
            public void onDataLoaded(final List<SingleQuestion> singleQuestions, int i) {
                // Code to handle data loaded from network
                // Use the data here!
                x = i;
                Log.d("noor", "like a child");

                singleQuestionList = singleQuestions;
                availableQuestionList.clear();
                if (singleQuestionList.size() > 0) {
                    for (int j = 0; j < singleQuestionList.size(); j++) {
                        Log.d("noor", Integer.toString(x) + Double.toString(singleQuestionList.get(j).getSLIDE()));
                        if (x == singleQuestionList.get(j).getSLIDE()) {
                            availableQuestionList.add(singleQuestionList.get(j));

                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (availableQuestionList.size() > 0) {
                                slideContentTextView.setText(availableQuestionList.get(0).getQuestionText());
                            } else {
                                slideContentTextView.setText("No questions for this slide");
                            }
                            //String title = ("Slide" + availableQuestionList.get(count).getSLIDE() + " " + "Q" + availableQuestionList.get(count).getQUESTION());
                            // slideTitleTextView.setText(title);
                            count++;
                            if (count == availableQuestionList.size()) {
                                count = 0;
                            }
                            ;
                        }
                    });
                }
            }
        })

        ;
    }

    ;


    private SingleFeedback setFeedback() {
        SingleFeedback singleFeedback = new SingleFeedback();
        singleFeedback.setUUID(UUID);
        singleFeedback.setSLIDE(x);
        return singleFeedback;
    }

    public void addListenerOnButton() {
        //show the next slide content
//        nextButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                Log.d("FeedbackActivity", client.rxString.toString());
//                //Log.d("FeedbackActivity", client.singleQuestionList.get(0).getQuestionText());
//                singleQuestionList = client.singleQuestionList;
//                //Log.d("FeedbackActivity", Integer.toString(singleQuestionList.size()));
//                slideContentTextView.setText(singleQuestionList.get(count).getQuestionText());
//                String title = ("Slide" + singleQuestionList.get(count).getSLIDE() + " " + "Q" + singleQuestionList.get(count).getQUESTION());
//                slideTitleTextView.setText(title);
//                count++;
//                if (count == singleQuestionList.size()) {
//                    count = 0;
//                }
//                ;
//
//            }
//        });
//        //show the previous slide content
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//            }
//        });
        //set A as the answer to the question
        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SingleFeedback singleFeedback = setFeedback();
                singleFeedback.setQUESTION(1);
                singleFeedback.setABC(1);
                String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                client.onSend(sendThis);
            }
        });
        //set B as the answer to the question
        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SingleFeedback singleFeedback = setFeedback();
                singleFeedback.setQUESTION(1);
                singleFeedback.setABC(2);
                String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                client.onSend(sendThis);
            }
        });
        //set C as the answer to the question
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SingleFeedback singleFeedback = setFeedback();
                singleFeedback.setQUESTION(1);
                singleFeedback.setABC(3);
                String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                client.onSend(sendThis);
                ;
            }
        });
        //set feedback to good
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SingleFeedback singleFeedback = setFeedback();
                singleFeedback.setGOOD_MEH_BAD(1);
                String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                client.onSend(sendThis);
            }
        });
        //set feedback as meh
        mehButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SingleFeedback singleFeedback = setFeedback();
                singleFeedback.setGOOD_MEH_BAD(2);
                String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                client.onSend(sendThis);
            }
        });
        //set feedback as bad
        badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SingleFeedback singleFeedback = setFeedback();
                singleFeedback.setGOOD_MEH_BAD(3);
                String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                client.onSend(sendThis);
            }
        });
        //send a comment or question
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (messageInput.getText().toString().length() < 300) {
                    SingleFeedback singleFeedback = setFeedback();
                    singleFeedback.setTEXT(messageInput.getText().toString());
                    String sendThis = feedbackJSON.FeedbackJSONGenerate(singleFeedback);
                    client.onSend(sendThis);
                } else {
                    Toast.makeText(getApplicationContext(), "Message too long. Over 300 character limit", Toast.LENGTH_LONG);
                }

            }

        });
    }

    public void changeViewWidths(int width) {

        double columnWidthDouble = width * 0.8;

        LinearLayout feedbackButtons = (LinearLayout) findViewById(R.id.feedbackButtonsLayout);
        feedbackButtons.getLayoutParams().width = (int) columnWidthDouble;

        ImageView backgroundView = (ImageView) findViewById(R.id.feedbackActivityBackground);
        //backgroundView.getLayoutParams().width = width;
        backgroundView.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int id, int reqWidth, int reqHeight) {
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
        sampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = sampleSize;
        //Set Just decode bounds to false so decode resources returns bitmap not NULL
        options.inJustDecodeBounds = false;

        //Return the bitmap with new bounds set
        return BitmapFactory.decodeResource(res, id, options);
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


    String getIpAddress() {
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        return Integer.toHexString(wm.getConnectionInfo().getIpAddress());
    }


}
