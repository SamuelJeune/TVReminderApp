package com.example.sam.tvreminderapp.DB.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sam.tvreminderapp.DB.TableObject;
import com.example.sam.tvreminderapp.Object.Episode;
import com.example.sam.tvreminderapp.Object.Season;
import com.example.sam.tvreminderapp.Object.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angelo on 06/01/2018.
 */

public class TvShowDB extends TableObject {

    public static final String TABLE_NAME = "TvShow";
    public static final String KEY = "id";
    public static final String KEY_OMDB = "idOMDB";
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String NUMBER_OF_SEASONS = "nb_season";

    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_OMDB + " TEXT, "
            + TITLE + " TEXT, "
            + YEAR + " TEXT, "
            + NUMBER_OF_SEASONS + " INTEGER);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TvShowDB(Context pContext) {
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

    public long add(String idOMDB, String title, String year, int numberSeasons) {
        long id;
        ContentValues value = new ContentValues();
        value.put(KEY_OMDB, idOMDB);
        value.put(TITLE, title);
        value.put(YEAR, year);
        value.put(NUMBER_OF_SEASONS, numberSeasons);
        this.open();

        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where title = '" + title + "' AND year = '" + year + "'", null);

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
    public void delete(long id) {
        // CODE
    }

    public void update(int idTvShow, String[] keys, String[] params) {
        this.open();
        ContentValues contentValues = new ContentValues();

        for(int i = 0; i < params.length; i++)
            contentValues.put(keys[i], params[i]);

        mDb.update(TABLE_NAME, contentValues, "id = " + idTvShow, null);
        this.close();
    }

    /**
     * @param id identification number of the TvShow
     */
    public TvShow getTvShow(long id) {
        TvShow tvShow = null;

        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where id = " + id, null);

        while (c.moveToNext()) {
            tvShow = new TvShow(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
        }
        c.close();
        return tvShow;
    }

    public void setTvShowSeen(long id, String idOMDB, int seen) {
        SeasonDB seasonDB = new SeasonDB(context);
        ArrayList<Season> listSeasons = new ArrayList<>();
        seasonDB.allSeasonFromTvShow(listSeasons, id);

        for(Season season : listSeasons) {
            String[] params = new String[1];
            String[] values = new String[1];
            params[0] = "seen";
            values[0] = String.valueOf(seen);

            seasonDB.setSeasonSeen(idOMDB, season.getNumber(), seen);
        }
    }

    public boolean isSeen(long id, String idOMDB) {
        SeasonDB seasonDB = new SeasonDB(context);
        ArrayList<Season> listSeasons = new ArrayList<>();
        seasonDB.allSeasonFromTvShow(listSeasons, id);

        for(Season season : listSeasons) {
            if(!seasonDB.isSeen(idOMDB, season.getNumber())) return false;
        }
        return true;
    }

    public TvShow getTvShowByIdOMDB(String id) {
        TvShow tvShow = null;

        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where idOMDB = '" + id + "'", null);

        while (c.moveToNext()) {
            tvShow = new TvShow(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
        }
        c.close();
        return tvShow;
    }

    public ArrayList<TvShow> allTvShows(ArrayList<TvShow> list) {
        //Lecture
        mDb = this.read();

        //Parcours
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        while (c.moveToNext()) { list.add(new TvShow(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4))); }

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
            result += c.getLong(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getInt(4) +"\n";
        }
        c.close();

        return result;
    }
}
