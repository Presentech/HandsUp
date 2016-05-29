package com.presentech.handsup;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.presentech.handsup.presentationfile.Audio;
import com.presentech.handsup.presentationfile.DocumentInfo;
import com.presentech.handsup.presentationfile.Image;
import com.presentech.handsup.presentationfile.Interactable;
import com.presentech.handsup.presentationfile.Polygon;
import com.presentech.handsup.presentationfile.PresentationFile;
import com.presentech.handsup.presentationfile.Shape;
import com.presentech.handsup.presentationfile.Slide;
import com.presentech.handsup.presentationfile.Text;
import com.presentech.handsup.presentationfile.Video;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noor.
 * Updated to include autoflip handler (by Jay)
 */

public class PresentationActivity extends AppCompatActivity {


    int screenWidth;
    int screenHeight;
    PresentationFile presentationFile;
    String mode = "PRESENTER";
    TextHandler tH = new TextHandler();
    RelativeLayout slide = null;
    Canvas canvas = new Canvas();
    //the presentation container
    private ViewFlipper viewFlipper;
    private float lastX;
    private navDrawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        setTitle(R.string.presentation_container);
        //Bundle b = this.getIntent().getExtras();
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
        getScreenSize();
        populateSlides();
        SharedPreferences sharedPreferences = getSharedPreferences(SlideContentTimingsActivity.PREF_KEY_NAME, MODE_PRIVATE);
        int duration = sharedPreferences.getInt(SlideContentTimingsActivity.PREF_KEY_DURATION, 0);
        boolean isAdvanceActive = sharedPreferences.getBoolean(SlideContentTimingsActivity.PREF_KEY_ADVANCE_CHECKED, false);
        boolean isLoopActive = sharedPreferences.getBoolean(SlideContentTimingsActivity.PREF_KEY_LOOP_CHECKED, false);

