package com.wordpress.trusted827.mira.db.mapper;

import android.database.Cursor;

import com.wordpress.trusted827.mira.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDBMapper {

    public List<Transaction> mapTransactionList(Cursor paramCursor) {
        ArrayList localArrayList = new ArrayList();
        if (paramCursor.getCount() > 0) {
            while (paramCursor.moveToNext()) {
                String device = paramCursor.getString(paramCursor.getColumnIndex("device"));
                String name = paramCursor.getString(paramCursor.getColumnIndex("name"));
                String key = paramCursor.getString(paramCursor.getColumnIndex("key"));
                int x = paramCursor.getInt(paramCursor.getColumnIndex("x"));
                int y = paramCursor.getInt(paramCursor.getColumnIndex("y"));
                long timestamp = paramCursor.getLong(paramCursor.getColumnIndex("timestamp"));
                String installation = paramCursor.getString(paramCursor.getColumnIndex("installation"));
                localArrayList.add(new Transaction(device, timestamp, x, y, installation, name, key));
            }
        }
        paramCursor.close();
        return localArrayList;
    }
}
