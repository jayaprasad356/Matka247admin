package com.example.bigbillionadmin.model;

public class Transaction {
    String id,user_id,game_name,game_type,game_method,balance,points,type,share_to,share_from,date_created,reason;
    public Transaction(){

    }

    public Transaction(String id, String user_id, String game_name, String game_type, String game_method, String balance, String points, String type, String share_to, String share_from, String date_created, String reason) {
        this.id = id;
        this.user_id = user_id;
        this.game_name = game_name;
        this.game_type = game_type;
        this.game_method = game_method;
        this.balance = balance;
        this.points = points;
        this.type = type;
        this.share_to = share_to;
        this.share_from = share_from;
        this.date_created = date_created;
        this.reason = reason;
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

    public String getGame_method() {
        return game_method;
    }

    public void setGame_method(String game_method) {
        this.game_method = game_method;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShare_to() {
        return share_to;
    }

    public void setShare_to(String share_to) {
        this.share_to = share_to;
    }

    public String getShare_from() {
        return share_from;
    }

    public void setShare_from(String share_from) {
        this.share_from = share_from;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
