package com.example.bigbillionadmin.model;

public class Withdrawal {
    String id,name,mobile,points,status,date_created,account_number,ifsc_code,holder_name,paytm,phonepe;
    public Withdrawal(){

    }

    public Withdrawal(String id, String name, String mobile, String points, String status, String date_created, String account_number, String ifsc_code, String holder_name, String paytm, String phonepe) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.points = points;
        this.status = status;
        this.date_created = date_created;
        this.account_number = account_number;
        this.ifsc_code = ifsc_code;
        this.holder_name = holder_name;
        this.paytm = paytm;
        this.phonepe = phonepe;
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

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
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
}
