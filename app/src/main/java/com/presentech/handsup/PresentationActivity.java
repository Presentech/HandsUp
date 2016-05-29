package com.presentech.handsup;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;
import android.graphics.Color;

import com.presentech.handsup.presentationfile.*;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PresentationActivity extends AppCompatActivity {

    public static final String BOOLEAN_NAME1 = "boolean1";
    public static final String  BOOLEAN_NAME2 = "boolean2";
    public static final String  BOOLEAN_NAME3 = "boolean3";
    public static final String  BOOLEAN_NAME4= "boolean4";
    public static final String  BOOLEAN_NAME5 = "boolean5";

    //the presentation container
    private ViewFlipper viewFlipper;
    private float lastX;
    int screenWidth, stackedBarsAmount;
    int screenHeight, feedbackHeight, feedbackWidth;
    public Boolean understanding, multiChoice, messaging, hideFeedback, feedbackPerSlide;
    PresentationFile presentationFile;
    private navDrawer drawer;

    String mode = "PRESENTER";
    liveFeedbackFragment fbFragment;
    public SingleFeedback[] feedbackArray = new SingleFeedback[10];

    TextHandler tH = new TextHandler();
    RelativeLayout slide = null;
    Canvas canvas = new Canvas();

    //Connectivity requirements
    MyApplication application;
    Server presenterServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        setTitle(R.string.presentation_container);
        Bundle b = this.getIntent().getExtras();
        //String id = b.getParcelable(SyncStateContract.Constants.CUSTOM_LISTING);
        //presentationFile = (PresentationFile) this.getIntent().getSerializableExtra("pF");

        try {
            getPresentation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        viewFlipper.setBackgroundColor(Color.parseColor("#" + presentationFile.getDefaults().getBackgroundColour()));
        viewFlipper.getParent().getParent().requestDisallowInterceptTouchEvent(true);

        understanding = b.getBoolean(BOOLEAN_NAME1);
        messaging = b.getBoolean(BOOLEAN_NAME2);
        multiChoice = b.getBoolean(BOOLEAN_NAME3);
        hideFeedback = b.getBoolean(BOOLEAN_NAME4);
        //feedbackPerSlide = b.getBoolean(BOOLEAN_NAME5);

        getScreenSize();
        populateSlides();


        // If presenter wants to view feedback create feedback fragment
        if (!hideFeedback){
            if (findViewById(R.id.feedbackFragmentContainer) != null){
                // If we're being restored from a previous state, don't do anything
                // we could end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }
                // Create a new Fragment to be placed in the activity layout
                fbFragment = new liveFeedbackFragment();
                fbFragment.setScreenParams(feedbackHeight, feedbackWidth);
                fbFragment.setFeedbackSettings(messaging, multiChoice, understanding);
                //fbFragment.setName();
                getSupportFragmentManager().beginTransaction().add(R.id.feedbackFragmentContainer, fbFragment).commit();
            }
        }

        application = (MyApplication)getApplication();
        presenterServer = application.getServer();

//        // Step 4 - Setup the listener for this object
//        presenterServer.setCustomObjectListener(new Server.onMessageListener() {
//            @Override
//            public void onObjectReady(String title) {
//                // Code to handle object ready
//            }
//
//            @Override
//            public void onDataLoaded(SingleFeedback feedbackObject) {
//                // Code to handle data loaded from network
//                // Use the data here!
//                Log.d("ABCD","In Presentation!!!");
//            }
//        });


    }

    public void createTextViews(Text t) {
        String textDisplay = null;
        if (t.getSourceFile()!= null){
            try {
                InputStream inputStream = getAssets().open(t.getSourceFile());
                textDisplay = tH.retrieveText(inputStream, t.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            textDisplay = tH.retrieveText(null, t.getText());
        }

        int marginLeft = (int) (screenWidth*(t.getxStart())); //px
        int marginTop = (int) (screenHeight*(t.getyStart())); //px

        //creating a TextView to hold the parsed text
        TextView tV = new TextView(this);
        tV.setText(Html.fromHtml(textDisplay));
        if (t.getFontColour() != null) {
            Log.d("TAG font colour", t.getFontColour());
            tV.setTextColor(Color.parseColor("#"+t.getFontColour()));
        }else {
            tV.setTextColor(Color.parseColor("#"+presentationFile.getDefaults().getFontColour()));
        }
        tV.setMaxWidth(screenWidth);
        tV.setPadding(0,0,10,0);
        tV.setX(marginLeft);
        tV.setY(marginTop);
        tV.setTextSize(t.getFontSize());
        slide.addView(tV);
    }

    public void createImageViews(Image i) {
        Bitmap b = null;
        if (i.getSourceFile() != null){
            try {
                InputStream inputStream = getAssets().open(i.getSourceFile());
                b = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        convert floats to pixels for image dimensions
        int imgWidth = (int) (i.getWidth())*screenWidth;
        int imgHeight = (int) (i.getHeight())*screenHeight;
        ImageView iV = new ImageView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imgWidth, imgHeight);
        //iV.setLayoutParams(layoutParams);
        iV.setImageBitmap(b);
        slide.addView(iV);
    }

    public void createVideoViews(Video v) {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + getResources().getIdentifier(v.getSourceFile(), "raw", getPackageName()));
        VideoView vV = new VideoView(this);
        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(vV);
        vV.setMediaController(mediaController);
        vV.clearFocus();
        vV.requestFocus();
        vV.setVideoURI(uri);
        vV.setX(v.getxStart() * screenWidth);
        vV.setY(v.getyStart() * screenHeight);
        //vV.layout((int) vV.getX(), (int) vV.getY(),(int) vV.getX() + 800, (int) vV.getY() + 500);
        vV.start();
        slide.addView(vV);
    }

    public void createAudioPlayer(Audio a) throws IOException {
        Button button = new Button(this);
        button.setText("Play audio");
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + getResources().getIdentifier(a.getSourceFile(), "raw", getPackageName()));
        final MediaPlayer mP = new MediaPlayer();
        mP.setDataSource(getApplicationContext(), uri);
        mP.prepare();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mP.start();
            }
        });
        slide.addView(button);
    }

    public void addGraphics(Shape s, Polygon p){

//        if (p.sourceFile != null){
//
//                String next[] = {};
//                List<String[]> list = new ArrayList<String[]>();
//
//                try {
//                    CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open(p.sourceFile)));
//                    while(true) {
//                        next = reader.readNext();
//                        if(next != null) {
//                            list.add(next);
//                        } else {
//                            break;
//                        }
//                    }

        ArrayList x = new ArrayList();
        ArrayList y = new ArrayList();
//                    for (int i = 0; i < list.size(); i++) {
//                        x.add(list.get(i)[0]);
////                        y.add(list.get(i)[1]);
//                    }


        GraphicsHandler pH = new GraphicsHandler(this, x, y, p , s, screenWidth, screenHeight);
        pH.draw(canvas);
        slide.addView(pH);

//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//        }
    }

    public void getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        //Set screen params differently if the presenter wants to be able to see feedback
        if (hideFeedback){
            screenWidth = size.x;
            screenHeight = size.y;
        }
        else{
            int tempx = size.x;
            int tempy = size.y;
            double screenWidthDouble = tempx*0.7;
            double screenHeightDouble = tempy-360;
            screenWidth = (int) screenWidthDouble;
            screenHeight = (int) screenHeightDouble;
            feedbackHeight = tempy;
            feedbackWidth = tempx;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                //Show feedback here
                if(!hideFeedback){
                    if (fbFragment.isHidden()) getSupportFragmentManager().beginTransaction().show(fbFragment).commit();
                    else getSupportFragmentManager().beginTransaction().hide(fbFragment).commit();
                    fbFragment.updateStackedBars();
                }
                return true;
            case R.id.action_settings:
                Intent settings_Intent = new Intent(this, SettingsActivity.class);
                startActivity(settings_Intent);
                return true;
            case R.id.action_presenterHelp:
                Intent tutorial_Intent = new Intent(this, PresentationModeTutorial.class);
                startActivity(tutorial_Intent);
                return true;
            case R.id.action_home:
                Intent home_Intent = new Intent(this, HostingWizardActivity.class);
                startActivity(home_Intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_presentation, menu);
        return true;
    }

    public void populateSlides(){
        DocumentInfo docInfo = presentationFile.getDocumentInfo();
        List<Slide> slides = presentationFile.getSlides();
        int numberOfSlides = slides.size();
        Shape shape = null;
        Polygon poly = null;

        for (int i = 0; i< numberOfSlides; i++) {
            int numberOfTexts = slides.get(i).getText().size();
            int numberOfShapes = slides.get(i).getShape().size();
            int numberOfImages = slides.get(i).getImage().size();
            int numberOfAudios = slides.get(i).getAudio().size();
            int numberOfPolygons = slides.get(i).getPolygon().size();
            int numberOfInteractables = slides.get(i).getInteractable().size();
            int numberOfVideos = slides.get(i).getVideo().size();

            slide = new RelativeLayout(this);
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            slide.setLayoutParams(llp);

            if (numberOfTexts > 0){
                for (int j = 0; j< numberOfTexts; j++){
                    Text text = slides.get(i).getText().get(j);
                    createTextViews(text);
                }
            }


            if (numberOfShapes > 0){
                for (int j = 0; j< numberOfShapes; j++){
                    shape = slides.get(i).getShape().get(j);
                    addGraphics(shape, null);
                }
            }

            if (numberOfPolygons > 0){
                for (int j = 0; j< numberOfPolygons; j++){
                    poly = slides.get(i).getPolygon().get(j);
                    addGraphics(null, poly);
                }
            }

            if (numberOfImages > 0){
                for (int j = 0; j< numberOfImages; j++){
                    Image image = slides.get(i).getImage().get(j);
                    createImageViews(image);
                }
            }

            if (numberOfVideos > 0){
                for (int j = 0; j< numberOfVideos; j++){
                    Video video = slides.get(i).getVideo().get(j);
                    createVideoViews(video);
                }
            }

            if (numberOfAudios > 0){
                for (int j = 0; j< numberOfAudios; j++){
                    Audio audio = slides.get(i).getAudio().get(j);
                    try {
                        createAudioPlayer(audio);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (numberOfInteractables > 0){
                for (int j = 0; j< numberOfInteractables; j++){
                    final Interactable interactable = slides.get(i).getInteractable().get(j);
                    Button button = new Button(this);
                    button.setBackgroundColor(Color.TRANSPARENT);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewFlipper.setDisplayedChild(interactable.getTargetSlideID());
                        }
                    });
                    if (interactable.getImage() != null){
                        Image intImage = interactable.getImage();
                        createImageViews(intImage);
                        button.setY(intImage.getyStart()*screenHeight);
                        button.setX(intImage.getxStart() * screenWidth);
                        button.setWidth((int) (intImage.getWidth() * screenWidth));
                        button.setHeight((int) (intImage.getHeight()*screenHeight));
                        slide.addView(button);
                    }
                    if (interactable.getShape() != null) {
                        Shape intShape = interactable.getShape();
                        addGraphics(intShape, null);
                        button.setY(intShape.getyStart()*screenHeight);
                        button.setX(intShape.getxStart()*screenWidth);
                        button.setWidth((int) (intShape.getWidth() * screenWidth));
                        button.setHeight((int) (intShape.getHeight()*screenHeight));
                        slide.addView(button);
                    }
                    //add polygon method too
                }
            }


            viewFlipper.addView(slide);
        }


    }

    public void getPresentation() throws IOException, XmlPullParserException{
        XMLParser parser = new XMLParser();
        InputStream in = null;
        in = getAssets().open("test.xml");
        presentationFile = parser.getPresentation(in);
    }

    public boolean onTouchEvent(MotionEvent touchevent) {

        switch (touchevent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();

                if (lastX < currentX)
                {
                    if (viewFlipper.getDisplayedChild()==0)
                        break;

                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
//                    vf.showNext();
                    viewFlipper.showPrevious();
                }

                if (lastX > currentX)
                {
//                    if (vf.getDisplayedChild()==1)
                    if (viewFlipper.getDisplayedChild()==viewFlipper.getChildCount()-1)
                        break;

                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
//                    vf.showPrevious();
                    viewFlipper.showNext();
                }

                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                float tempX = touchevent.getX();
                int scrollX = (int) (tempX - lastX);

                //vf.scrollBy(scrollX, 0);

                break;
            }

        }

        return false;

    }

}

