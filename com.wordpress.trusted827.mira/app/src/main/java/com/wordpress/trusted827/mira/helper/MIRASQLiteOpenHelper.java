package com.wordpress.trusted827.mira.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wordpress.trusted827.mira.db.RelativeDB;
import com.wordpress.trusted827.mira.db.TransactionDB;

public class MIRASQLiteOpenHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "mira.db";
    protected static final int DATABASE_VERSION = 1;
    private static MIRASQLiteOpenHelper sInstance;

    private MIRASQLiteOpenHelper(Context paramContext) {
        super(paramContext, "mira.db", null, 1);
    }

    public static MIRASQLiteOpenHelper getInstance(Context paramContext) {
        try {
            if (sInstance == null) {
                sInstance = new MIRASQLiteOpenHelper(paramContext);
            }
            return sInstance;
        } finally {
        }
    }

    public String getDatabaseName() {
        return "mira.db";
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL(RelativeDB.getCreateTableSentence());
        paramSQLiteDatabase.execSQL(TransactionDB.getCreateTableSentence());
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    }
}
