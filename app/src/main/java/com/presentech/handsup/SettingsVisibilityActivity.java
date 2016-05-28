package com.presentech.handsup;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * Created by Jay on 23-05-2016.
 */
public class SettingsVisibilityActivity extends AppCompatActivity {

    private MyPreferenceFragment myPreferenceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_toolbar);
        myPreferenceFragment = new MyPreferenceFragment();


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, myPreferenceFragment).commit();

    }


    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private SeekBarPreference seekBarBrightness;
        private SwitchPreference spZoom, spAutoRotate;
        private String keySeekBarBrightness, keyAutoRotate, keyZoom;
        //Variable to store brightness value
        private int brightness;
        //Content resolver used as a handle to the system's settings
        private ContentResolver cResolver;
        //Window object, that will store a reference to the current window
        private Window window;
        private int PERMISSION_WRITE_SETTINGS = 10;


        @TargetApi(23)
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeBrightnessSettingCode();
            } else {
                seekBarBrightness.setEnabled(false);
            }
        }

        private void checkWriteSettingsPermission() {


            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.System.canWrite(getActivity())) {
                    initializeBrightnessSettingCode();
           /*     if (Build.VERSION.SDK_INT >= 23) {
                    // Assume thisActivity is the current activity
                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_SETTINGS);
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        initializeBrightnessSettingCode();
                    } else {
                        String[] permissions = {Manifest.permission.WRITE_SETTINGS};
                        ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_WRITE_SETTINGS);
                    }

                } else {
                    initializeBrightnessSettingCode();
                }*/
                } else {
                    startActivity(new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS));
                }
            }else{
                initializeBrightnessSettingCode();
            }

        }

        private void initializeBrightnessSettingCode() {

            cResolver = getActivity().getContentResolver();
            window = getActivity().getWindow();
            try {
                // To handle the auto
                Settings.System.putInt(cResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                //Get the current system brightness
                brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
                seekBarBrightness.setValue(brightness);

            } catch (Settings.SettingNotFoundException e) {
                //Throw an error case it couldn't be retrieved
                Log.e("Error", "Cannot access system brightness");
                e.printStackTrace();
            }
        }


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_visibility_prefs);

            keyAutoRotate = getString(R.string.pref_auto_rotate);
            keySeekBarBrightness = getString(R.string.pref_brightness);
            keyZoom = getString(R.string.pref_zoom);


            seekBarBrightness = (SeekBarPreference) findPreference(keySeekBarBrightness);
            spZoom = (SwitchPreference) findPreference(keyZoom);
            spAutoRotate = (SwitchPreference) findPreference(keyAutoRotate);

            checkWriteSettingsPermission();


        }


        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
            prefs.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
            prefs.unregisterOnSharedPreferenceChangeListener(this);
        }


        private void setAutoOrientationEnabled(Context context, boolean enabled) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
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
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            if (s.equalsIgnoreCase(keySeekBarBrightness)) {
                int brightness = sharedPreferences.getInt(s, 0);
                seekBarBrightness.setValue(brightness);
                //Set the system brightness using the brightness variable value
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                //Get the current window attributes
                WindowManager.LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                layoutpars.screenBrightness = brightness / (float) 255;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);

            } else if (s.equalsIgnoreCase(keyAutoRotate)) {
                boolean autoRotate = sharedPreferences.getBoolean(keyAutoRotate, false);

                setAutoOrientationEnabled(getActivity(), autoRotate);


            } else if (s.equalsIgnoreCase(keyZoom)) {

                boolean zoom = sharedPreferences.getBoolean(keyZoom, false);


            }

        }

    }
}

