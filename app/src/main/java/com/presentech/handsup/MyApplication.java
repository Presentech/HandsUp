package com.presentech.handsup;

import android.app.Application;

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
