package com.presentech.handsup;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class liveFeedbackFragment extends Fragment{

    public SingleFeedback[] feedbackArray = new SingleFeedback[10];
    String[] messagesTemp = {"Woo Electronics!", "Boo Electronics!", "SwEng was so much fun OMFG xox", "See you in October!"};

    public int a, b, c;
    private int numberOfFeedbacks = 0;
    View stackedBarsABCView, stackedBarsUndView, messagingView;
    public int screenWidth, screenHeight, lastBar, barWidth, feedbackHeight;
    RelativeLayout feedbackLayout;

    boolean fragUnderstanding, fragMultiChoice, fragMessaging;
    stackedBarsFragment ABCBarsFragment = null, understandingBarsFragment = null;
    messagingFragment slideFeedbackMessagesFragment =null;
    private static final Object MUTEX = new Object();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        feedbackLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_live_feedback, container, false);
        initFeedback(savedInstanceState);
        return feedbackLayout;
    }

    public void setFeedbackSettings(boolean messaging, boolean multiChoice, boolean understanding){
        fragUnderstanding = understanding;
        fragMessaging = messaging;
        fragMultiChoice = multiChoice;
        if (fragUnderstanding) numberOfFeedbacks++;
        if (fragMessaging) numberOfFeedbacks++;
        if (fragMultiChoice) numberOfFeedbacks++;
    }
    private void initFeedback(Bundle savedInstanceState){
        setFeedbackArray(feedbackArray);
        createFragments(savedInstanceState);
        setLayoutParams();
        addFragments();
        //updateStackedBars();
        //updateMessages();
    }
    public void setName(){
        ABCBarsFragment.setName("Understanding");
        ABCBarsFragment.setName("ABC");

    }
    private void addFragments(){
        if (fragMultiChoice){
            getChildFragmentManager().beginTransaction().add(R.id.fragmentContainerFBBarABC, ABCBarsFragment).commit();
            ABCBarsFragment.setScreenParams(feedbackHeight, barWidth);
        }
        if (fragUnderstanding){
            getChildFragmentManager().beginTransaction().add(R.id.fragmentContainerFBBarUnderstanding, understandingBarsFragment).commit();
            understandingBarsFragment.setScreenParams(feedbackHeight, barWidth);
        }
        if (fragMessaging){
            getChildFragmentManager().beginTransaction().add(R.id.fragmentContainerMessaging, slideFeedbackMessagesFragment).commit();
        }
    }

    private void createFragments(Bundle savedInstanceState){
        //Create reference to views
        stackedBarsABCView  = feedbackLayout.findViewById(R.id.fragmentContainerFBBarABC);
        stackedBarsABCView  = feedbackLayout.findViewById(R.id.fragmentContainerFBBarABC);
        stackedBarsUndView = feedbackLayout.findViewById(R.id.fragmentContainerFBBarUnderstanding);
        messagingView = feedbackLayout.findViewById(R.id.fragmentContainerMessaging);


        if ((stackedBarsABCView != null) && (fragMultiChoice)) {
            // If we're being restored from a previous state, don't do anything
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            ABCBarsFragment = new stackedBarsFragment();
            Log.d("ABCD", "Stuff");
            // Add the fragment to the 'fragment_container' FrameLayout
            lastBar = R.id.fragmentContainerFBBarABC;
        }
        if ((stackedBarsUndView != null) && (fragUnderstanding)) {
            // If we're being restored from a previous state, don't do anything
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            understandingBarsFragment = new stackedBarsFragment();
            Log.d("ABCD", "Stuff2");
            // Add the fragment to the 'fragment_container' FrameLayout
            lastBar = R.id.fragmentContainerFBBarUnderstanding;
        }
        if ((messagingView != null) && (fragMessaging)) {
            // If we're being restored from a previous state, don't do anything
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            Log.d("ABCD","Create Messaging");
            // Create a new Fragment to be placed in the activity layout
            slideFeedbackMessagesFragment = new messagingFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
        }

    }

    private void setLayoutParams(){
        double feedbackWidthDouble = screenWidth * 0.25;
        double barWidthDouble = feedbackWidthDouble * 0.2;
        double messagingWidthDouble;
        if (!(fragMultiChoice)&&(fragUnderstanding)) messagingWidthDouble= feedbackWidthDouble * 0.75;
        else messagingWidthDouble = feedbackWidthDouble;

        int feedbackWidth = (int) feedbackWidthDouble;
        int messagingWidth = (int) messagingWidthDouble;
        barWidth = screenWidth;
        feedbackHeight = 130* numberOfFeedbacks;


        RelativeLayout.LayoutParams fblp = new RelativeLayout.LayoutParams(screenWidth, feedbackHeight);
        RelativeLayout.LayoutParams blpABC = new RelativeLayout.LayoutParams(screenWidth, 130);
        RelativeLayout.LayoutParams blpUnd = new RelativeLayout.LayoutParams(screenWidth, 130);
        RelativeLayout.LayoutParams mlp = new RelativeLayout.LayoutParams(screenWidth, 130);
        fblp.addRule(RelativeLayout.ALIGN_BOTTOM);
        fblp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        feedbackLayout.setLayoutParams(fblp);

        //If only displaying messaging
        if ((fragMessaging) && (!fragMultiChoice) && (!fragUnderstanding)) mlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            //If displaying messaging and one Bar
        else if ((fragMessaging) && (!(fragMultiChoice && fragUnderstanding))) {
            blpABC.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            blpUnd.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mlp.addRule(RelativeLayout.ABOVE, lastBar);
        }
        //All 3
        else if (fragMessaging){
            blpABC.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            blpUnd.addRule(RelativeLayout.ABOVE, R.id.fragmentContainerFBBarABC);
            mlp.addRule(RelativeLayout.ABOVE, lastBar);
        }
        //Displaying just 1 bars
        if ((!fragMessaging) && (!(fragMultiChoice && fragUnderstanding))){
            blpABC.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            blpUnd.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        else if ((!fragMessaging) && (fragMultiChoice && fragUnderstanding)){
            blpABC.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            blpUnd.addRule(RelativeLayout.ABOVE, R.id.fragmentContainerFBBarABC);

        }

        blpABC.addRule(RelativeLayout.CENTER_HORIZONTAL);
        blpUnd.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mlp.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //If ragment present then set new layout parameters
        //If ragment present then set new layout parameters
        if (fragMultiChoice) stackedBarsABCView.setLayoutParams(blpABC);
        if (fragUnderstanding) stackedBarsUndView.setLayoutParams(blpUnd);
        if (fragMessaging) messagingView.setLayoutParams(mlp);

    }

    public void reset(){
        if (fragMultiChoice) ABCBarsFragment.reset("Question Response");
        if (fragUnderstanding) understandingBarsFragment.reset("Reaction");
        if (fragMessaging) slideFeedbackMessagesFragment.reset();
    }

    public void updateFeedback(SingleFeedback feedbackObject){
        //Create new fragments to replace the old ones

        //Replace the fragment currently in the fragment container with the new fragments
        if (ABCBarsFragment != null){
            Log.d("ABCD", "Do Something");
            //If Question changed then update bars
            if (feedbackObject.getABC() != -1) {
                synchronized (MUTEX){
                    ABCBarsFragment.setFeedbackResponse(feedbackObject);
                    ABCBarsFragment.updateBarHeight("Question Response");
                    getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBBarABC, ABCBarsFragment).commit();
                }
            }
        }
        if (understandingBarsFragment != null){
            Log.d("ABCD", "Do Something Else");
            //Draw these and set random feedback values
            if (feedbackObject.getGOOD_MEH_BAD() != -1){
                synchronized (MUTEX){
                    understandingBarsFragment.setFeedbackResponse(feedbackObject);
                    understandingBarsFragment.updateBarHeight("Level of Understanding");
                    getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerFBBarUnderstanding, understandingBarsFragment).commit();
                }
            }
        }
        //Replace the fragment currently in the fragment container with the new fragments
        if (slideFeedbackMessagesFragment != null){
            //Draw these and set random feedback values
            if (feedbackObject.getTEXT() != null) slideFeedbackMessagesFragment.updateMessages(feedbackObject);
            getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerMessaging, slideFeedbackMessagesFragment).commit();
        }

    }


    public void setFeedbackArray(SingleFeedback[] feedback){
        Random r = new Random();
        int rV;
        feedbackArray = feedback;
        /*Set some example ojects that would be expected to recieve*/
        for(int i=0; i<10;i++){
            rV = r.nextInt(4 - 1) + 1;
            feedbackArray[i] = new SingleFeedback("abc",1.00,1,1,rV,rV,"abc",123L);
        }
    }

    public void setScreenParams(int height, int width){
        screenHeight = height;
        screenWidth = width;
    }
}
