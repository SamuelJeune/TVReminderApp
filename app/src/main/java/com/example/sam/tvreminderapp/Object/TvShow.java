package com.example.sam.tvreminderapp.Object;

/**
 * Created by Angelo on 06/01/2018.
 */

public class TvShow extends Item{

    private long id;
    private String idOMDB;
    private String title;
    private String year;
    private int nbSeasons;

    public TvShow(long id, String idOMDB, String title, String year, int nbSeasons) {
        super("series");
        this.id = id;
        this.idOMDB = idOMDB;
        this.title = title;
        this.year = year;
        this.nbSeasons = nbSeasons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdOMDB() {
        return idOMDB;
    }

    public void setIdOMDB(String idOMDB) {
        this.idOMDB = idOMDB;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNbSeasons() {
        return nbSeasons;
    }

    public void setNbSeasons(int nbSeasons) {
        this.nbSeasons = nbSeasons;
    }

    public String toString()
    {
        return this.title + " " + this.year;
    }
}
