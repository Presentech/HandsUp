package com.presentech.handsup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by epren on 27/05/2016. As of yet untested.
 */
public class questionJSON {
    private String SLIDEJName = "slideNumber";
    private String QUESTIONJName = "questionNumber";
    private String abcJName = "abcBool";
    private String goodmehbadJName = "gmbBool";
    private String questionTextJName = "QuestionText";
    private JSONObject jo = new JSONObject();
    private JSONArray ja = new JSONArray();

    public String questionCreateJSON(List<SingleQuestion> listIn) {
        for (SingleQuestion singleIn : listIn) {
            try {
                jo.put(SLIDEJName, singleIn.getSLIDE());
                jo.put(QUESTIONJName, singleIn.getQUESTION());
                jo.put(abcJName, singleIn.isAbc());
                jo.put(goodmehbadJName, singleIn.isGoodmehbad());
                jo.put(questionTextJName, singleIn.getQuestionText());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ja.put(jo);
        }
        return jo.toString();
    }

    public List<SingleQuestion> questionParseJSON(String JSONQuestions) {
        double readSLIDE;
        int readQUESTION;
        boolean readabc;
        boolean readgoodmehbad;
        String readquestionText;

        List<SingleQuestion> parsedList = null;
        try {
            JSONArray ja = new JSONArray(JSONQuestions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo;
            try {
                jo = ja.getJSONObject(i);
                readSLIDE = jo.getDouble(SLIDEJName);
                readQUESTION = jo.getInt(QUESTIONJName);
                readabc = jo.getBoolean(abcJName);
                readgoodmehbad = jo.getBoolean(goodmehbadJName);
                readquestionText = jo.getString(questionTextJName);
                parsedList.add(new SingleQuestion(readSLIDE,readQUESTION, readabc, readgoodmehbad, readquestionText )); // Creating a new object and adding it to list - single step
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return parsedList;
    }

}

