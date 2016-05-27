package com.presentech.handsup;

        import android.content.Intent;
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v4.content.ContextCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import com.presentech.handsup.R;

public class AudienceSessionSelect extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button connectButton1;
    private Button connectButton2;
    private Button connectButton3;
    private Button connectButton4;
    private Button connectButton5;
    private navDrawer drawer;
    String mode = "AUDIENCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_session_select);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

           /*Set content textviews and title buttons*/
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        connectButton1 = (Button) findViewById(R.id.connectButton1);
        connectButton2 = (Button) findViewById(R.id.connectButton2);
        connectButton3 = (Button) findViewById(R.id.connectButton3);
        connectButton4 = (Button) findViewById(R.id.connectButton4);
        connectButton5 = (Button) findViewById(R.id.connectButton5);

        /*Set content initial visibility*/
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);
        connectButton1.setVisibility(View.GONE);
        connectButton2.setVisibility(View.GONE);
        connectButton3.setVisibility(View.GONE);
        connectButton4.setVisibility(View.GONE);
        connectButton5.setVisibility(View.GONE);

        //nav drawer

        drawer = new navDrawer();

        drawer.mDrawerLayout = (DrawerLayout) findViewById(R.id.audience_session_select_drawerFrame);
        drawer.mDrawerList = (ListView) findViewById(R.id.audience_session_select_leftDrawer);
        drawer.createDrawer(AudienceSessionSelect.this, mode);
       // Enable drawer display button in Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void feedbackActivity(View view){
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public void textView1Expand(View v){
        /*Toggle visibility of content TextViews*/
        textView1.setVisibility(textView1.isShown() ? View.GONE : View.VISIBLE);
        connectButton1.setVisibility(connectButton1.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (textView1.getVisibility() == View.VISIBLE)
            button1.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowndown), null);
        else
            button1.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowright), null);
    }

    public void textView2Expand(View v) {
        /*Toggle visibility of content TextViews*/
        textView2.setVisibility(textView2.isShown() ? View.GONE : View.VISIBLE);
        connectButton2.setVisibility(connectButton2.isShown() ? View.GONE : View.VISIBLE);
        /*Toggle up/down arrows*/
        if (textView2.getVisibility() == View.VISIBLE)
            button2.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowndown), null);
        else
            button2.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowright), null);

    }

    public void textView3Expand(View v) {
        /*Toggle visibility of content TextViews*/
        textView3.setVisibility(textView3.isShown() ? View.GONE : View.VISIBLE);
        connectButton3.setVisibility(connectButton3.isShown() ? View.GONE : View.VISIBLE);
        /*Toggle up/down arrows*/
        if (textView3.getVisibility() == View.VISIBLE)
            button3.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowndown), null);
        else
            button3.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowright), null);

    }

    public void textView4Expand(View v) {
        /*Toggle visibility of content TextViews*/
        textView4.setVisibility(textView4.isShown() ? View.GONE : View.VISIBLE);
        connectButton4.setVisibility(connectButton4.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (textView4.getVisibility() == View.VISIBLE)
            button4.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowndown), null);
        else
            button4.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowright), null);

    }

    public void textView5Expand(View v) {
        /*Toggle visibility of content TextViews*/
        textView5.setVisibility(textView5.isShown() ? View.GONE : View.VISIBLE);
        connectButton5.setVisibility(connectButton5.isShown() ? View.GONE : View.VISIBLE);

        /*Toggle up/down arrows*/
        if (textView5.getVisibility() == View.VISIBLE)
            button5.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowndown), null);
        else
            button5.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.mipmap.arrowright), null);
    }

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