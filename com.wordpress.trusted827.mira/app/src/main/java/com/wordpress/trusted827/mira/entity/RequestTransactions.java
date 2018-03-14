package com.wordpress.trusted827.mira.entity;

public class RequestTransactions
{
    private String device;
    private String diw;
    private int index;

    public RequestTransactions(String device, String diw, int index)
    {
        this.device = device;
        this.diw = diw;
        this.index = index;
    }
}
