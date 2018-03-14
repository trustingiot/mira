package com.wordpress.trusted827.mira.usecase;

import com.wordpress.trusted827.mira.db.TransactionDB;
import com.wordpress.trusted827.mira.entity.Transaction;

public class SaveTransactionUseCase {
    private TransactionDB mTransactionDB;

    public SaveTransactionUseCase(TransactionDB paramTransactionDB) {
        this.mTransactionDB = paramTransactionDB;
    }

    public void saveTransaction(final Transaction paramTransaction) {
        new Thread(new Runnable() {
            public void run() {
                SaveTransactionUseCase.this.mTransactionDB.save(paramTransaction);
            }
        }).start();
    }
}
