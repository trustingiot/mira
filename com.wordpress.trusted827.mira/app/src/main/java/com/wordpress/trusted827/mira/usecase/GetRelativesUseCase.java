package com.wordpress.trusted827.mira.usecase;

import com.wordpress.trusted827.mira.db.RelativeDB;
import com.wordpress.trusted827.mira.entity.Relative;
import java.util.List;

public class GetRelativesUseCase
{
    private RelativeDB mRelativeDB;

    public GetRelativesUseCase(RelativeDB paramRelativeDB)
    {
        this.mRelativeDB = paramRelativeDB;
    }

    public void getRelatives(final Callback<List<Relative>> paramCallback)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                paramCallback.onSuccess(GetRelativesUseCase.this.mRelativeDB.getAll());
            }
        }).start();
    }
}
