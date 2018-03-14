package com.wordpress.trusted827.mira.db.mapper;


import android.database.Cursor;
import com.wordpress.trusted827.mira.entity.Relative;
import java.util.ArrayList;
import java.util.List;

public class RelativeDBMapper{

    public List<Relative> mapRelativeList(Cursor paramCursor)
    {
        ArrayList localArrayList = new ArrayList();
        if (paramCursor.getCount() > 0) {
            while (paramCursor.moveToNext()) {
                localArrayList.add(new Relative(paramCursor.getString(paramCursor.getColumnIndex("name")), paramCursor.getString(paramCursor.getColumnIndex("mac")), paramCursor.getString(paramCursor.getColumnIndex("password"))));
            }
        }
        paramCursor.close();
        return localArrayList;
    }
}