        if (duration != 0 && isAdvanceActive) {
            setAutomaticHandler(duration, isLoopActive);
        }
        //setAutomaticHandler(3);
    }

    private void setAutomaticHandler(final int duration, final boolean isInLoop) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewFlipper.getDisplayedChild() != viewFlipper.getChildCount() - 1) {

                    viewFlipper.setInAnimation(PresentationActivity.this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(PresentationActivity.this, R.anim.slide_out_to_left);
                    viewFlipper.showNext();
                    setAutomaticHandler(duration, isInLoop);
                } else {
                    if (isInLoop) {
                        viewFlipper.setDisplayedChild(0);
                    }
                    setAutomaticHandler(duration, isInLoop);
                }
            }
        }, duration * 1000);
    }




    public void createTextViews(Text t) {
        String textDisplay = null;
        if (t.getSourceFile() != null) {
            try {
                InputStream inputStream = getAssets().open(t.getSourceFile());
                textDisplay = tH.retrieveText(inputStream, t.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            textDisplay = tH.retrieveText(null, t.getText());
        }

        int marginLeft = (int) (screenWidth * (t.getxStart())); //px
        int marginTop = (int) (screenHeight * (t.getyStart())); //px

        //creating a TextView to hold the parsed text
        TextView tV = new TextView(this);
        tV.setText(Html.fromHtml(textDisplay));
        if (t.getFontColour() != null) {
            Log.d("TAG font colour", t.getFontColour());
            tV.setTextColor(Color.parseColor("#" + t.getFontColour()));
        } else {
            tV.setTextColor(Color.parseColor("#" + presentationFile.getDefaults().getFontColour()));
        }
        tV.setMaxWidth(screenWidth);
        tV.setPadding(0, 0, 10, 0);
        tV.setX(marginLeft);
        tV.setY(marginTop);
        tV.setTextSize(t.getFontSize());
        slide.addView(tV);
    }

    public void createImageViews(Image i) {
        Bitmap b = null;
        if (i.getSourceFile() != null) {
            try {
                InputStream inputStream = getAssets().open(i.getSourceFile());
                b = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        convert floats to pixels for image dimensions
        int imgWidth = (int) (i.getWidth()) * screenWidth;
        int imgHeight = (int) (i.getHeight()) * screenHeight;
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

    public void addGraphics(Shape s, Polygon p) {

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


        GraphicsHandler pH = new GraphicsHandler(this, x, y, p, s, screenWidth, screenHeight);
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
        screenWidth = size.x;
        screenHeight = size.y;
    }

    public void populateSlides() {
        DocumentInfo docInfo = presentationFile.getDocumentInfo();
        List<Slide> slides = presentationFile.getSlides();
        int numberOfSlides = slides.size();
        Shape shape = null;
        Polygon poly = null;

        for (int i = 0; i < numberOfSlides; i++) {
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

            if (numberOfTexts > 0) {
                for (int j = 0; j < numberOfTexts; j++) {
                    Text text = slides.get(i).getText().get(j);
                    createTextViews(text);
                }
            }


            if (numberOfShapes > 0) {
                for (int j = 0; j < numberOfShapes; j++) {
                    shape = slides.get(i).getShape().get(j);
                    addGraphics(shape, null);
                }
            }

            if (numberOfPolygons > 0) {
                for (int j = 0; j < numberOfPolygons; j++) {
                    poly = slides.get(i).getPolygon().get(j);
                    addGraphics(null, poly);
                }
            }

            if (numberOfImages > 0) {
                for (int j = 0; j < numberOfImages; j++) {
                    Image image = slides.get(i).getImage().get(j);
                    createImageViews(image);
                }
            }

            if (numberOfVideos > 0) {
                for (int j = 0; j < numberOfVideos; j++) {
                    Video video = slides.get(i).getVideo().get(j);
                    createVideoViews(video);
                }
            }

            if (numberOfAudios > 0) {
                for (int j = 0; j < numberOfAudios; j++) {
                    Audio audio = slides.get(i).getAudio().get(j);
                    try {
                        createAudioPlayer(audio);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (numberOfInteractables > 0) {
                for (int j = 0; j < numberOfInteractables; j++) {
                    final Interactable interactable = slides.get(i).getInteractable().get(j);
                    Button button = new Button(this);
                    button.setBackgroundColor(Color.TRANSPARENT);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewFlipper.setDisplayedChild(interactable.getTargetSlideID());
                        }
                    });
                    if (interactable.getImage() != null) {
                        Image intImage = interactable.getImage();
                        createImageViews(intImage);
                        button.setY(intImage.getyStart() * screenHeight);
                        button.setX(intImage.getxStart() * screenWidth);
                        button.setWidth((int) (intImage.getWidth() * screenWidth));
                        button.setHeight((int) (intImage.getHeight() * screenHeight));
                        slide.addView(button);
                    }
                    if (interactable.getShape() != null) {
                        Shape intShape = interactable.getShape();
                        addGraphics(intShape, null);
                        button.setY(intShape.getyStart() * screenHeight);
                        button.setX(intShape.getxStart() * screenWidth);
                        button.setWidth((int) (intShape.getWidth() * screenWidth));
                        button.setHeight((int) (intShape.getHeight() * screenHeight));
                        slide.addView(button);
                    }
                    //add polygon method too
                }
            }
            viewFlipper.addView(slide);
        }
    }

    public void getPresentation() throws IOException, XmlPullParserException {
        XMLParser parser = new XMLParser();
        InputStream in = null;
        in = getAssets().open("test.xml");
        presentationFile = parser.getPresentation(in);
    }


    public boolean onTouchEvent(MotionEvent touchevent) {

        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = touchevent.getX();
                break;
            }

            case MotionEvent.ACTION_UP: {
                float currentX = touchevent.getX();

                if (lastX < currentX) {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
//                    vf.showNext();
                    viewFlipper.showPrevious();
                }

                if (lastX > currentX) {
//                    if (vf.getDisplayedChild()==1)
                    if (viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1)
                        break;

                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
//                    vf.showPrevious();
                    viewFlipper.showNext();
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float tempX = touchevent.getX();
                int scrollX = (int) (tempX - lastX);

                //vf.scrollBy(scrollX, 0);
                break;
            }
        }
        return false;
    }
}

