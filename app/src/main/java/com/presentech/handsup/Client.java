package com.presentech.handsup;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noor on 27/05/2016.
 */
public class Client {

    Socket s;
    PrintWriter writer;
    HandlerThread handlerThread;
    Handler h;

    String log = "";
    String wordsToSend;
    String hostAddress;
    StringBuffer out = new StringBuffer();
    final String TAG = "Client";

    StringBuffer rxString = new StringBuffer();
    String messageFromServer = new String();
    int slideNumber;

    List<SingleQuestion> singleQuestionList = new ArrayList<>();
    questionJSON questionJSON = new questionJSON();

    private onMessageListener listener;

    public Client() {
        handlerThread = new HandlerThread("Client");
        handlerThread.start();
        h = new Handler(handlerThread.getLooper());
    }

    //This interface defines the type of messages I want to communicate to my owner
    public interface onMessageListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onObjectReady(String title);
        // or when data has been loaded
        public void onDataLoaded( List<SingleQuestion> singleQuestions, int i);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(onMessageListener listener) {
        this.listener = listener;
    }

    public void onSend(String msg) {
        Log.d(TAG, "Sending: " + msg);
        wordsToSend = msg;
        h.post(new Runnable() {

            @Override
            public void run() {
                Log.d(TAG, "Writing: " + wordsToSend);
                writer.println(wordsToSend);
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
        rxString.append(Character.toChars(c));
        if (rxString.toString().contains("}]") && rxString.toString().contains("}](")) {

            if (rxString.charAt(rxString.length() - 1) == ')') {

                String temp = rxString.substring(rxString.indexOf("(")+1, rxString.indexOf(")"));
                slideNumber =  Integer.parseInt(temp);
                Log.d("Split in rxString", temp);
                if (singleQuestionList != null){
                    listener.onDataLoaded(singleQuestionList, slideNumber);
                }

                rxString.setLength(0);
            }
        } else if (rxString.toString().contains("}]")){
            messageFromServer = rxString.toString();
            Log.d("Client", "Message received from server: " + messageFromServer);
                singleQuestionList = questionJSON.questionParseJSON(messageFromServer);
                Log.d("Client", "Listener fired");


        }


        };

    public String onConnect(String host) {
        hostAddress = host;
        final String ip = hexToIp(hostAddress.toUpperCase());
        out.append("Connecting to ").append(ip).append("\n\r");
        h.post(new Runnable() {
            @Override
            public void run() {
                try {
                    s = new Socket();
                    s.connect(new InetSocketAddress(ip, Config.PORT));
                    writer = new PrintWriter(s.getOutputStream());
                    out.append("Connected\n");

                    //adding server to client stuff
                    new Thread(new ConnectionHandler(Client.this, s.getInputStream())).start();


                } catch (IOException e) {
                    Log.e(TAG, "Error", e);
                }
            }
        });
        log = out.toString();
        return log;
    }

    class ConnectionHandler implements Runnable {

        String TAG = "ConnectionHandler";
        Reader in;
        Client ctx;

        public ConnectionHandler(Client ctx, InputStream in) {
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

    public static String hexToIp(String hex) {
        String ip = "";
        for (int i = hex.length() - 2; i >= 0; i = i - 2) {
            ip = ip + Integer.valueOf(hex.substring(i, i + 2), 16) + ".";
        }
        return ip.substring(0, ip.length() - 1);
    }
}

