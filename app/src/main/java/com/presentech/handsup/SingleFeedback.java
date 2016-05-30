package com.presentech.handsup;

/**
 * This file comntains a structure with all the required fields for feedback reception prior to parsing to SQL.
 * NOTE: To give an idea of the place of this file within the user flow I believe that its use might be
 * contained within the process for achieving the recieption of feedback JSON and its storge in SQL
 * e.g. JSON>>>InstanceOfThisObject>>>SQL
 */
// http://www.survivingwithandroid.com/2013/10/android-json-tutorial-create-and-parse.html - JSON guide

public class SingleFeedback {

    String UUID;
    double SLIDE;/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
    int SLIDE_ITERATION;/** Slide iteration - for when a slide is visited multiple times.*/
    int QUESTION;/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
    int ABC; /** -1 not sent, 1 a, 2 - b, 3 -c*/
    int GOOD_MEH_BAD; /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
    String TEXT; /** If anything but null the text should be interprted - acts as flag for text input.*/
    long TIME_RECEIVED;

    // Empty constructor - should be used.
    public SingleFeedback(){
        this.UUID = null;
        this.SLIDE = -1;/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
        this.SLIDE_ITERATION = -1;/** Slide iteration - for when a slide is visited multiple times.*/
        this.QUESTION = -1;/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
        this.ABC = -1 ; /** -1 not sent, 1 a, 2 - b, 3 -c*/
        this.GOOD_MEH_BAD = -1; /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
        this.TEXT = null; /** If anything but null the text should be interprted - acts as flag for text input.*/
        this.TIME_RECEIVED = -1;
    }

    // constructor - recommended not to use this, create a 'blank feedback then set UID and other required value with getter/setter.
    public SingleFeedback(String inUUD, double inSlide, int inSlideIteration, int inQuestion, int inABC, int inGoodMehBad, String inText, long inTimeReceived){
       this.UUID = inUUD;
       this.SLIDE = inSlide;/** Slide number, float is smaller than double and can hold branches to sufficient accuracy (e.g. slide 1.51 as 1.51)*/
       this.SLIDE_ITERATION = inSlideIteration;/** Slide iteration - for when a slide is visited multiple times.*/
       this.QUESTION = inQuestion;/** In case we have multiple sections on a slide. Might rip this out, but design is for worst-case.*/
       this.ABC = inABC ; /** -1 not sent, 1 a, 2 - b, 3 -c*/
       this.GOOD_MEH_BAD = inGoodMehBad; /** -1 not sent, 1 good, 2 - meh, 3 - bad*/
       this.TEXT = inText; /** If anything but null the text should be interprted - acts as flag for text input.*/
       this.TIME_RECEIVED = inTimeReceived;
    }

    public String getUUID() {return UUID;}

    public void setUUID(String UUID) {this.UUID = UUID;}

    public double getSLIDE() {
        return SLIDE;
    }

    public void setSLIDE(double SLIDE) {
        this.SLIDE = SLIDE;
    }

    public int getSLIDE_ITERATION() {
        return SLIDE_ITERATION;
    }

    public void setSLIDE_ITERATION(int SLIDE_ITERATION) {
        this.SLIDE_ITERATION = SLIDE_ITERATION;
    }

    public int getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(int QUESTION) {
        this.QUESTION = QUESTION;
    }

    public int getABC() {
        return ABC;
    }

    public void setABC(int ABC) {
        this.ABC = ABC;
    }

    public int getGOOD_MEH_BAD() {
        return GOOD_MEH_BAD;
    }

    public void setGOOD_MEH_BAD(int GOOD_MEH_BAD) {
        this.GOOD_MEH_BAD = GOOD_MEH_BAD;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

    public long getTIME_RECEIVED() {
        return TIME_RECEIVED;
    }

    public void setTIME_RECEIVED(long TIME_RECEIVED) {
        this.TIME_RECEIVED = TIME_RECEIVED;
    }
}


