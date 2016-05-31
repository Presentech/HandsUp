package com.presentech.handsup;


import android.app.Application;
import android.content.SharedPreferences;

public class HandsUpApplication extends Application{

    SharedPreferences sharedPreferences;
    public static String PREF_NAME = "pref";
    public static String PREF_USERNAME = "username";
    public static String PREF_PASSWORD = "password";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);

        //Added default username and password in case of first login
        if(sharedPreferences.getString(PREF_USERNAME,"").equalsIgnoreCase("")){
            sharedPreferences.edit().putString(PREF_USERNAME,"a").commit();
            sharedPreferences.edit().putString(PREF_PASSWORD,"b").commit();
        }

    }
}
