package com.presentech.handsup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AccountCredentialsActivity extends AppCompatActivity {

    private final String LOGIN_ID = "Login ID: ";
    private EditText etOldPassword, etNewPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_credentials);
        String name = getIntent().getStringExtra("name")==null?"":getIntent().getStringExtra("name");


        //initialized view and sharedepreference
        sharedPreferences = getSharedPreferences(HandsUpApplication.PREF_NAME, MODE_PRIVATE);

        ((TextView) findViewById(R.id.activity_account_credentials_tv_login_id)).setText(LOGIN_ID + name);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);

    }


    public void submitData(View view) {
        //Viadation to check blank data, and verify of old password matches or not
        if (etOldPassword.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Kindly enter old password", Toast.LENGTH_SHORT).show();
            etOldPassword.requestFocus();

        } else if (etNewPassword.getText().toString().trim().equalsIgnoreCase("")) {
            etNewPassword.requestFocus();
            Toast.makeText(this, "Kindly enter new password", Toast.LENGTH_SHORT).show();
        } else {
            String oldPassword = etOldPassword.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();

            if (oldPassword.equalsIgnoreCase(sharedPreferences.getString(HandsUpApplication.PREF_PASSWORD, "").trim())) {
                sharedPreferences.edit().putString(HandsUpApplication.PREF_PASSWORD, newPassword).commit();
                Toast.makeText(this, "New password saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Wrong old password entered", Toast.LENGTH_SHORT).show();
            }


        }


    }
}

