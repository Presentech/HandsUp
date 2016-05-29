package com.presentech.handsup;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    //Connections
    Socket socket;
    Thread t;

    public Server(){
        //main.setText(Html.fromHtml(contents.toString()));
        //header.setText("Host: " + getIpAddress());
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
                        Socket s = socket.accept();
                        new Thread(new ConnectionHandler(Server.this, s.getInputStream())).start();
                        onConnection(s.getInetAddress().getHostAddress());
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

        t.start();
    }


    public void onMessage(final int c) {
        contents.append(Character.toChars(c));
        if (contents.toString().contains("}")){
            feedbackString = contents.toString();
            Log.d("Server", "Message received: " + feedbackString);
            feedbackObject = usingJSON.FeedbackJSONParse(feedbackString);
            contents.setLength(0);
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
                   // Log.d(TAG, "Message: " + c);
                    final int finalC = c;
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
