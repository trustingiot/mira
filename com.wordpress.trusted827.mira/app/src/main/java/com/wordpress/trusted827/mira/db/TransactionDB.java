package com.wordpress.trusted827.mira.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.wordpress.trusted827.mira.db.mapper.TransactionDBMapper;
import com.wordpress.trusted827.mira.entity.Transaction;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import java.util.List;

public class TransactionDB extends BaseEntityDB
{
    private TransactionDBMapper mTransactionDBMapper;

    public TransactionDB(MIRASQLiteOpenHelper paramMIRASQLiteOpenHelper, TransactionDBMapper paramTransactionDBMapper)
    {
        super(paramMIRASQLiteOpenHelper);
        this.mTransactionDBMapper = paramTransactionDBMapper;
    }

    public static String getCreateTableSentence()
    {
        return "CREATE TABLE transactions (id INTEGER PRIMARY KEY AUTOINCREMENT, device TEXT, name TEXT, key TEXT, timestamp INTEGER, x INTEGER, y INTEGER);";
    }

    private long saveNew(Transaction paramTransaction)
    {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("device", paramTransaction.getDevice());
        localContentValues.put("name", paramTransaction.getTransaction());
        localContentValues.put("key", paramTransaction.getKey());
        localContentValues.put("timestamp", paramTransaction.getTimestamp());
        localContentValues.put("x", paramTransaction.getX());
        localContentValues.put("y", paramTransaction.getY());
        return localSQLiteDatabase.insert("transactions", null, localContentValues);
    }

    private void update(Transaction paramTransaction)
    {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("device", paramTransaction.getDevice());
        localContentValues.put("name", paramTransaction.getTransaction());
        localContentValues.put("key", paramTransaction.getKey());
        localContentValues.put("timestamp", paramTransaction.getTimestamp());
        localContentValues.put("x", paramTransaction.getX());
        localContentValues.put("y", paramTransaction.getY());
        localSQLiteDatabase.update("transactions", localContentValues, "name LIKE " + paramTransaction.getTransaction(), null);
    }

    public boolean checkIfExist(Transaction paramTransaction)
    {

        boolean exist = false;

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM transactions WHERE name = '" + paramTransaction.getTransaction() + "'", null);

        exist = cursor.getCount() > 0;

        cursor.close();

        return exist;
    }

    public List<Transaction> getAll()
    {
        Cursor localCursor = getWritableDatabase().rawQuery("SELECT * FROM transactions", null);
        return this.mTransactionDBMapper.mapTransactionList(localCursor);
    }

    public void save(Transaction paramTransaction)
    {
        if (checkIfExist(paramTransaction))
        {
            update(paramTransaction);
            return;
        }
        saveNew(paramTransaction);
    }
}
