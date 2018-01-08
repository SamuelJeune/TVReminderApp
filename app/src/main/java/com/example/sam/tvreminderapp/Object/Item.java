package com.example.sam.tvreminderapp.Object;

/**
 * Created by Angelo on 08/01/2018.
 */

public abstract class Item {
    protected String type;

    public Item(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract String getIdOMDB();
    public abstract String getTitle();
    public abstract String getYear();
}
