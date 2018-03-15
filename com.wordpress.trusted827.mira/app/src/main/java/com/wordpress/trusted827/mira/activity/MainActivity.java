package com.wordpress.trusted827.mira.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.trusted827.mira.CustomApplication;
import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.helper.SharedPreferencesHelperImpl;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private Button mBtnRelatives;
    private Button mBtnSharedLocations;
    private TextView mTvPhone;
    private SharedPreferencesHelperImpl mSharedPreferencesHelper;

    private void goToRelatives()
    {
        startActivity(new Intent(this, RelativesActivity.class));
    }

    private void goToSharedLocations()
    {
        startActivity(new Intent(this, SharedLocationsActivity.class));
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        searchPhoneNumber();
        CustomApplication.connectMqtt(getApplicationContext());
        this.mBtnRelatives = ((Button)findViewById(R.id.btnRelatives));
        this.mBtnSharedLocations = ((Button)findViewById(R.id.btnSharedLocations));
        this.mTvPhone = ((TextView)findViewById(R.id.tvPhone));
        this.mBtnRelatives.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                goToRelatives();
            }
        });
        this.mBtnSharedLocations.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                goToSharedLocations();
            }
        });
        this.mTvPhone.setText("Your number: " + new SharedPreferencesHelperImpl(this).getOwnUserRawPhoneNumber());
    }

    protected void searchPhoneNumber() {
        mSharedPreferencesHelper = new SharedPreferencesHelperImpl(this);
        if (mSharedPreferencesHelper.getOwnUserRawPhoneNumber().isEmpty()) {
            mSharedPreferencesHelper.setOwnUserRawPhoneNumber(getPhoneNumber());
        }
    }

    private String getPhoneNumber() {
        String phoneNumber = null;

        try {
            TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            phoneNumber = tMgr.getLine1Number();
        } catch (SecurityException e) {
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            phoneNumber = "";
            Random random = new Random();
            while (phoneNumber.length() < 9) {
                phoneNumber += Integer.toString(random.nextInt(10));
            }

            Toast.makeText(MainActivity.this, "Phone number detection failed. Assigned random phone number: " + phoneNumber, Toast.LENGTH_LONG).show();
        }

        return phoneNumber;
    }
}
