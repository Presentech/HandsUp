package com.presentech.handsup;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Noor on 27/05/2016.
 */
public class MyApplication extends Application{

    Client client;
    Server server;
    feedbackDatabaseHandler data_base;

    @Override
    public void onCreate (){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        client = new Client();
        server = new Server();
        data_base = new feedbackDatabaseHandler(this, "DataBase");
    }

    public Client getClient(){
        return client;
    }

    public Server getServer(){
        return server;
    }

    public feedbackDatabaseHandler getDB() {return data_base;}
}
