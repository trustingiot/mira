package com.wordpress.trusted827.mira.entity;

import java.io.Serializable;

public class Relative
        implements Serializable
{
    private String mac;
    private String name;
    private String password;

    public Relative(String paramString1, String paramString2, String paramString3)
    {
        this.name = paramString1;
        this.mac = paramString2;
        this.password = paramString3;
    }

    public String getMac()
    {
        return this.mac;
    }

    public String getName()
    {
        return this.name;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setMac(String paramString)
    {
        this.mac = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setPassword(String paramString)
    {
        this.password = paramString;
    }
}

