package com.presentech.handsup;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PresentationFileListActivity extends AppCompatActivity {
    public ArrayList<String> FilesInFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_file_list);

        //Create ListView
        ListView lv;
        String sdCardLocation = Environment.getExternalStorageDirectory().getPath();
        //Get files from storage
        FilesInFolder = GetFiles();
        //Create reference to listview
        lv = (ListView)findViewById(R.id.fileListView);
        //Set adapter for listview (standard)
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, FilesInFolder));
        //Wait for item click return position
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v, int position, long id){
              //On item click create intent for returning to hosting wizard
              Intent Intent = new Intent(PresentationFileListActivity.this, HostingWizardActivity.class);
              //Get name of file selected
              String fileName = (String) FilesInFolder.get(position);
              String filePath = Environment.getExternalStorageDirectory().getPath() + "/Presentations/" + fileName;
              Log.d("FILEPATH", filePath);
              //put filePath as extra message with intent
              Intent.putExtra(HostingWizardActivity.FILE_PATH_NAME, filePath);

              startActivity(Intent);

            }
        });
    }


    public ArrayList<String> GetFiles() {
        //Create a list to store file names in
        ArrayList<String> MyFiles = new ArrayList<String>();
        File[] files = null;
        //Create file of directory path (actually folder)
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sdcard = Environment.getExternalStorageDirectory();
            File f = new File(sdcard, "/Presentations");
            files = f.listFiles();
        }
        //Add all files to an arryList
        //f.mkdirs();
        if (files == null) {
            //No Files found at this location
            return null;
        }
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }
}
