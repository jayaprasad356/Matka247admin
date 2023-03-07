package com.example.bigbillionadmin.model;

public class Game {
    String gamename,gametime;
    public Game(){

    }

    public Game(String gamename, String gametime) {
        this.gamename = gamename;
        this.gametime = gametime;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGametime() {
        return gametime;
    }

    public void setGametime(String gametime) {
        this.gametime = gametime;
    }
    //to display object as a string in spinner
    @Override
    public String toString() {
        return gametime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Game){
            Game c = (Game ) obj;
            if(c.getGametime().equals(gametime) && c.getGamename()==gamename ) return true;
        }

        return false;
    }
}
