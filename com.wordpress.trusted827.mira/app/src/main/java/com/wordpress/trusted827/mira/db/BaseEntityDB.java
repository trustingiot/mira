package com.wordpress.trusted827.mira.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;

public abstract class BaseEntityDB
{
    protected static final String ID = "id";
    private MIRASQLiteOpenHelper mMIRASQLiteOpenHelper;

    public BaseEntityDB(MIRASQLiteOpenHelper paramMIRASQLiteOpenHelper)
    {
        this.mMIRASQLiteOpenHelper = paramMIRASQLiteOpenHelper;
    }

    protected void executeQuery(String paramString)
    {
        getWritableDatabase().rawQuery(paramString, null).close();
    }

    protected SQLiteDatabase getReadableDatabase()
    {
        return this.mMIRASQLiteOpenHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase()
    {
        return this.mMIRASQLiteOpenHelper.getWritableDatabase();
    }
}
