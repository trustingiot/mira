package com.wordpress.trusted827.mira.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wordpress.trusted827.mira.CustomApplication;
import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.adapter.TransactionAdapter;
import com.wordpress.trusted827.mira.components.CustomDialog;
import com.wordpress.trusted827.mira.components.MessageReceiver;
import com.wordpress.trusted827.mira.db.TransactionDB;
import com.wordpress.trusted827.mira.db.mapper.TransactionDBMapper;
import com.wordpress.trusted827.mira.entity.Transaction;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import com.wordpress.trusted827.mira.usecase.Callback;
import com.wordpress.trusted827.mira.usecase.GetTransactionsUseCase;
import java.util.ArrayList;
import java.util.List;

import static com.wordpress.trusted827.mira.Instance.*;

public class SharedLocationsActivity
        extends AppCompatActivity
{
    private MessageReceiver mMessageReceiver;
    private RecyclerView mRvTransactions;
    private TransactionAdapter mTransactionAdapter;
    private ArrayList<Transaction> mTransactions = new ArrayList();

    protected void onCreate(@Nullable Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_shared_locations);
        this.mRvTransactions = ((RecyclerView)findViewById(R.id.rvHistory));
        this.mMessageReceiver = new MessageReceiver(new MessageReceiver.NewMessageCallback()
        {
            public void onNewMessage(Transaction paramAnonymousTransaction)
            {
                mTransactions.add(paramAnonymousTransaction);
                mTransactionAdapter.notifyDataSetChanged();
            }
        });
        new GetTransactionsUseCase(new TransactionDB(MIRASQLiteOpenHelper.getInstance(this), new TransactionDBMapper())).getSharedTransactions(new Callback<List<Transaction>>() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(List<Transaction> transactions) {
                mTransactions.clear();
                mTransactions.addAll(transactions);
                mTransactionAdapter = new TransactionAdapter(mTransactions, true, new TransactionAdapter.TransactionCallback()
                {
                    public void onShareTransationClick(final Transaction transaction)
                    {
                        new CustomDialog.Builder(SharedLocationsActivity.this).setCallBack(new CustomDialog.CallBack()
                        {
                            public void onNegativeClick() {}

                            public void onPositiveClick(String paramAnonymous3String)
                            {
                                CustomApplication.share(transaction, paramAnonymous3String, SharedLocationsActivity.this.getApplicationContext());
                            }
                        }).show();
                    }

                    public void onVerifiedTransationClick(Transaction transaction)
                    {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(TRANSACTIONS_EXPLORER + transaction.getTransaction()));
                        startActivity(intent);
                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SharedLocationsActivity.this);
                SharedLocationsActivity.this.mRvTransactions.setLayoutManager(linearLayoutManager);
                SharedLocationsActivity.this.mRvTransactions.setAdapter(SharedLocationsActivity.this.mTransactionAdapter);
            }
        });
    }

    protected void onPause()
    {
        this.mMessageReceiver.unregister(this);
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.wordpress.trusted827.mira.NEW_MESSAGE");
        this.mMessageReceiver.register(this, localIntentFilter);
    }
}
