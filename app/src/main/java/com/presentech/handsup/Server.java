package com.presentech.handsup;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Noor on 28/05/2016.
 */
public class Server {

    final String TAG = "Server";

    //debugging
    StringBuffer contents = new StringBuffer();
    String feedbackString = new String();

    //Feedback objects
    FeedbackJSON usingJSON = new FeedbackJSON();
    SingleFeedback feedbackObject = new SingleFeedback();

    private onMessageListener listener;

    //Connections
    Socket s;
    Thread t;

    Handler h;
    HandlerThread tx;
    PrintWriter writer;
    String json;

    public Server(){

        this.feedbackString = "";
        this.feedbackObject = null;

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket socket = null;
                try {
                    socket = new ServerSocket(Config.PORT);

                    Log.d(TAG, "Opened connection");
                    while (!Thread.interrupted() && !socket.isClosed()) {
                        s = socket.accept();
                        new Thread(new ConnectionHandler(Server.this, s.getInputStream())).start();
                        onConnection(s.getInetAddress().getHostAddress());
                        writer = new PrintWriter(s.getOutputStream());
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            Log.e(TAG, "error", e);
                        }
                    }
                }
            }
        }

        );

        tx = new HandlerThread("Server");
        tx.start();
        h = new Handler(tx.getLooper());
        this.listener = null;
        t.start();
    }



    //This interface defines the type of messages I want to communicate to my owner
    public interface onMessageListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onObjectReady(String title);
        // or when data has been loaded
        public void onDataLoaded(SingleFeedback feedback);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(onMessageListener listener) {
        this.listener = listener;
    }

    public void onSend(String msg) {
        Log.d(TAG, "Sending: " + msg);
        json = msg;
        h.post(new Runnable() {
            @Override
            public void run() {
                writer.println(json);
                writer.flush();
                try {
                    s.getOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onMessage(final int c) {
        contents.append(Character.toChars(c));
        if (contents.toString().contains("}")) {
            feedbackString = contents.toString();
            Log.d("Server", "Message received: " + feedbackString);
            feedbackObject = usingJSON.FeedbackJSONParse(feedbackString);
            contents.setLength(0);
            //fire listener here
            Log.d("Server", "Listener fired");
            listener.onDataLoaded(feedbackObject);
        }
    }

    public void onConnection(final String s) {
        //contents.append("New client connected from ").append(s).append("<br>");
        Log.d("Server", "New client connected from "+ s);
    }

    class ConnectionHandler implements Runnable {

        String TAG = "ConnectionHandler";
        Reader in;
        Server ctx;

        public ConnectionHandler(Server ctx, InputStream in) {
            this.in = new BufferedReader(new InputStreamReader(in));
            this.ctx = ctx;
        }

        @Override
        public void run() {
            int c;
            try {
                while (true) {
                    c = in.read();
                    if (c == -1) continue;
                    ctx.onMessage(c);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
