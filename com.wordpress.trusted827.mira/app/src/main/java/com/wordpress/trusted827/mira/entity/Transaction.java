package com.wordpress.trusted827.mira.entity;

import java.io.Serializable;

public class Transaction
        implements Serializable {
    int X;
    int Y;
    String device;
    String key;
    long timestamp;
    String installation;
    String transaction;

    public Transaction(String device, long timestamp, int x, int y, String installation, String transaction, String key) {
        this.device = device;
        this.timestamp = timestamp;
        this.X = x;
        this.Y = y;
        this.installation = installation;
        this.transaction = transaction;
        this.key = key;
    }

    public String getDevice() {
        return this.device;
    }

    public String getKey() {
        return this.key;
    }

    public String getInstallation() {
        return this.installation;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getTransaction() {
        return this.transaction;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public void setDevice(String paramString) {
        this.device = paramString;
    }

    public void setKey(String paramString) {
        this.key = paramString;
    }

    public void setTimestamp(long paramLong) {
        this.timestamp = paramLong;
    }

    public void setInstallation(String paramString) {
        this.installation = paramString;
    }

    public void setTransaction(String paramString) {
        this.transaction = paramString;
    }

    public void setX(int paramInt) {
        this.X = paramInt;
    }

    public void setY(int paramInt) {
        this.Y = paramInt;
    }
}
