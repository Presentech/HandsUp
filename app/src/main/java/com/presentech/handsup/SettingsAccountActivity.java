//package com.presentech.handsup;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.Preference;
//import android.preference.PreferenceFragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ListView;
//
///**
// * Created by Jay on 10-03-2016.
// */
//public class SettingsAccountActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings_toolbar);
//
//      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);*/
//
//        //toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//
//        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home)
//            finish();
//
//        return true;
//    }
//
////    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
////        String userName;
////        @Override
////        public void onCreate(final Bundle savedInstanceState) {
////            super.onCreate(savedInstanceState);
////            addPreferencesFromResource(R.xml.settings_account_prefs);
////            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);
////            userName = sharedPreferences.getString(HandsUpApplication.PREF_USERNAME, "");
////            if (!userName.equalsIgnoreCase("")) {
////                findPreference("active_account_key").setTitle(userName);
////            }
////        }
//
//        @Override
//        public void onActivityCreated(Bundle savedInstanceState) {
//            super.onActivityCreated(savedInstanceState);
//
//            View rootView = getView();
//            ListView list = (ListView) rootView.findViewById(android.R.id.list);
//            list.setDivider(ContextCompat.getDrawable(getActivity(), R.color.textColour));
//            list.setDividerHeight(1);
//
//
//        }
//
//
//        @Override
//        public void onResume() {
//            super.onResume();
//
//            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
//        }
//
//        @Override
//        public void onPause() {
//            super.onPause();
//
//            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
//        }
//
//        @Override
//        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//
//        }
//
//////        @Override
//////        public boolean onPreferenceClick(Preference preference) {
//////
//////            if (preference.getKey().equalsIgnoreCase("account_credentials_key")) {
//////                Intent intent =new Intent(getActivity(),AccountCredentialsActivity.class);
//////                intent.putExtra("name",userName);
//////                startActivity(intent);
//////            }
////
////
////            return true;
//        }
//    }
//
//}


package com.presentech.handsup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Jay on 10-03-2016.
 */
public class SettingsAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_toolbar);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(R.drawable.arrow_left);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
        String userName;
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_account_prefs);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);
            userName = sharedPreferences.getString(HandsUpApplication.PREF_USERNAME, "");
            findPreference("account_credentials_key").setOnPreferenceClickListener(this);
            if (!userName.equalsIgnoreCase("")) {
                findPreference("active_account_key").setTitle("Active Account : "+userName);
            }
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            View rootView = getView();
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            list.setDivider(ContextCompat.getDrawable(getActivity(), R.color.textColour));
            list.setDividerHeight(1);
        }


        @Override
        public void onResume() {
            super.onResume();

            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();

            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equalsIgnoreCase("account_credentials_key")) {
                Intent intent =new Intent(getActivity(),AccountCredentialsActivity.class);
                intent.putExtra("name",userName);
                startActivity(intent);
            }
            return true;
        }
    }

}

