package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.UsersAdapter;
import com.example.bigbillionadmin.adapter.WithdrawalAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.Users;
import com.example.bigbillionadmin.model.Withdrawal;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    Activity activity;
    EditText etSearch;
    ArrayList<Users> transactions = new ArrayList<>();
    TextView tvTotalUsers,tvTotalPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        activity = UsersActivity.this;
        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        tvTotalUsers = findViewById(R.id.tvTotalUsers);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        usersAdapter = new UsersAdapter(activity, transactions);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    usersList(editable.toString());
                }else {
                    usersList("");

                }

            }
        });
        usersList("");


    }

    private void usersList(String s)
    {
        transactions.clear();
        usersAdapter.notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.SEARCH,s);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("user_response",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvTotalUsers.setText("No. Of Users = "+jsonObject.getString(Constant.TOTALUSERS));
                        tvTotalPoints.setText("Total Points = "+jsonObject.getString(Constant.TOTAL_POINTS));
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Users group = g.fromJson(jsonObject1.toString(), Users.class);
                                transactions.add(group);
                            } else {
                                break;
                            }
                        }

                        recyclerView.setAdapter(usersAdapter);
                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.USERS_LIST_URL, params, true);
    }
}