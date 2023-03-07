package com.example.bigbillionadmin.model;

public class SharePoints {
    String id,mobile,shared_mobile,points,date_created;
    public SharePoints(){

    }

    public SharePoints(String id, String mobile, String shared_mobile, String points, String date_created) {
        this.id = id;
        this.mobile = mobile;
        this.shared_mobile = shared_mobile;
        this.points = points;
        this.date_created = date_created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShared_mobile() {
        return shared_mobile;
    }

    public void setShared_mobile(String shared_mobile) {
        this.shared_mobile = shared_mobile;
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
