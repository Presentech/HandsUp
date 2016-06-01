package com.presentech.handsup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;

/* Created by Jay on 15-05-2016 */

public class SlideContentTimingsActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private CheckBox cbAdvanceAutomatically, cbLoopContinuous;
    private NumberPicker npAdvanceAutomatically;
    SharedPreferences sharedPreferences;
    int duration = 0;
    public static final String PREF_KEY_DURATION = "duration";
    public static final String PREF_KEY_ADVANCE_CHECKED = "advanced";
    public static final String PREF_KEY_LOOP_CHECKED = "loop";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_content_timings);

        /*get SharedPreferences object*/
        sharedPreferences = getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);
        /*Get saved duration of slide timing from sharedpreferences*/
        final int duration = sharedPreferences.getInt(PREF_KEY_DURATION, 0);

        /*get the widgets reference from xml resource*/
        npAdvanceAutomatically = (NumberPicker) findViewById(R.id.npTransitionTimePicker);
        cbAdvanceAutomatically = (CheckBox) findViewById(R.id.cbAdvanceAutomatically);
        cbLoopContinuous = (CheckBox) findViewById(R.id.cbLoopContinuously);

        /*initialising components as per their saved state in shared preferences*/
        if (sharedPreferences.getBoolean(PREF_KEY_ADVANCE_CHECKED, false)) {
            //*if advance was checked, check advance and enable both loop and timing component*/
            cbAdvanceAutomatically.setChecked(true);
            cbLoopContinuous.setEnabled(true);
            npAdvanceAutomatically.setEnabled(true);
        }else{
            /*if advance was checked, don't check advance and disable both loop and timing component*/
            cbLoopContinuous.setEnabled(false);
            cbAdvanceAutomatically.setChecked(false);
            npAdvanceAutomatically.setEnabled(false);
        }

        //if loop was checked then set it checked true*/
        if (sharedPreferences.getBoolean(PREF_KEY_LOOP_CHECKED, false)) {
            cbLoopContinuous.setChecked(true);
        }else{
            cbLoopContinuous.setChecked(false);
        }

        /*populate NumberPicker values from minimum and maximum value range.
          set the minimum value of NumberPicker*/
        npAdvanceAutomatically.setMinValue(0);
        /*specify the maximum value/number of NumberPicker*/
        npAdvanceAutomatically.setMaxValue(20);

        /*set value of duration if it was selected*/
        if (sharedPreferences.getInt(PREF_KEY_DURATION, 0) > 0) {
            npAdvanceAutomatically.setValue(duration);
        }

        cbLoopContinuous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean(PREF_KEY_LOOP_CHECKED,b).commit();
            }
        });

        cbAdvanceAutomatically.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    /*if advance was unchecked then disable and uncheck loop as well*/
                    cbLoopContinuous.setEnabled(false);
                    cbLoopContinuous.setChecked(false);
                }else{
                    /*only enable loop if advance was checked*/
                    cbLoopContinuous.setEnabled(true);
                }
                npAdvanceAutomatically.setEnabled(b);
            }
        });

        npAdvanceAutomatically.setOnValueChangedListener(this);

        /*gets whether the selector wheel wraps when reaching the min/max value*/
        /*allows/disallows consecutive scrolling through the NumberPicker*/
        npAdvanceAutomatically.setWrapSelectorWheel(true);
    }


    public void saveSettings(View v) {
        if (!cbAdvanceAutomatically.isChecked()) {
            /*if advance is unchecked, set false in sharedpref for both advance and looping*/
            sharedPreferences.edit().putBoolean(PREF_KEY_ADVANCE_CHECKED, false).commit();
            sharedPreferences.edit().putBoolean(PREF_KEY_LOOP_CHECKED, false).commit();
        } else {
            /*set advance true in shared pref*/
            sharedPreferences.edit().putBoolean(PREF_KEY_ADVANCE_CHECKED, true).commit();
            if (cbLoopContinuous.isChecked()) {
                /*if loop was checked set true in shared prefs8*/
                sharedPreferences.edit().putBoolean(PREF_KEY_LOOP_CHECKED, true).commit();
            } else {
                /*if loop was unchecked set false in shared prefs*/
                sharedPreferences.edit().putBoolean(PREF_KEY_LOOP_CHECKED, false).commit();
            }
        }
        sharedPreferences.edit().putInt(PREF_KEY_DURATION, duration).commit();
        finish();
    }

    /*set a value change listener for NumberPicker*/
    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        /*display the newly selected number from picker*/
        duration = newVal;
    }
}
