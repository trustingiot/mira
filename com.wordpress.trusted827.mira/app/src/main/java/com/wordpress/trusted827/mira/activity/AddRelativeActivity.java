package com.wordpress.trusted827.mira.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.db.RelativeDB;
import com.wordpress.trusted827.mira.db.mapper.RelativeDBMapper;
import com.wordpress.trusted827.mira.entity.Relative;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import com.wordpress.trusted827.mira.usecase.AddRelativeUseCase;
import com.wordpress.trusted827.mira.usecase.Callback;

import java.util.HashSet;
import java.util.Set;

public class AddRelativeActivity
        extends AppCompatActivity {
    private Button btnAddRelative;
    private EditText etMac;
    private EditText etName;
    private EditText etPassword;
    private RelativeDB relativeDB;

    private void addRelative(String paramString1, String paramString2, String paramString3) {
        new AddRelativeUseCase(relativeDB).addRelative(new Relative(paramString1, paramString2, paramString3), new Callback<Void>() {
            @Override
            public void onError() {
                Toast.makeText(AddRelativeActivity.this, "Error adding relative", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Void paramT) {
                new Handler(getMainLooper()).post(new Runnable() {
                    public void run() {
                        Toast.makeText(AddRelativeActivity.this, "Relative added successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_add_relative);
        relativeDB = new RelativeDB(MIRASQLiteOpenHelper.getInstance(this), new RelativeDBMapper());
        this.etName = ((EditText) findViewById(R.id.etName));
        this.etMac = ((EditText) findViewById(R.id.etMac));
        this.etPassword = ((EditText) findViewById(R.id.etPassword));
        this.btnAddRelative = ((Button) findViewById(R.id.btnAddRelative));
        this.btnAddRelative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = AddRelativeActivity.this.etName.getText().toString();
                String str1 = AddRelativeActivity.this.etMac.getText().toString();
                String str2 = AddRelativeActivity.this.etPassword.getText().toString();
                Set<String> devices = new HashSet<>();
                for (Relative relative : relativeDB.getAll()) {
                    devices.add(relative.getMac());
                }
                if (TextUtils.isEmpty(name)) {
                    AddRelativeActivity.this.etName.setError("Invalid name");
                    return;
                }
                if (TextUtils.isEmpty(str1)) {
                    AddRelativeActivity.this.etMac.setError("Invalid mac");
                    return;
                }
                if (devices.contains(str1)) {
                    AddRelativeActivity.this.etMac.setError("Assigned");
                    return;
                }
                if (TextUtils.isEmpty(str2)) {
                    AddRelativeActivity.this.etPassword.setError("Invalid password");
                    return;
                }
                AddRelativeActivity.this.addRelative(name, str1, str2);
            }
        });
    }
}
