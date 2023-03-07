package com.example.bigbillionadmin.model;

public class Results {
    String id,date,game_name;
    public Results(){

    }

    public Results(String id, String date, String game_name) {
        this.id = id;
        this.date = date;
        this.game_name = game_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }
}
