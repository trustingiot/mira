package com.wordpress.trusted827.mira.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.wordpress.trusted827.mira.entity.Transaction;

public class MessageReceiver extends BroadcastReceiver {
    public static final String ACTION_NEW_MESSAGE = "com.wordpress.trusted827.mira.NEW_MESSAGE";
    public static boolean isRegistered;
    private NewMessageCallback mNewMessageCallback;

    public MessageReceiver(NewMessageCallback paramNewMessageCallback) {
        this.mNewMessageCallback = paramNewMessageCallback;
    }

    public void onReceive(Context paramContext, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_NEW_MESSAGE:
                this.mNewMessageCallback.onNewMessage((Transaction) intent.getSerializableExtra("transaction"));
                break;
        }
    }

    public Intent register(Context paramContext, IntentFilter paramIntentFilter) {
        isRegistered = true;
        return paramContext.registerReceiver(this, paramIntentFilter);
    }

    public boolean unregister(Context paramContext) {
        boolean bool = false;
        if (isRegistered) {
            paramContext.unregisterReceiver(this);
            isRegistered = false;
            bool = true;
        }
        return bool;
    }

    public interface NewMessageCallback {
        void onNewMessage(Transaction paramTransaction);
    }
}
