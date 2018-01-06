package com.example.sam.tvreminderapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Angelo on 06/01/2018.
 */

public class Handler extends SQLiteOpenHelper {

    /**
     * Attributs
     */
    private ArrayList<TableObject> listeTable = new ArrayList<>();

    /**
     * Constructeur
     * @param context   Contexte de l'application
     * @param name      Nom de la BDD
     * @param factory
     * @param version   Version de la BDD
     */
    public Handler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    /**
     * Création des tables
     */
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < listeTable.size(); i++) {
            //System.out.println(listeTable.get(i).toString());
            if(db.getVersion() == 1) {
                listeTable.get(i).upgrade(2);
            }
            else {
                db.execSQL(listeTable.get(i).create());
            }
        }
    }

    @Override
    /**
     * Delete puis création des tables
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*for(int i = 0; i < listeTable.size(); i++) {
            db.execSQL(listeTable.get(i).delete());
        }*/
        onCreate(db);
        db.setVersion(2);
    }

    /**
     * Ajout d'une table
     * @param to table
     */
    public void addTable(TableObject to)
    {
        listeTable.add(to);
    }

    /**
     * Delete d'une table
     * @param db bdd
     */
    public void dropTable(SQLiteDatabase db)
    {
        for(int i = 0; i < listeTable.size(); i++) {
            db.execSQL(listeTable.get(i).delete());
        }
    }

    public static List<String> GetColumns(SQLiteDatabase db, String tableName) {
        List<String> ar = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + tableName + " limit 1", null);
            if (c != null) {
                ar = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
        return ar;
    }

    public static String join(List<String> list, String delim) {
        StringBuilder buf = new StringBuilder();
        int num = list.size();
        for (int i = 0; i < num; i++) {
            if (i != 0)
                buf.append(delim);
            buf.append((String) list.get(i));
        }
        return buf.toString();
    }
}