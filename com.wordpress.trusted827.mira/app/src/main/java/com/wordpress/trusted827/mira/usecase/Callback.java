package com.wordpress.trusted827.mira.usecase;

public interface Callback<T>
{
    void onError();

    void onSuccess(T paramT);
}
