package com.presentech.handsup;

import android.content.Context;

import com.presentech.handsup.SingleFeedback;

/**
 * Created by epren on 27/05/2016.
 * This is the only code that should alter counts of feedback (clearing or adding to), or call the SQL function
 */
public class IncomingFeedbackHandler {



    private static final Object MUTEX = new Object();

    public  IncomingFeedbackHandler(Context context) {
        feedbackDatabaseHandler database;
                                       //TODO What goes here??
  database = new  feedbackDatabaseHandler(context);
        database.deleteTable();


    }

    public void processIncomingFeedback(String textFeedbackIn, String nameOfSQLDatabase) {
    SingleFeedback feedbackin;
    feedbackin  =   FeedbackJSON.FeedbackJSONParse(textFeedbackIn);

//Decide what to do with the feedback
//Test to see if abc feedback
        if (feedbackin.getABC()!=-1)
        {
            //Process text
            synchronized(MUTEX) {
                //Update any shared values relating to text.
            }
        }
        //Test to see if G/M/B feedback
        else if (feedbackin.getGOOD_MEH_BAD()!=-1)
        {
            //Process text
            synchronized(MUTEX) {
                //Update any shared values relating to text.
            }
        }
        //Test to see if text feedback

        if (feedbackin.getTEXT()!=null)
        {
            //Process text
            synchronized(MUTEX) {
                //Update any shared values relating to text.
            }

        }

        //its none of them. This would be a good point to have a program error log

        synchronized(MUTEX) {
            //nameOfSQLDatabase;
        }
    }

    public static void doSomethingStatic() {
        synchronized(MUTEX) {
            // Do something static
        }
    }
}