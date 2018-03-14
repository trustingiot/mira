package com.wordpress.trusted827.mira.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.trusted827.mira.R;
import com.wordpress.trusted827.mira.entity.Relative;
import java.util.List;

public class RelativeAdapter extends RecyclerView.Adapter<RelativeAdapter.CustomViewHolder>
{
    private RelativeCallback mRelativeCallback;
    private List<Relative> mRelativeList;

    public RelativeAdapter(List<Relative> paramList, RelativeCallback paramRelativeCallback)
    {
        this.mRelativeList = paramList;
        this.mRelativeCallback = paramRelativeCallback;
    }

    public int getItemCount()
    {
        return this.mRelativeList.size();
    }

    public void onBindViewHolder(CustomViewHolder paramCustomViewHolder, final int paramInt)
    {
        paramCustomViewHolder.name.setText(((Relative)this.mRelativeList.get(paramInt)).getName());
        paramCustomViewHolder.mac.setText(((Relative)this.mRelativeList.get(paramInt)).getMac());
        paramCustomViewHolder.root.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                RelativeAdapter.this.mRelativeCallback.onRelativeClick((Relative)RelativeAdapter.this.mRelativeList.get(paramInt));
            }
        });
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
        return new CustomViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_relative, null));
    }

    class CustomViewHolder
            extends RecyclerView.ViewHolder
    {
        private TextView mac;
        private TextView name;
        private View root;

        public CustomViewHolder(View view)
        {
            super(view);
            this.root = view;
            this.name = ((TextView)view.findViewById(R.id.tvRelativeName));
            this.mac = ((TextView)view.findViewById(R.id.tvRelativeMac));
        }
    }

    public interface RelativeCallback
    {
        void onRelativeClick(Relative paramRelative);
    }
}
