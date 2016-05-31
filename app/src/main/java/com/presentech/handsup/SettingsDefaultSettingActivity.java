package com.presentech.handsup;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Jay on 10-03-2016.
 */
public class SettingsDefaultSettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_toolbar);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

        private ContentResolver cResolver;
        //Window object, that will store a reference to the current window
        private Window window;


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_default_setting_prefs);
            findPreference("reset_setting_key").setOnPreferenceClickListener(this);
            checkWriteSettingsPermission();
        }


        private void initializeBrightnessSettingCode() {
            cResolver = getActivity().getContentResolver();
            window = getActivity().getWindow();

            // To handle auto
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);


        }


        @TargetApi(23)
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeBrightnessSettingCode();
            }
        }

        private void setAutoOrientationEnabled(Context context, boolean enabled) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
        }

        private void checkWriteSettingsPermission() {

            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.System.canWrite(getActivity())) {
                    initializeBrightnessSettingCode();
                } else {
                    startActivity(new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS));
                }
            } else {
                initializeBrightnessSettingCode();
            }
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
            if (preference.getKey().equalsIgnoreCase("reset_setting_key")) {
                //set default values

                //Set the system brightness using the brightness variable value
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS,50);
                //Get the current window attributes
                WindowManager.LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                layoutpars.screenBrightness = 50 / (float) 255;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);

                sharedPreferences.edit().putBoolean(SlideContentTimingsActivity.PREF_KEY_ADVANCE_CHECKED, false).commit();
                sharedPreferences.edit().putBoolean(SlideContentTimingsActivity.PREF_KEY_LOOP_CHECKED, false).commit();
                sharedPreferences.edit().putInt(SlideContentTimingsActivity.PREF_KEY_DURATION, 0).commit();
                setAutoOrientationEnabled(getActivity(), false);
                getActivity().finish();
            }
            return true;
        }
    }


}

