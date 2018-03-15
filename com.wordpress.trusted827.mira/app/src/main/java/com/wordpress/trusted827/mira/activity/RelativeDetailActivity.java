package com.wordpress.trusted827.mira.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wordpress.trusted827.mira.CustomApplication;
import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.adapter.TransactionAdapter;
import com.wordpress.trusted827.mira.components.CustomDialog;
import com.wordpress.trusted827.mira.components.LoadingDialog;
import com.wordpress.trusted827.mira.db.TransactionDB;
import com.wordpress.trusted827.mira.db.mapper.TransactionDBMapper;
import com.wordpress.trusted827.mira.entity.Relative;
import com.wordpress.trusted827.mira.entity.Transaction;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import com.wordpress.trusted827.mira.usecase.Callback;
import com.wordpress.trusted827.mira.usecase.GetTransactionsUseCase;

import java.util.ArrayList;

import static com.wordpress.trusted827.mira.Instance.*;

public class RelativeDetailActivity extends AppCompatActivity {
    private RecyclerView mRvTransactions;
    private TransactionAdapter mTransactionAdapter;

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_relative_detail);
        this.mRvTransactions = ((RecyclerView) findViewById(R.id.rvHistory));
        Relative relative = (Relative) getIntent().getExtras().getSerializable("EXTRA_RELATIVE");
        getSupportActionBar().setTitle(relative.getName().substring(0,1).toUpperCase() + relative.getName().substring(1) + "'s locations");
        LoadingDialog.startLoading(getSupportFragmentManager());
        new GetTransactionsUseCase(new TransactionDB(MIRASQLiteOpenHelper.getInstance(this), new TransactionDBMapper())).getDeviceTransactions(relative.getMac(), relative.getPassword(), new Callback<ArrayList<Transaction>>() {
            @Override
            public void onError() {
                Toast.makeText(RelativeDetailActivity.this, "Error loading transactions", Toast.LENGTH_LONG).show();
                LoadingDialog.stopLoading(getSupportFragmentManager());
            }

            @Override
            public void onSuccess(final ArrayList<Transaction> transactions) {
                new Handler(getMainLooper()).post(new Runnable() {
                    public void run() {
                        mTransactionAdapter = new TransactionAdapter(transactions, new TransactionAdapter.TransactionCallback() {
                            public void onShareTransationClick(final Transaction transaction) {
                                new CustomDialog.Builder(RelativeDetailActivity.this).setCallBack(new CustomDialog.CallBack() {
                                    public void onNegativeClick() {
                                    }

                                    public void onPositiveClick(String phone) {
                                        CustomApplication.share(transaction, phone, getApplicationContext());
                                    }
                                }).show();
                            }

                            public void onVerifiedTransationClick(Transaction transaction) {
                                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(TRANSACTIONS_EXPLORER + transaction.getTransaction()));
                                startActivity(intent);
                            }
                        });
                        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(RelativeDetailActivity.this);
                        mRvTransactions.setLayoutManager(localLinearLayoutManager);
                        mRvTransactions.setAdapter(mTransactionAdapter);
                        LoadingDialog.stopLoading(getSupportFragmentManager());
                        if (transactions.size() == 0) {
                            Toast.makeText(RelativeDetailActivity.this, "This user does not have any transaction", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
