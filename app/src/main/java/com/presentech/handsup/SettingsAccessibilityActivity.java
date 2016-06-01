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

/* Created by Jay on 10-03-2016 */

public class SettingsAccessibilityActivity extends AppCompatActivity {
    @Override
    /*calls onCreate to load a saved instance from the bundle to regenerate activity*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_toolbar);

        /*display the fragment as the main content.
         execute and start transaction. specify fragment to replace and in
         which view to insert. To take effect, commit the changes*/
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            /*load the preferences from XML resource*/
            addPreferencesFromResource(R.xml.settings_accessibility_prefs);
            findPreference("visibility_key").setOnPreferenceClickListener(this);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            View rootView = getView();
            assert rootView != null;
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            list.setDivider(ContextCompat.getDrawable(getActivity(), R.color.textColour));
            list.setDividerHeight(1);
        }

        @Override
        public void onResume() {
            super.onResume();
            /*register the listener*/
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
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equalsIgnoreCase("visibility_key")) {
                Intent intent = new Intent(getActivity(), SettingsVisibilityActivity.class);
                startActivity(intent);
            }
            return true;
        }
    }
}

