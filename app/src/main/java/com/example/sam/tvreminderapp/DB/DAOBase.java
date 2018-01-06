package com.example.sam.tvreminderapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Angelo on 06/01/2018.
 */

public class DAOBase {
    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "database.db";

    protected Context context;
    protected SQLiteDatabase mDb = null;
    protected Handler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new Handler(pContext, NOM, null, VERSION);
        this.context = pContext;
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public SQLiteDatabase read() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getReadableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}