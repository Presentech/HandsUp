package com.presentech.handsup.presentationfile;

import com.presentech.handsup.SingleFeedback;

/**
 * Created by epren on 27/05/2016.
 * This is the only code that should alter counts of feedback (clearing or adding to), or call the SQL function
 */
public class IncomingFeedbackHandler {

    private static final Object MUTEX = new Object();

    public void processIncomingFeedback(SingleFeedback feedbackin) {


//Decide what to do with the feedback

//Test to see if abc feedback

        //Test to see if G/M/B feedback
        if (feedbackin.getGOOD_MEH_BAD()!=-1)
        {
            //Process good meh bad stuff
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
            //Update SQL Database.

        }
    }

    public static void doSomethingStatic() {
        synchronized(MUTEX) {
            // Do something static
        }
    }
}