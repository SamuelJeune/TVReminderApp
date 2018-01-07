package com.example.sam.tvreminderapp.Object;

/**
 * Created by Angelo on 06/01/2018.
 */

public class Movie {

    private long id;
    private String idOMDB;
    private String title;
    private int year;
    private String director;
    private boolean seen;

    public Movie(long id, String idOMDB, String title, int year, String director, boolean seen) {
        this.id = id;
        this.idOMDB = idOMDB;
        this.title = title;
        this.year = year;
        this.director = director;
        this.seen = seen;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Movie : Title : " + title + " & Year : " + year;
    }
}
