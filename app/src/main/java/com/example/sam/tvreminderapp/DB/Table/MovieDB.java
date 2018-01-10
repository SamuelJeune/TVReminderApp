package com.example.sam.tvreminderapp.DB.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sam.tvreminderapp.DB.TableObject;
import com.example.sam.tvreminderapp.Object.Movie;
import com.example.sam.tvreminderapp.Object.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angelo on 06/01/2018.
 */

public class MovieDB extends TableObject {
    public static final String TABLE_NAME = "Movie";
    public static final String KEY = "id";
    public static final String KEY_OMDB = "idOMDB";
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String DIRECTOR = "director";
    public static final String SEEN = "seen";

    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_OMDB + " TEXT, "
            + TITLE + " TEXT, "
            + YEAR + " INTEGER, "
            + DIRECTOR + " TEXT, "
            + SEEN + " INTEGER);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public MovieDB(Context pContext) {
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
        if(version == 2) {
            Cursor c = mDb.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+ TABLE_NAME +"'", null);
            if(c.getCount() == 0) {
                List<String> columns = mHandler.GetColumns(getDb(), TABLE_NAME);
                getDb().execSQL("ALTER TABLE " + TABLE_NAME + " RENAME TO temp_" + TABLE_NAME);
                getDb().execSQL(create());
                columns.retainAll(mHandler.GetColumns(getDb(), TABLE_NAME));
                String cols = mHandler.join(columns, "'");
                getDb().execSQL(String.format("INSERT INTO %s (%s) SELECT %s from temp_%s",
                        TABLE_NAME, cols, cols, TABLE_NAME));
                getDb().execSQL("DROP TABLE temp_" + TABLE_NAME);
            }
            else {
                getDb().execSQL(create());
            }
        }
    }

    public long add(String idOMDB, String title, int year, String director, int seen) {
        long id;
        ContentValues value = new ContentValues();
        value.put(KEY_OMDB, idOMDB);
        value.put(TITLE, title);
        value.put(YEAR, year);
        value.put(DIRECTOR, director);
        value.put(SEEN, seen);
        this.open();

        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where title = '" + title + "' AND year = " + year, null);

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

    public void update(long idMovie, String[] keys, String[] params) {
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
    public Movie getMovie(long id) {
        Movie movie = null;

        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = " + id, null);

        while (c.moveToNext()) {
            movie = new Movie(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5) == 1);
        }
        c.close();
        return movie;
    }

    public Movie getMovieByIMDBID(String id) {
        Movie movie = null;

        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where idOMDB = '" + id + "'", null);
        if(c.getCount()<=0){
            c.close();
            return null;
        }

        while (c.moveToNext()) {
            movie = new Movie(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5) == 1);
        }
        c.close();
        return movie;
    }

    public ArrayList<Movie> allMovies(ArrayList<Movie> list) {
        //Lecture
        mDb = this.read();

        //Parcours
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        while (c.moveToNext()) { list.add(new Movie(c.getLong(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5) == 1)); }

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
