package com.example.sam.tvreminderapp.DB.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sam.tvreminderapp.DB.TableObject;
import com.example.sam.tvreminderapp.Object.Episode;
import com.example.sam.tvreminderapp.Object.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angelo on 09/01/2018.
 */

public class EpisodeDB extends TableObject {
    public static final String TABLE_NAME = "Episode";
    public static final String KEY = "id";
    public static final String NUMBER = "number";
    public static final String TITLE = "title";
    public static final String SEEN = "seen";
    public static final String ID_SEASON = "idSeason";

    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + TITLE + " TEXT, "
            + NUMBER + " INTEGER,"
            + SEEN + " INTEGER,"
            + ID_SEASON + " INTEGER,"
            + " FOREIGN KEY ("+ID_SEASON+") REFERENCES Season (id));";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public EpisodeDB(Context pContext) {
        super(pContext);
        this.mHandler.addTable(this);
        mDb = this.open();
        mHandler.onCreate(mDb);
    }

    @Override
    /**
     * Création de la table dans le cas où elle n'existe pas
     */
    public String create() {
        return TABLE_CREATE;
    }

    @Override
    /**
     * Suppression de la table
     */
    public String delete() {
        return TABLE_DROP;
    }

    @Override
    public void upgrade(int version) {

    }

    public long add(String title, int number, int seen, long idSeason) {
        long id;
        ContentValues value = new ContentValues();
        value.put(TITLE, title);
        value.put(NUMBER, number);
        value.put(SEEN, seen);
        value.put(ID_SEASON, idSeason);
        this.open();

        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where title = '" + title + "' AND idSeason = " + idSeason, null);

        if(c.getCount() == 0) {
            id = mDb.insert(TABLE_NAME, null, value);
        }
        else {
            mDb = this.read();
            c.moveToFirst();
            id = c.getInt(0);
            mDb.close();
        }
        c.close();
        return id;
    }

    /**
     * Delete a TvShow
     * @param id identification number of the TvShow
     */
    public void delete(String id) {
        // CODE
        mDb = this.read();
        mDb.delete(TABLE_NAME, "idOMDB = '" + id + "'", null);
        this.close();
    }

    public void update(int idMovie, String[] keys, String[] params) {
        this.open();
        ContentValues contentValues = new ContentValues();

        for(int i = 0; i < params.length; i++)
            contentValues.put(keys[i], params[i]);

        mDb.update(TABLE_NAME, contentValues, "id = " + idMovie, null);
        this.close();
    }

    /**
     * @param id identification number of the TvShow
     */
    public Episode getEpisode(long id) {
        Episode episode = null;

        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = " + id, null);

        while (c.moveToNext()) {
            episode = new Episode(c.getLong(0), c.getString(1), c.getInt(2), c.getInt(3) == 1, c.getInt(4));
        }
        c.close();
        return episode;
    }

    public ArrayList<Episode> allEpisodeFromSeason(ArrayList<Episode> list, long idSeason, int number) {
        //Lecture
        mDb = this.read();

        //Parcours
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " WHERE number = " + number + " AND idSeason = " + idSeason, null);
        while (c.moveToNext()) { list.add(new Episode(c.getLong(0), c.getString(1), c.getInt(2), c.getInt(3) == 1, c.getInt(4))); }

        //Fermeture flux
        c.close();
        return list;
    }

    /**
     * Affichage console des enseignants enregistré
     * @return
     */
    public String toString()
    {
        String result = "";
        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        while (c.moveToNext()) {
            result += c.getLong(0) + " " + c.getString(1) + " " + c.getInt(2) + " " + c.getString(3) +"\n";
        }
        c.close();

        return result;
    }
}