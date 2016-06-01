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

public class SettingsPresentationFeedbackActivity extends AppCompatActivity {
    @Override
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
            addPreferencesFromResource(R.xml.settings_presentation_feedback_prefs);

            findPreference("media_transition_setting_key").setOnPreferenceClickListener(this);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            View rootView = getView();
            assert rootView != null;
            ListView list = (ListView) rootView.findViewById(android.R.id.list);
            /*use ContextCompat class and drawable method to retrieve and xml drawable and set divider.
             It is backwards compatible and saves having to worry about system versions*/
            list.setDivider(ContextCompat.getDrawable(getActivity(), R.color.textColour));
            /*set the divider height dynamically*/
            list.setDividerHeight(1);
        }

        /*get method notified by registering and unregister the activity as an active listener*/
        @Override
        public void onResume() {
            super.onResume();
            /*registering the changeListener*/
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            /*unregister the changeListener*/
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        }

        @Override
        /*retrieving the preference in the preference fragment.
         override when clicked */
        public boolean onPreferenceClick(Preference preference) {
            /*reference key for the preference from the xml
              if the key is clicked launch an activity*/
            if (preference.getKey().equalsIgnoreCase("media_transition_setting_key")) {
                Intent intent = new Intent(getActivity(), SlideContentTimingsActivity.class);
                startActivity(intent);
            }
            return true;
        }
    }
}
