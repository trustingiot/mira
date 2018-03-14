package com.wordpress.trusted827.mira.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wordpress.trusted827.mira.CustomApplication;
import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.helper.SharedPreferencesHelperImpl;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etPhone;
    private SharedPreferencesHelperImpl mSharedPreferencesHelper;

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mSharedPreferencesHelper = new SharedPreferencesHelperImpl(this);
        if (mSharedPreferencesHelper.getOwnUserRawPhoneNumber().isEmpty()) {
            this.etPhone = ((EditText) findViewById(R.id.etPhone));
            this.btnLogin = ((Button) findViewById(R.id.btnLogin));
            this.btnLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    String phone = LoginActivity.this.etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        etPhone.setError("Invalid phone number");
                    } else {
                        mSharedPreferencesHelper.setOwnUserRawPhoneNumber(phone);
                        CustomApplication.connectMqtt(getApplicationContext());
                        goToMain();
                    }
                }
            });
        } else {
            goToMain();
        }
    }
}
