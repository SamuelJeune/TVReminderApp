package com.example.sam.tvreminderapp.Object;

/**
 * Created by Angelo on 09/01/2018.
 */

public class Episode {

    private long id;
    private int number;
    private String title;
    private boolean seen;
    private long idSeason;

    public Episode(long id, String title, int number, boolean seen, long idSeason) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.seen = seen;
        this.idSeason = idSeason;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getIdSeason() {
        return idSeason;
    }

    public void setIdSeason(long idSeason) {
        this.idSeason = idSeason;
    }
}
