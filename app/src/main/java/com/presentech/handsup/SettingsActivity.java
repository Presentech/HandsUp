package com.presentech.handsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolBar;
    private ListView lvSetting;
    private SettingAdapter mAdapter;

    private List<String> settingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);

        setupToolBar();
        bindComponents();
        init();
        addListeners();
    }

    private void init() {
        settingList = Arrays.asList(getResources().getStringArray(R.array.setting_entries));
        mAdapter = new SettingAdapter(settingList);
        lvSetting.setAdapter(mAdapter);
    }

    private void addListeners() {
        lvSetting.setOnItemClickListener(this);
    }

    private void bindComponents() {
        lvSetting = (ListView) findViewById(R.id.listView);
    }

    private void setupToolBar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(null);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0 :
                Intent accessibility_intent = new Intent(this, SettingsAccessibilityActivity.class);
                startActivity(accessibility_intent);
                break;

            case 1 :
                Intent account_intent = new Intent(this, SettingsAccountActivity.class);
                startActivity(account_intent);
                break;

            case 2 :
                Intent presentation_intent = new Intent(this, SettingsPresentationFeedbackActivity.class);
                startActivity(presentation_intent);
                break;

            case 3:
                Intent file_intent = new Intent(this, SettingsFileStorageActivity.class);
                startActivity(file_intent);
                break;

            case 4 :
                Intent intent = new Intent(this, SettingsDefaultSettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    class SettingAdapter extends BaseAdapter {

        private List<String> settingNames;
        SettingAdapter(List<String> settingList) {
            settingNames = settingList;
        }

        @Override
        public int getCount() {
            return settingNames.size();
        }

        @Override
        public Object getItem(int position) {
            return settingNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_settings_custom_listview, parent,false);
            }

            TextView tvTitle = (TextView) convertView.findViewById(R.id.lvTitle);
            tvTitle.setText(settingNames.get(position));

            return convertView;
        }
    }
}
