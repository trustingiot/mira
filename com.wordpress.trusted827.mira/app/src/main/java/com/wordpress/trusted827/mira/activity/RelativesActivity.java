package com.wordpress.trusted827.mira.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.adapter.RelativeAdapter;
import com.wordpress.trusted827.mira.db.RelativeDB;
import com.wordpress.trusted827.mira.db.mapper.RelativeDBMapper;
import com.wordpress.trusted827.mira.entity.Relative;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import com.wordpress.trusted827.mira.usecase.Callback;
import com.wordpress.trusted827.mira.usecase.GetRelativesUseCase;

import java.util.ArrayList;
import java.util.List;

public class RelativesActivity extends AppCompatActivity {
    private FloatingActionButton mBtnAdd;
    private GetRelativesUseCase mGetRelativesUseCase;
    private RelativeAdapter mRelativeAdapter;
    private List<Relative> mRelatives;
    private RecyclerView mRvRelatives;

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_relatives);
        this.mRvRelatives = ((RecyclerView) findViewById(R.id.rvRelatives));
        this.mBtnAdd = ((FloatingActionButton) findViewById(R.id.btnAddRelative));
        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(RelativesActivity.this, AddRelativeActivity.class);
                startActivity(intent);
            }
        });
        this.mGetRelativesUseCase = new GetRelativesUseCase(new RelativeDB(MIRASQLiteOpenHelper.getInstance(this), new RelativeDBMapper()));
    }

    protected void onResume() {
        super.onResume();
        mGetRelativesUseCase.getRelatives(new Callback<List<Relative>>() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(final List<Relative> relatives) {
                new Handler(getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (mRelatives == null) {
                            mRelatives = new ArrayList<>();
                        }
                        mRelatives.clear();
                        mRelatives.addAll(relatives);
                        if (mRelativeAdapter == null) {
                            mRelativeAdapter = new RelativeAdapter(mRelatives, new RelativeAdapter.RelativeCallback() {
                                public void onRelativeClick(Relative relative) {
                                    Intent localIntent = new Intent(RelativesActivity.this, RelativeDetailActivity.class);
                                    localIntent.putExtra("EXTRA_RELATIVE", relative);
                                    startActivity(localIntent);
                                }
                            });
                            LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(RelativesActivity.this);
                            mRvRelatives.setLayoutManager(localLinearLayoutManager);
                            mRvRelatives.setAdapter(mRelativeAdapter);
                            return;
                        }
                        mRelativeAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}