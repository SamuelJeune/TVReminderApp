package com.example.sam.tvreminderapp.Object;

/**
 * Created by Angelo on 09/01/2018.
 */

public class Season {

    private long id;
    private int number;
    private long idTvShow;

    public Season(long id, int number, long idTvShow) {
        this.id = id;
        this.number = number;
        this.idTvShow = idTvShow;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getIdTvShow() {
        return idTvShow;
    }

    public void setIdTvShow(long idTvShow) {
        this.idTvShow = idTvShow;
    }
}
