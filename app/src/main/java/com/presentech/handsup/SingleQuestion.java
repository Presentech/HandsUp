package com.presentech.handsup;

/**
 * Created by epren on 27/05/2016.
 */

/**
 * Question data for each client.
 */
// http://www.survivingwithandroid.com/2013/10/android-json-tutorial-create-and-parse.html - JSON guide

public class SingleQuestion {

   private  double SLIDE;
    private  int QUESTION;
    private boolean abc;
    private boolean goodmehbad;
    private String questionText;

    // Empty constructor - should be used.
    public SingleQuestion(double inSlide, int inQuestion, boolean inabc, boolean inGoodMehBad, String inQuestionText ){
        this.SLIDE = inSlide;
        this.QUESTION = inQuestion;
        this.abc = inabc;
        this.goodmehbad = inGoodMehBad;
        this.questionText = inQuestionText;
    }

    public double getSLIDE() {
        return SLIDE;
    }

    public void setSLIDE(double SLIDE) {
        this.SLIDE = SLIDE;
    }

    public int getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(int QUESTION) {
        this.QUESTION = QUESTION;
    }

    public boolean isAbc() {
        return abc;
    }

    public void setAbc(boolean abc) {
        this.abc = abc;
    }

    public boolean isGoodmehbad() {
        return goodmehbad;
    }

    public void setGoodmehbad(boolean goodmehbad) {
        this.goodmehbad = goodmehbad;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}



