package com.presentech.handsup;

import android.app.Application;

/**
 * Created by Noor on 27/05/2016.
 */
public class MyApplication extends Application{

    Client client;
    Server server;

    @Override
    public void onCreate (){
        super.onCreate();
        client = new Client();
        server = new Server();

    }

    public Client getClient(){
        return client;
    }

    public Server getServer(){
        return server;
    }


}
