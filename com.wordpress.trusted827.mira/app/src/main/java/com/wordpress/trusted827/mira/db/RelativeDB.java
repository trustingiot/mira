package com.wordpress.trusted827.mira.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.wordpress.trusted827.mira.db.mapper.RelativeDBMapper;
import com.wordpress.trusted827.mira.entity.Relative;
import com.wordpress.trusted827.mira.helper.MIRASQLiteOpenHelper;
import java.util.List;

public class RelativeDB extends BaseEntityDB
{
    private RelativeDBMapper mRelativeDBMapper;

    public RelativeDB(MIRASQLiteOpenHelper paramMIRASQLiteOpenHelper, RelativeDBMapper paramRelativeDBMapper)
    {
        super(paramMIRASQLiteOpenHelper);
        this.mRelativeDBMapper = paramRelativeDBMapper;
    }

    public static String getCreateTableSentence()
    {
        return "CREATE TABLE relative (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mac TEXT, password TEXT);";
    }

    private long saveNew(Relative paramRelative)
    {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("name", paramRelative.getName());
        localContentValues.put("mac", paramRelative.getMac());
        localContentValues.put("password", paramRelative.getPassword());
        return localSQLiteDatabase.insert("relative", null, localContentValues);
    }

    private void update(Relative paramRelative)
    {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("name", paramRelative.getName());
        localContentValues.put("mac", paramRelative.getMac());
        localContentValues.put("password", paramRelative.getPassword());
        localSQLiteDatabase.update("relative", localContentValues, "mac LIKE " + paramRelative.getMac(), null);
    }

    public boolean checkIfExist(Relative paramRelative){

        boolean exist = false;

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM relative WHERE mac = '" + paramRelative.getMac() + "'", null);

        exist = cursor.getCount() > 0;

        cursor.close();

        return exist;
    }

    public List<Relative> getAll()
    {
        Cursor localCursor = getWritableDatabase().rawQuery("SELECT * FROM relative", null);
        return this.mRelativeDBMapper.mapRelativeList(localCursor);
    }

    public void save(Relative paramRelative)
    {
        if (checkIfExist(paramRelative))
        {
            update(paramRelative);
            return;
        }
        saveNew(paramRelative);
    }
}
