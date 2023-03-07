package com.example.bigbillionadmin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.DepositPointsAdapter;
import com.example.bigbillionadmin.adapter.WithdrawalAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.DepositPoints;
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

public class WithdrawalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WithdrawalAdapter withdrawalAdapter;
    Activity activity;
    String format = "%1$02d";
    DatePicker picker;
    String date;
    String day,month,year;
    Button btnSubmit;
    ArrayList<Withdrawal> transactions = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        activity = WithdrawalActivity.this;
        recyclerView = findViewById(R.id.recyclerView);
        btnSubmit = findViewById(R.id.btnSubmit);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = dateObj.format(formatter);
        withdrawalAdapter = new WithdrawalAdapter(activity, transactions);
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
                withdrwalList();
                Log.d("DEP_DATE",date);
            }
        });
        withdrwalList();



    }

    private void withdrwalList()
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
                                Withdrawal group = g.fromJson(jsonObject1.toString(), Withdrawal.class);
                                transactions.add(group);
                            } else {
                                break;
                            }
                        }

                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }
                    recyclerView.setAdapter(withdrawalAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.WITHDRAWAL_LISTS_URL, params, true);
    }
}