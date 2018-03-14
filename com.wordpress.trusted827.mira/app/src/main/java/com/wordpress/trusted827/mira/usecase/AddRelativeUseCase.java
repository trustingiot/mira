package com.wordpress.trusted827.mira.usecase;

import com.wordpress.trusted827.mira.db.RelativeDB;
import com.wordpress.trusted827.mira.entity.Relative;

public class AddRelativeUseCase
{
    private RelativeDB mRelativeDB;

    public AddRelativeUseCase(RelativeDB paramRelativeDB)
    {
        this.mRelativeDB = paramRelativeDB;
    }

    public void addRelative(final Relative paramRelative, final Callback<Void> paramCallback)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                AddRelativeUseCase.this.mRelativeDB.save(paramRelative);
                paramCallback.onSuccess(null);
            }
        }).start();
    }
}
