package com.presentech.handsup;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

/* Created by Jay on 10-03-2016 */

public class SettingsVisibilityActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private String keySeekBarBrightness, keyAutoRotate, keyZoom;
    /*Variable to store brightness value*/
    private int brightness;
    /*Content resolver used as a handle to the system's settings*/
    private ContentResolver cResolver;
    /*Window object, that will store a reference to the current window*/
    private Window window;
    /*SeekBar for brightness slider*/
    private SeekBar seekBarBrightness;

    public static final String PREF_KEY_ROTATE = "rotate";
    public static final String PREF_KEY_ZOOM = "zoom";

    Switch switchAutoRotate, switchZoom;
    SharedPreferences sharedPreferences;

    @Override
    /*Called when the activity is first created*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_visibility);

        keyAutoRotate = getString(R.string.pref_auto_rotate);
        keySeekBarBrightness = getString(R.string.pref_brightness);
        keyZoom = getString(R.string.pref_zoom);

        /*get the widgets reference from xml resource*/
        seekBarBrightness = (SeekBar) findViewById(R.id.seekbarBrightness);
        switchAutoRotate = (Switch) findViewById(R.id.switchAutoRotate);
        switchZoom = (Switch) findViewById(R.id.switchZoooming);

        /*reference to SharedPreferences object*/
        sharedPreferences = getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(PREF_KEY_ROTATE, false)) {
            switchAutoRotate.setChecked(true);
        }else{
            switchAutoRotate.setChecked(false);
        }

        if (sharedPreferences.getBoolean(PREF_KEY_ZOOM, false)) {
            switchZoom.setChecked(true);
        }else{
            switchZoom.setChecked(false);
        }

        seekBarBrightness.setOnSeekBarChangeListener(this);
        switchZoom.setOnCheckedChangeListener(this);
        switchAutoRotate.setOnCheckedChangeListener(this);

        checkWriteSettingsPermission();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switchAutoRotate:
                sharedPreferences.edit().putBoolean(PREF_KEY_ROTATE,b).commit();
                setAutoOrientationEnabled(this, b);
                break;
            case R.id.switchZoooming:
                sharedPreferences.edit().putBoolean(PREF_KEY_ZOOM,b).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int brightness = i;
        seekBarBrightness.setProgress(brightness);
        /*Set the system brightness using the brightness variable value*/
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        /*Get the current window attributes*/
        WindowManager.LayoutParams layoutpars = window.getAttributes();
        /*Set the brightness of this window*/
        layoutpars.screenBrightness = brightness / (float) 255;
        /*Apply attribute changes to this window*/
        window.setAttributes(layoutpars);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

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
            if (Settings.System.canWrite(this)) {
                initializeBrightnessSettingCode();
            } else {
                startActivity(new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS));
            }
        } else {
            initializeBrightnessSettingCode();
        }
    }

    private void initializeBrightnessSettingCode() {
        cResolver = getContentResolver();
        window = getWindow();
        try {
            /*to handle auto*/
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            /*Get the current system brightness*/
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
            seekBarBrightness.setProgress(brightness);

        } catch (Settings.SettingNotFoundException e) {
            /*Throw an error case it couldn't be retrieved*/
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
    }

    private void setAutoOrientationEnabled(Context context, boolean enabled) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }
}
