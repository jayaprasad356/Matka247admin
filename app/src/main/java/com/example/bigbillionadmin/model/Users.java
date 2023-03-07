package com.example.bigbillionadmin.model;

public class Users {
    String id,name,mobile,points,earn,date_created,user_status,account_number,ifsc_code,paytm,phonepe,holder_name;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getPaytm() {
        return paytm;
    }

    public void setPaytm(String paytm) {
        this.paytm = paytm;
    }

    public String getPhonepe() {
        return phonepe;
    }

    public void setPhonepe(String phonepe) {
        this.phonepe = phonepe;
    }

    public Users(){

    }

    public Users(String id, String name, String mobile, String points, String earn, String date_created, String user_status, String account_number, String ifsc_code, String paytm, String phonepe, String holder_name) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.points = points;
        this.earn = earn;
        this.date_created = date_created;
        this.user_status = user_status;
        this.account_number = account_number;
        this.ifsc_code = ifsc_code;
        this.paytm = paytm;
        this.phonepe = phonepe;
        this.holder_name = holder_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEarn() {
        return earn;
    }

    public void setEarn(String earn) {
        this.earn = earn;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }
}
