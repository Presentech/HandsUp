package com.presentech.handsup;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class messagingFragment extends Fragment{

    LinearLayout messagingLayout;
    ListView listView;
    ArrayAdapter<String> listAdapter;

    ArrayList<String> messageList = new ArrayList<>();
    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        messagingLayout = (LinearLayout) inflater.inflate(R.layout.fragment_messaging, container, false);

        Log.d("ABCD", "Hello");
        ArrayList<String> messageList = Getlist();
        listView = (ListView) messagingLayout.findViewById(R.id.messagingList);
        listView.setStackFromBottom(true);
        listAdapter = new ArrayAdapter<>(getActivity(), R.layout.message_layout, messageList);
        listView.setAdapter(listAdapter);
        return messagingLayout;
    }

    public void reset(){
        listView.setAdapter(null);
    }

    private ArrayList<String> Getlist(){

        messageList.add(" ");
        return messageList;
    }

    public void updateMessages(SingleFeedback feedback) {
        String message = feedback.getTEXT();
        messageList.add(message);
        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);
        View tempView = new View(getActivity());
        messagingLayout.addView(tempView);
    }
}
