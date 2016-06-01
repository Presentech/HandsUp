package com.presentech.handsup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Jay on 10-03-2016.
 */
public class SettingsAccountActivity extends AppCompatActivity {
    @Override
    /*calls onCreate to load a saved instance from the bundle to regenerate activity*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_toolbar);

        /*execute and start transaction. specify fragment to replace and in which view to insert
        /*to take effect, commit the changes*/
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
            /*load the preferences from XML resource*/
            addPreferencesFromResource(R.xml.settings_account_prefs);
            /*get the instance of the SharedPreference*/
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);
            userName = sharedPreferences.getString(HandsUpApplication.PREF_USERNAME, "");
            /*set the listener*/
            findPreference("account_credentials_key").setOnPreferenceClickListener(this);
            findPreference("logout").setOnPreferenceClickListener(this);
            if (!userName.equalsIgnoreCase("")) {
                findPreference("active_account_key").setTitle("Active Account : "+userName);
            }
        }

        @Override

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            /*add data to listview*/
            View rootView = getView();
            assert rootView != null;
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            list.setDivider(ContextCompat.getDrawable(getActivity(), R.color.textColour));
            list.setDividerHeight(1);
        }

        @Override
        public void onResume() {
            super.onResume();

            /*register the preference values' changes by setting up a listener*/
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();

            /*unregister the listener*/
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        }

        @Override
        /*Invokes callback when a Preference is clicked*/
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equalsIgnoreCase("account_credentials_key")) {
                Intent intent =new Intent(getActivity(),AccountCredentialsActivity.class);
                intent.putExtra("name",userName);
                startActivity(intent);
            } else if (preference.getKey().equalsIgnoreCase("logout")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HandsUpApplication.PREF_NAME,MODE_PRIVATE);
            sharedPreferences.edit().putString(HandsUpApplication.PREF_USERNAME,"").commit();
            sharedPreferences.edit().putString(HandsUpApplication.PREF_PASSWORD,"").commit();
            Intent modeSelectScreenIntent = new Intent(getActivity(),ModeSelectActivity.class);
            modeSelectScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(modeSelectScreenIntent);
            getActivity().finish();
        }
            /*returns boolean when click is handled*/
            return true;
        }
    }
}

