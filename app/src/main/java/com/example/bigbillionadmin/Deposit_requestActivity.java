package com.example.bigbillionadmin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.DepositPointsAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.DepositPoints;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Deposit_requestActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    DepositPointsAdapter depositPointsAdapter;
    Activity activity;
    Button btnSubmit;
    String date;
    String day,month,year;
    ArrayList<DepositPoints> transactions = new ArrayList<>();
    String format = "%1$02d";
    DatePicker picker;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_request);
        activity = Deposit_requestActivity.this;
        recyclerView = findViewById(R.id.recyclerView);
        btnSubmit = findViewById(R.id.btnSubmit);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        depositPointsAdapter = new DepositPointsAdapter(activity, transactions);
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = dateObj.format(formatter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = ""+picker.getDayOfMonth();
                day = String.format(format, Long.parseLong(day));
                month = ""+(picker.getMonth() + 1);
                month = String.format(format, Long.parseLong(month));
                year = ""+picker.getYear();
                year = String.format(format, Long.parseLong(year));
                date = year +"-"+month + "-"+day;
                depositList();
            }
        });
        depositList();
    }
    private void depositList()
    {
        transactions.clear();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                DepositPoints group = g.fromJson(jsonObject1.toString(), DepositPoints.class);
                                transactions.add(group);
                            } else {
                                break;
                            }
                        }


                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                    recyclerView.setAdapter(depositPointsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.POINTS_LIST_URL, params, true);
    }
}