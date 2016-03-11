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
        final ArrayList<String> FilesInFolder = GetFiles("/HandsUp");
        //Create reference to listview
        lv = (ListView)findViewById(R.id.fileListView);
        //Set adapter for listview (standard)
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, FilesInFolder));
        //Wait for item click return position
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
              //On item click create intent for returning to hosting wizard
              Intent Intent = new Intent(PresentationFileListActivity.this, HostingWizardActivity.class);
              //Get name of file selected
              String fileName = (String) FilesInFolder.get(position);
              String filePath = Environment.getExternalStorageDirectory().getPath() + "/HandsUp/" + fileName;
              //put filePath as extra message with intent
              Intent.putExtra(HostingWizardActivity.FILE_PATH_NAME, filePath);

              startActivity(Intent);

            }
        });
    }

    public void selectItem (int position, Intent intent, ArrayList<String> FilesInFolder){
        //Attach different things to the intent depending on which file was selected
        //Intent gives new activity to start, position is item clicked, i is number of items


    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        //Create a list to store file names in
        ArrayList<String> MyFiles = new ArrayList<String>();
        //Create file of directory path (actually folder)
        File dataRoot = Environment.getExternalStorageDirectory();
        File f = new File(dataRoot, DirectoryPath);
        //Add all files to an arryList
        //f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0) {
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
