package com.presentech.handsup;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

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

    public Client() {
        handlerThread = new HandlerThread("Client");
        handlerThread.start();
        h = new Handler(handlerThread.getLooper());
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

                } catch (IOException e) {
                    Log.e(TAG, "Error", e);
                }
            }
        });
        log = out.toString();
        return log;
    }

    public static String hexToIp(String hex) {
        String ip = "";
        for (int i = hex.length() - 2; i >= 0; i = i - 2) {
            ip = ip + Integer.valueOf(hex.substring(i, i + 2), 16) + ".";
        }
        return ip.substring(0, ip.length() - 1);
    }
}

