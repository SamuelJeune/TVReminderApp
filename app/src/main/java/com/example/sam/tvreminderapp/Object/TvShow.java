package com.example.sam.tvreminderapp.Object;

/**
 * Created by Angelo on 06/01/2018.
 */

public class TvShow {

    private long id;
    private String title;
    private int year;
    private int nbSeasons;

    public TvShow(long id, String title, int year, int nbSeasons) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
