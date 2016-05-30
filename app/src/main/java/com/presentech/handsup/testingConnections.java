package com.presentech.handsup;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class testingConnections extends Activity {

    //UI
    TextView testTextView;
    Button refreshButton;

    //Connectivity requirements
    MyApplication application;
    Server server;

    //Feedback
    IncomingFeedbackHandler feedbackHandler;
    feedbackDatabaseHandler fdHandler;
    FeedbackJSON feedbackJSON = new FeedbackJSON();
    List<SingleFeedback> feedbackList = new ArrayList<SingleFeedback>();
    String feedback = "";
    SingleFeedback feedbackObjectRx = new SingleFeedback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_connections);

        testTextView = (TextView)findViewById(R.id.testTextView);
        refreshButton = (Button)findViewById(R.id.refreshButton);

        feedbackHandler = new IncomingFeedbackHandler(getApplicationContext());
        //Using methods from the server to receive
        application = (MyApplication)getApplication();
        server = application.getServer();

        SQLiteDatabase.CursorFactory factory = new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                return null;
            }
        };

        fdHandler = new feedbackDatabaseHandler(getBaseContext(), "");
        if (fdHandler == null) {
            Log.d("Server", "empty database");
        }
        //use button to refresh log
        addListenerOnButton();

        // Step 4 - Setup the listener for this object
        server.setCustomObjectListener(new Server.onMessageListener() {
            @Override
            public void onObjectReady(String title) {
                // Code to handle object ready
            }
            @Override
            public void onDataLoaded(SingleFeedback feedbackObject) {
                // Code to handle data loaded from network
                // Use the data here!
                feedbackObjectRx = feedbackObject;
                Log.d("testingConnections", "In Presentation!!!");
                if(feedbackObjectRx.getTEXT() != null){
                    Log.d("testingConnections", feedbackObjectRx.getTEXT());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testTextView.setText(feedbackObjectRx.getTEXT());
                        }
                    });

                }
            }
        });
    }

    public void addListenerOnButton() {
        //show the next slide content
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                server.onSend("blah");
            }
        });
    }


}
