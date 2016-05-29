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

        fdHandler = new feedbackDatabaseHandler(getBaseContext());
        if (fdHandler == null) {
            Log.d("Server", "empty database");
        }
        //use button to refresh log
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        //show the next slide content
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                feedbackHandler.processIncomingFeedback(server.feedbackString, "Jack");


                //Viewing on the activity
                if(server.feedbackObject.getTEXT() != null){
                    Log.d("testingConnections", server.feedbackObject.getTEXT());
                    testTextView.setText(server.feedbackObject.getTEXT());
                }

                if (server.feedbackObject != null){
                    fdHandler.addFeedbackCollumn(server.feedbackObject);
                }
               // getting whatever is in the database
                feedbackList = fdHandler.getAllFeedback();
                Log.d("testingConnections", "feedbackList size is " + feedbackList);
                //and printing each object as a string
                for (int i = 0; i<feedbackList.size(); i++){
                    feedback = feedbackJSON.FeedbackJSONGenerate(feedbackList.get(i+1));
                    Log.d("This is in the database", feedback);
                }

            }
        });
    }


}
