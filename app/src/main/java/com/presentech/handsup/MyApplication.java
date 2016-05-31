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

    @Override
    public void onCreate (){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
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
