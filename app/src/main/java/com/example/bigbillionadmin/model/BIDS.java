package com.example.bigbillionadmin.model;

public class BIDS {
    String number,points,game_type;
    public BIDS(){

    }

    public BIDS(String number, String points, String game_type) {
        this.number = number;
        this.points = points;
        this.game_type = game_type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }
}
