package com.presentech.handsup;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class PresentationFileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_file_list);

        //Create ListView
        ListView lv;
        String sdCardLocation = Environment.getExternalStorageDirectory().getPath();
        //Get files from storage
        ArrayList<String> FilesInFolder = GetFiles("/handsup");

        lv = (ListView)findViewById(R.id.fileListView);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, FilesInFolder));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               // Clicking on items
            }
        });
    }
    public ArrayList<String> GetFiles(String DirectoryPath) {
        //Create a list to store file names in
        ArrayList<String> MyFiles = new ArrayList<String>();
        //Create file of directory path (actually folder)
        File dataRoot = Environment.getDataDirectory();
        //File f = new File(dataRoot);

        if (dataRoot.exists()){
            Log.d("FileLocation", "YOU MAY LIVE");
        }
        else{
            Log.d("FileLocation", dataRoot.toString() + DirectoryPath);
        }
        //Add all files to an arryList
        dataRoot.mkdirs();
        File[] files = dataRoot.listFiles();
        if (files.length == 0)
            //No Files found at this location
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }
}
