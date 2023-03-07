package com.example.bigbillionadmin.helper;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.bigbillionadmin.model.Game;

import java.util.ArrayList;

public class Functions {
    public static void setData(Activity activity, Spinner spinGame) {

        ArrayList<Game> countryList = new ArrayList<>();
        //Add countries

        countryList.add(new Game("", "Select Game"));
        countryList.add(new Game("FD", "FD-05:35 PM"));
        countryList.add(new Game("GB", "GB-07:35 PM"));
        countryList.add(new Game("GL", "GL-10:35 PM"));
        countryList.add(new Game("", "Next Day Games"));
        countryList.add(new Game("DS", "DS-02:10 AM"));

        //fill data in spinner
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(activity, android.R.layout.simple_spinner_dropdown_item, countryList);
        spinGame.setAdapter(adapter);
        //spinGame.setSelection(adapter.getPosition());//Optional to set the selected item.
    }
}
