package com.wordpress.trusted827.mira.components;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wordpress.trusted827.mira.R;

public class LoadingDialog
        extends DialogFragment
{
    private static final String DIALOG_NAME = "loading_dialog";
    private static LoadingDialog mDialog;
    private static boolean mIsDialogShowing = false;

    private static LoadingDialog newInstance()
    {
        return new LoadingDialog();
    }

    private static void start(final FragmentManager fragmentManager)
    {
        if (mDialog == null) {
            mDialog = newInstance();
        }
        if ((!mIsDialogShowing) && (!mDialog.isAdded()))
        {
            mIsDialogShowing = true;
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                public void run()
                {
                    fragmentManager.beginTransaction().add(LoadingDialog.mDialog, "loading_dialog").commitAllowingStateLoss();
                }
            });
        }
    }

    public static void startLoading(FragmentManager paramFragmentManager)
    {
        start(paramFragmentManager);
    }

    public static void stopLoading(final FragmentManager fragmentManager)
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (fragmentManager != null) {
                    try {
                        fragmentManager.executePendingTransactions();
                    } catch (NullPointerException e) {
                    }
                }

                if (mDialog != null && mIsDialogShowing && mDialog.isAdded()) {
                    mIsDialogShowing = false;
                    mDialog.dismissAllowingStateLoss();
                }
            }
        });
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
    }

    public Dialog onCreateDialog(Bundle paramBundle)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_loading, null));
        return builder.create();
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
    {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setCancelable(false);
        return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    }
}
