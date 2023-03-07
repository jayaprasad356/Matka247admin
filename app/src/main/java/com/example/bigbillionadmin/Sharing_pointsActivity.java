package com.example.bigbillionadmin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.SharePointsAdapter;
import com.example.bigbillionadmin.adapter.WithdrawalAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.SharePoints;
import com.example.bigbillionadmin.model.Withdrawal;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sharing_pointsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharePointsAdapter sharePointsAdapter;
    Activity activity;
    String format = "%1$02d";
    DatePicker picker;
    String date;
    String day,month,year;
    ArrayList<SharePoints> transactions = new ArrayList<>();
    Button btnSubmit;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_points);
        activity = Sharing_pointsActivity.this;
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = dateObj.format(formatter);
        btnSubmit = findViewById(R.id.btnSubmit);
        picker=(DatePicker)findViewById(R.id.datePicker1);

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
                sharePointsList();
            }
        });
        sharePointsList();
    }

    private void sharePointsList()
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
                                SharePoints group = g.fromJson(jsonObject1.toString(), SharePoints.class);
                                transactions.add(group);
                            } else {
                                break;
                            }
                        }
                        sharePointsAdapter = new SharePointsAdapter(activity, transactions);

                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                    recyclerView.setAdapter(sharePointsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.SHARED_POINTS_LIST_URL, params, true);
    }
}