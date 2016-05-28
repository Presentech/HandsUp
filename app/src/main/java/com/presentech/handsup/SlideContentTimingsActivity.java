package com.presentech.handsup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;

public class SlideContentTimingsActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private CheckBox cbAdvanceAutomatically, cbLoopContinuous;
    private NumberPicker npAdvanceAutomatically;
    SharedPreferences sharedPreferences;
    int duration = 0;
    public static final String PREF_KEY_DURATION = "duration";
    public static final String PREF_KEY_NAME = "ubpref";
    public static final String PREF_KEY_ADVANCE_CHECKED = "advanced";
    public static final String PREF_KEY_LOOP_CHECKED = "loop";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_content_timings);

        //Reference to SharedPreferences object
        sharedPreferences = getSharedPreferences(PREF_KEY_NAME, MODE_PRIVATE);
        final int duration = sharedPreferences.getInt(PREF_KEY_DURATION, 0);

        //Get the widgets reference from XML layout
        npAdvanceAutomatically = (NumberPicker) findViewById(R.id.npTransitionTimePicker);
        cbAdvanceAutomatically = (CheckBox) findViewById(R.id.cbAdvanceAutomatically);
        cbLoopContinuous = (CheckBox) findViewById(R.id.cbLoopContinuously);

        if (sharedPreferences.getBoolean(PREF_KEY_ADVANCE_CHECKED, false)) {
            cbAdvanceAutomatically.setChecked(true);
            npAdvanceAutomatically.setEnabled(true);
        }else{
            cbAdvanceAutomatically.setChecked(false);
            npAdvanceAutomatically.setEnabled(false);
        }

        if (sharedPreferences.getBoolean(PREF_KEY_LOOP_CHECKED, false)) {
            cbLoopContinuous.setChecked(true);
        }else{
            cbLoopContinuous.setChecked(false);
        }

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        npAdvanceAutomatically.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        npAdvanceAutomatically.setMaxValue(20);

        if (sharedPreferences.getInt(PREF_KEY_DURATION, 0) > 0) {
            //Specify the NumberPicker data source
            npAdvanceAutomatically.setValue(duration);
        }

        //Register a callback to be invoked when the checked state of this button changes.
        cbAdvanceAutomatically.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            //Called everytime user sets/unsets the checkbox.
            //Using Boolean parameter with constructor, to determine if toggle is set.
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean(PREF_KEY_ADVANCE_CHECKED,b).commit();
                if(sharedPreferences.getBoolean(PREF_KEY_LOOP_CHECKED,false) && b==false) {
                    sharedPreferences.edit().putBoolean(PREF_KEY_LOOP_CHECKED, b).commit();
                    cbLoopContinuous.setChecked(false);
                }

                npAdvanceAutomatically.setEnabled(b);
            }
        });

        cbLoopContinuous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean(PREF_KEY_LOOP_CHECKED,b).commit();
            }
        });

        npAdvanceAutomatically.setOnValueChangedListener(this);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        //Allows/disallows consecutive scrolling through the NumberPicker
        npAdvanceAutomatically.setWrapSelectorWheel(true);
    }


    public void saveSettings(View v) {
        //Call edit on sharedPreferences and load data to object
        sharedPreferences.edit().putInt(PREF_KEY_DURATION, duration).commit();
        finish();
    }

    @Override
    //Set a value change listener for NumberPicker
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        //Display the newly selected number from picker
        duration = newVal;
    }
}
