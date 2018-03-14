package com.wordpress.trusted827.mira.usecase;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wordpress.trusted827.mira.db.TransactionDB;
import com.wordpress.trusted827.mira.entity.RequestTransactions;
import com.wordpress.trusted827.mira.entity.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetTransactionsUseCase
{
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private TransactionDB mTransactionDB;

    public GetTransactionsUseCase(TransactionDB paramTransactionDB)
    {
        this.mTransactionDB = paramTransactionDB;
    }

    public void getDeviceTransactions(final String device, final String pass, final Callback<ArrayList<Transaction>> paramCallback)
    {
        new Thread(new Runnable()
        {
            public void run()
            {

                ArrayList<Transaction> transactions = new ArrayList<>();

                RequestTransactions requestTransactions = new RequestTransactions(device, pass, 0);
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, new Gson().toJson(requestTransactions));


                // Create request for remote resource.
                Request request = new Request.Builder()
                        .url("server" + "/firma/transactions")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    // Deserialize HTTP response to concrete type

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    JSONArray array = jsonObject.optJSONArray("transactions");
                    if (array != null) {
                        for (int i = 0; i < array.length(); i++) {
                            long timestamp = array.getJSONObject(i).getJSONObject("body").getLong("timestamp");
                            int x = array.getJSONObject(i).getJSONObject("body").getJSONObject("location").getJSONObject("point").getInt("X");
                            int y = array.getJSONObject(i).getJSONObject("body").getJSONObject("location").getJSONObject("point").getInt("Y");
                            String name = array.getJSONObject(i).getString("transaction");
                            String key = array.getJSONObject(i).getString("key");
                            transactions.add(new Transaction(device, timestamp, x, y, name, key));
                        }
                    }

                    paramCallback.onSuccess(transactions);

                } catch (IOException | JSONException e){
                    paramCallback.onError();
                }
            }
        }).start();
    }

    public void getSharedTransactions(final Callback<List<Transaction>> paramCallback)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                paramCallback.onSuccess(GetTransactionsUseCase.this.mTransactionDB.getAll());
            }
        }).start();
    }
}
