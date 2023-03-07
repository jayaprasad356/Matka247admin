package com.example.bigbillionadmin.model;

public class DepositPoints {
    String id,user_id,name,mobile,points,status,date_created;
    public DepositPoints(){

    }

    public DepositPoints(String id, String user_id, String name, String mobile, String points, String status, String date_created) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.mobile = mobile;
        this.points = points;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
