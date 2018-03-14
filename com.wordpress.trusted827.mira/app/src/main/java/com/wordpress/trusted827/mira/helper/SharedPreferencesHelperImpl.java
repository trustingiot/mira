package com.wordpress.trusted827.mira.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelperImpl
{
    private Context mContext;

    public SharedPreferencesHelperImpl(Context paramContext)
    {
        this.mContext = paramContext;
    }

    private SharedPreferences getPreferences(String paramString)
    {
        return this.mContext.getSharedPreferences(paramString, 0);
    }

    private SharedPreferences.Editor getPreferencesEditor(String paramString)
    {
        return getPreferences(paramString).edit();
    }

    public String getOwnUserRawPhoneNumber()
    {
        return getPreferences("ownuser_storage").getString("ownuser_phone", "");
    }

    public void setOwnUserRawPhoneNumber(String paramString)
    {
        getPreferencesEditor("ownuser_storage").putString("ownuser_phone", paramString).apply();
    }
}
