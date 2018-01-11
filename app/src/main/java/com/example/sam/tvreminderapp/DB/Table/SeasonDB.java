package com.example.sam.tvreminderapp.DB.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.sam.tvreminderapp.DB.TableObject;
import com.example.sam.tvreminderapp.Object.Episode;
import com.example.sam.tvreminderapp.Object.Season;

import java.util.ArrayList;

/**
 * Created by Angelo on 09/01/2018.
 */

public class SeasonDB extends TableObject {
    public static final String TABLE_NAME = "Season";
    public static final String KEY = "id";
    public static final String NUMBER = "number";
    public static final String ID_TVSHOW = "idTvShow";

    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + NUMBER + " INTEGER,"
            + ID_TVSHOW + " INTEGER,"
            + " FOREIGN KEY ("+ID_TVSHOW+") REFERENCES TvShow (id));";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public SeasonDB(Context pContext) {
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

    public long add(int number, long idTvShow) {
        long id;
        ContentValues value = new ContentValues();
        value.put(NUMBER, number);
        value.put(ID_TVSHOW, idTvShow);
        this.open();

        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where number = " + number + " AND  idTvShow  = " + idTvShow, null);

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
     * @param idOMDB identification number of the TvShow
     */
    public Season getSeason(String idOMDB, int number) {
        Season season = null;

        mDb = this.read();
        System.out.println("select * from " + TABLE_NAME + " INNER JOIN TvShow ON TvShow.id=Season.idTvShow" +
                " where Season.number = " + number + " AND idOMDB = " + idOMDB);
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " INNER JOIN TvShow ON TvShow.id=Season.idTvShow" +
                " where Season.number = " + number + " AND idOMDB = '" + idOMDB + "'", null);

        while (c.moveToNext()) {
            season = new Season(c.getLong(0), c.getInt(1), c.getInt(2));
        }
        c.close();
        return season;
    }

    public boolean isSeen(String idOMDB, int number) {
        EpisodeDB episodeDB = new EpisodeDB(context);
        ArrayList<Episode> listEpisodes = new ArrayList<>();
        long idSeason = getSeason(idOMDB, number).getId();

        episodeDB.allEpisodeFromSeason(listEpisodes, idSeason, number);

        for(Episode episode : listEpisodes) {
            if(!episode.isSeen()) return false;
        }
        return true;
    }

    public void setSeasonSeen(String idTvShow, int number, int seen) {
        EpisodeDB episodeDB = new EpisodeDB(context);
        ArrayList<Episode> listEpisodes = new ArrayList<>();
        long idSeason = getSeason(idTvShow, number).getId();

        episodeDB.allEpisodeFromSeason(listEpisodes, idSeason, number);

        for(Episode episode : listEpisodes) {
            String[] params = new String[1];
            String[] values = new String[1];
            params[0] = "seen";
            values[0] = String.valueOf(seen);

            episodeDB.update(episode.getId(), params, values);
        }
    }

    public Season getSeasonByIdTvShow(long id, int number) {
        Season season = null;

        mDb = this.read();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " where number = " + number + " AND idTvShow = " + id, null);

        while (c.moveToNext()) {
            season = new Season(c.getLong(0), c.getInt(1), c.getInt(2));
        }
        c.close();
        return season;
    }

    public ArrayList<Season> allSeasonFromTvShow(ArrayList<Season> list, long idTvShow) {
        //Lecture
        mDb = this.read();

        //Parcours
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME + " WHERE idTvShow = " + idTvShow, null);
        while (c.moveToNext()) { list.add(new Season(c.getLong(0), c.getInt(1), c.getInt(2))); }

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
            result += c.getLong(0) + " " + c.getString(1) + " " + c.getInt(2) +"\n";
        }
        c.close();

        return result;
    }
}