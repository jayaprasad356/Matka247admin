package com.example.bigbillionadmin.model;

public class HarufBids {
    String id,user_id,game_name,game_type,number,points,date_created;
    public HarufBids(){

    }

    public HarufBids(String id, String user_id, String game_name, String game_type, String number, String points, String date_created) {
        this.id = id;
        this.user_id = user_id;
        this.game_name = game_name;
        this.game_type = game_type;
        this.number = number;
        this.points = points;
        this.date_created = date_created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
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

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
