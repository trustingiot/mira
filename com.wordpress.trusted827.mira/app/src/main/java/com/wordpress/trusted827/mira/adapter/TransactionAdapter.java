package com.wordpress.trusted827.mira.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.entity.Transaction;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.CustomViewHolder>
{
    private TransactionCallback mTransactionCallback;
    private List<Transaction> mTransactions;

    public TransactionAdapter(List<Transaction> paramList, TransactionCallback paramTransactionCallback)
    {
        this.mTransactions = paramList;
        this.mTransactionCallback = paramTransactionCallback;
    }

    public int getItemCount()
    {
        return this.mTransactions.size();
    }

    public void onBindViewHolder(CustomViewHolder paramCustomViewHolder, final int paramInt)
    {
        String text = mTransactions.get(paramInt).getInstallation();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        paramCustomViewHolder.installation.setText(text);

        paramCustomViewHolder.location.setText("{" + (mTransactions.get(paramInt)).getX() + ", " + (mTransactions.get(paramInt)).getY() + "}");
        Object localObject = Calendar.getInstance(Locale.ENGLISH);
        ((Calendar)localObject).setTimeInMillis((mTransactions.get(paramInt)).getTimestamp());
        localObject = DateFormat.format("dd-MM-yyyy HH:mm", (Calendar)localObject).toString();
        paramCustomViewHolder.time.setText((CharSequence)localObject);
        paramCustomViewHolder.share.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                TransactionAdapter.this.mTransactionCallback.onShareTransationClick(mTransactions.get(paramInt));
            }
        });
        paramCustomViewHolder.verified.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                TransactionAdapter.this.mTransactionCallback.onVerifiedTransationClick(mTransactions.get(paramInt));
            }
        });
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
        return new CustomViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_transation, null));
    }

    class CustomViewHolder
            extends RecyclerView.ViewHolder
    {
        private TextView installation;
        private TextView location;
        private View root;
        private AppCompatImageView share;
        private TextView verified;
        private TextView time;

        public CustomViewHolder(View view)
        {
            super(view);
            this.root = view;
            this.installation = ((TextView)view.findViewById(R.id.tvTransationInstallation));
            this.location = ((TextView)view.findViewById(R.id.tvTransationLocation));
            this.time = ((TextView)view.findViewById(R.id.tvTransationTime));
            this.share = ((AppCompatImageView)view.findViewById(R.id.ivShare));
            this.verified = (TextView) view.findViewById(R.id.ivAccept);
        }
    }

    public interface TransactionCallback
    {
        void onShareTransationClick(Transaction paramTransaction);

        void onVerifiedTransationClick(Transaction paramTransaction);
    }
}
