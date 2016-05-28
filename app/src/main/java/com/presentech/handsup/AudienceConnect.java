package com.presentech.handsup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AudienceConnect extends Activity {

    EditText host;
    EditText in;
    TextView main;
    Button connect;

    Client client;
    MyApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_connect);
        host = (EditText) findViewById(R.id.hostText);
        main = (TextView) findViewById(R.id.main);
        connect = (Button) findViewById(R.id.connect);

        //host.setText("Host");
        addListenerOnButton();
        application = (MyApplication)getApplication();
        client = application.getClient();



    }

    public void addListenerOnButton() {
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d("Noor", "Tap");
                client.onConnect(host.getText().toString());
                main.setText(client.log);
                feedbackActivity();


            }
        });
    }

    public void feedbackActivity(){
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }
}
