package com.wordpress.trusted827.mira.db.mapper;

import android.database.Cursor;
import com.wordpress.trusted827.mira.entity.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionDBMapper
{

    public List<Transaction> mapTransactionList(Cursor paramCursor)
    {
        ArrayList localArrayList = new ArrayList();
        if (paramCursor.getCount() > 0) {
            while (paramCursor.moveToNext())
            {
                String str1 = paramCursor.getString(paramCursor.getColumnIndex("device"));
                String str2 = paramCursor.getString(paramCursor.getColumnIndex("name"));
                String str3 = paramCursor.getString(paramCursor.getColumnIndex("key"));
                localArrayList.add(new Transaction(str1, paramCursor.getLong(paramCursor.getColumnIndex("timestamp")), paramCursor.getInt(paramCursor.getColumnIndex("x")), paramCursor.getInt(paramCursor.getColumnIndex("y")), str2, str3));
            }
        }
        paramCursor.close();
        return localArrayList;
    }
}
