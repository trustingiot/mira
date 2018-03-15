package com.wordpress.trusted827.mira;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wordpress.trusted827.mira.components.MessageReceiver;
import com.wordpress.trusted827.mira.db.TransactionDB;
import com.wordpress.trusted827.mira.db.mapper.TransactionDBMapper;
import com.wordpress.trusted827.mira.entity.Transaction;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import com.wordpress.trusted827.mira.helper.SharedPreferencesHelperImpl;
import com.wordpress.trusted827.mira.usecase.SaveTransactionUseCase;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.wordpress.trusted827.mira.Instance.*;

public class CustomApplication extends Application {
    private static MqttAndroidClient mMqttAndroidClient;

    public static void connectMqtt(final Context paramContext) {
        mMqttAndroidClient = new MqttAndroidClient(paramContext, MQTT_SERVER_URL, new SharedPreferencesHelperImpl(paramContext).getOwnUserRawPhoneNumber());
        mMqttAndroidClient.setCallback(new MqttCallback() {
            public void connectionLost(Throwable paramAnonymousThrowable) {
            }

            public void deliveryComplete(IMqttDeliveryToken paramAnonymousIMqttDeliveryToken) {
            }

            public void messageArrived(String paramAnonymousString, MqttMessage paramAnonymousMqttMessage)
                    throws Exception {
                Transaction transaction = (Transaction) new Gson().fromJson(paramAnonymousMqttMessage.toString(), Transaction.class);
                new SaveTransactionUseCase(new TransactionDB(MIRASQLiteOpenHelper.getInstance(paramContext), new TransactionDBMapper())).saveTransaction(transaction);
                if (MessageReceiver.isRegistered) {
                    Intent intent = new Intent("com.wordpress.trusted827.mira.NEW_MESSAGE");
                    intent.putExtra("transaction", transaction);
                    paramContext.sendBroadcast(intent);
                }
            }
        });
        try {
            MqttConnectOptions localMqttConnectOptions = new MqttConnectOptions();
            localMqttConnectOptions.setAutomaticReconnect(true);
            localMqttConnectOptions.setCleanSession(false);
            localMqttConnectOptions.setUserName(MQTT_USER);
            localMqttConnectOptions.setPassword(MQTT_PASSWORD);
            localMqttConnectOptions.setMqttVersion(4);
            mMqttAndroidClient.connect(localMqttConnectOptions, null, new IMqttActionListener() {
                public void onFailure(IMqttToken paramAnonymousIMqttToken, Throwable paramAnonymousThrowable) {
                    paramAnonymousThrowable.printStackTrace();
                }

                public void onSuccess(IMqttToken paramAnonymousIMqttToken) {
                    DisconnectedBufferOptions options = new DisconnectedBufferOptions();
                    options.setBufferEnabled(true);
                    options.setBufferSize(100);
                    options.setPersistBuffer(false);
                    options.setDeleteOldestMessages(false);
                    CustomApplication.mMqttAndroidClient.setBufferOpts(options);
                    CustomApplication.suscribeUser(paramContext, new SharedPreferencesHelperImpl(paramContext).getOwnUserRawPhoneNumber());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void share(Transaction paramTransaction, String paramString, Context paramContext) {
        byte[] payload = new Gson().toJson(paramTransaction).getBytes();
        if ((mMqttAndroidClient != null) && (mMqttAndroidClient.isConnected())) {
            try {
                mMqttAndroidClient.publish("/mira/inbox/" + paramString, new MqttMessage(payload));

            } catch (MqttException e) {
                Toast.makeText(paramContext, "Error sharing, try again later", Toast.LENGTH_LONG).show();
            }
        } else {
            connectMqtt(paramContext);
            Toast.makeText(paramContext, "Error sharing, try again later", Toast.LENGTH_LONG).show();
        }
    }

    public static void suscribeUser(final Context paramContext, final String paramString) {
        if ((mMqttAndroidClient != null) && (mMqttAndroidClient.isConnected())) {
            try {
                mMqttAndroidClient.subscribe("/mira/inbox/" + paramString, 0, null, new IMqttActionListener() {
                    public void onFailure(IMqttToken paramAnonymousIMqttToken, Throwable paramAnonymousThrowable) {
                        CustomApplication.suscribeUser(paramContext, paramString);
                    }

                    public void onSuccess(IMqttToken paramAnonymousIMqttToken) {
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            connectMqtt(paramContext);
        }
    }

    public void onCreate() {
        super.onCreate();
        new SharedPreferencesHelperImpl(getApplicationContext()).getOwnUserRawPhoneNumber();
        if (!new SharedPreferencesHelperImpl(getApplicationContext()).getOwnUserRawPhoneNumber().isEmpty()) {
            connectMqtt(getApplicationContext());
        }
    }
}
