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
public class FeedbackJSON {


public String FeedbackJSONGenerate(SingleFeedback feedback){
    JSONObject obj = null;

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

  public SingleFeedback FeedbackJSONParse(String jsonFeedback) {
      JSONObject obj = null;

      try {
          obj = new JSONObject(jsonFeedback) ;
      } catch (JSONException e) {
          e.printStackTrace();
      }

      SingleFeedback feedbackJSONParse = new SingleFeedback();
try{
        feedbackJSONParse.setUUID(obj.getString("UUID"));
        feedbackJSONParse.setSLIDE(obj.getInt("SLIDE"));
        feedbackJSONParse.setSLIDE_ITERATION(obj.getInt("SLIDE_ITERATION"));
        feedbackJSONParse.setQUESTION(obj.getInt("SLIDE_ITERATION"));
        feedbackJSONParse.setABC(obj.getInt("ABC"));
        feedbackJSONParse.setGOOD_MEH_BAD(obj.getInt("GOOD_MEH_BAD"));
        feedbackJSONParse.setTEXT(obj.getString("TEXT"));
        feedbackJSONParse.setTIME_RECEIVED(obj.getLong("TIME_RECEIVED"));
}
catch (JSONException e) {
    e.printStackTrace();
}
//TODO - is this return ok - what with the exceptions that can happen..
      return  feedbackJSONParse;
    }

}
