package com.presentech.handsup;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/** Created with help form https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html */
//TODO stop JSON illegal character injection - test this . Might be worth using Gson package
/**
 * Created by epren on 13/05/2016.
 */

// "{"theTeam":[{"teamId":"1","status":"pending"},{"teamId":"2","status":"member"},{"teamId":"3","status":"member"},{"teamId":"4","status":"pending"}]}"

public  class FeedbackJSON {

//Construct this in the Audience FeedbackActivity to send over to the Presenter side
public static String FeedbackJSONGenerate(SingleFeedback feedback){
    JSONObject obj = new JSONObject();
    try {
    obj.put("UUID", feedback.getUUID());
    obj.put("SLIDE", feedback.getSLIDE());
    obj.put("SLIDE_ITERATION", feedback.getSLIDE_ITERATION());
    obj.put("QUESTION", feedback.getQUESTION());
    obj.put("ABC", feedback.getABC());
    obj.put("GOOD_MEH_BAD", feedback.getGOOD_MEH_BAD());
    obj.put("TEXT", feedback.getTEXT());
    obj.put("TIME_RECEIVED", feedback.getTIME_RECEIVED());
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return obj.toString();
}
//Use this to regenerate the feedback object on the Pesenter side for storing and live feedback
  public static SingleFeedback FeedbackJSONParse(String jsonFeedback) {
      JSONObject obj = new JSONObject();

      try {
          obj = new JSONObject(jsonFeedback) ;
      } catch (JSONException e) {
          e.printStackTrace();
      }

      SingleFeedback feedbackJSONParse = new SingleFeedback();


try { feedbackJSONParse.setUUID(obj.getString("UUID")); }
catch (JSONException e) {feedbackJSONParse.setUUID(null);}
      try {feedbackJSONParse.setSLIDE(obj.getInt("SLIDE"));}
      catch (JSONException e) { feedbackJSONParse.setSLIDE(-1);}
      try {feedbackJSONParse.setSLIDE_ITERATION(obj.getInt("SLIDE_ITERATION"));}
      catch (JSONException e) {feedbackJSONParse.setSLIDE_ITERATION(-1);}
      try {feedbackJSONParse.setQUESTION(obj.getInt("SLIDE_ITERATION"));}
      catch (JSONException e) {feedbackJSONParse.setQUESTION(-1);}
      try {feedbackJSONParse.setABC(obj.getInt("ABC"));}
      catch (JSONException e) {feedbackJSONParse.setABC(-1);}
      try {feedbackJSONParse.setQUESTION(obj.getInt("QUESTION"));}
      catch (JSONException e) {feedbackJSONParse.setQUESTION(-1);}
      try {feedbackJSONParse.setGOOD_MEH_BAD(obj.getInt("GOOD_MEH_BAD"));}
      catch (JSONException e) {feedbackJSONParse.setGOOD_MEH_BAD(-1);}
      try {feedbackJSONParse.setTIME_RECEIVED(obj.getLong("TIME_RECEIVED"));}
      catch (JSONException e) {feedbackJSONParse.setTIME_RECEIVED(-1);}
      try {feedbackJSONParse.setTEXT(obj.getString("TEXT"));}
      catch (JSONException e) {feedbackJSONParse.setTEXT(null);}

      return  feedbackJSONParse;
    }









}
