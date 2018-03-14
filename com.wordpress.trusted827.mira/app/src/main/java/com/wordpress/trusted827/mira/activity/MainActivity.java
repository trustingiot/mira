package com.wordpress.trusted827.mira.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.helper.SharedPreferencesHelperImpl;

public class MainActivity extends AppCompatActivity
{
    private Button mBtnRelatives;
    private Button mBtnSharedLocations;
    private TextView mTvPhone;

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
        this.mTvPhone.setText("Actual phone: " + new SharedPreferencesHelperImpl(this).getOwnUserRawPhoneNumber());
    }
}
