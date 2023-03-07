package com.example.bigbillionadmin.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.bigbillionadmin.HomeActivity;
import com.example.bigbillionadmin.MainActivity;


public class Session {
    public static final String PREFER_NAME = "bigwigg";
    final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _activity;



    public Session(Context activity) {
        try {
            this._activity = activity;
            pref = _activity.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setData(String id, String val) {
        editor.putString(id, val);
        editor.commit();
    }

    public void setBoolean(String id, boolean val) {
        editor.putBoolean(id, val);
        editor.commit();
    }
    public void logoutUser(Activity activity) {

        editor.clear();
        editor.commit();


        Intent i = new Intent(activity, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        activity.finish();
    }

    public String getData(String id) {
        return pref.getString(id, "");
    }


    public boolean getBoolean(String id) {
        return pref.getBoolean(id, false);
    }
}