package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.BiddingUsersAdapter;
import com.example.bigbillionadmin.adapter.UsersAdapter;
import com.example.bigbillionadmin.adapter.WinnersAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Functions;
import com.example.bigbillionadmin.model.BIDS;
import com.example.bigbillionadmin.model.Game;
import com.example.bigbillionadmin.model.Users;
import com.example.bigbillionadmin.model.Winners;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Show_biddinsActivity extends AppCompatActivity {
    String format = "%1$02d";
    DatePicker picker;
    String date;
    String spinGameName;
    Spinner spinGame;
    Activity activity;
    Button btnResult;
    String day,month,year;
    ArrayList<Users> transactions = new ArrayList<>();
    BiddingUsersAdapter biddingUsersAdapter;
    RecyclerView recyclerView;
    TextView tvUsers,tvPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_biddins);
        activity = Show_biddinsActivity.this;
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnResult=findViewById(R.id.btnResult);
        recyclerView=findViewById(R.id.recyclerView);
        spinGame=findViewById(R.id.spinGame);
        tvUsers=findViewById(R.id.tvUsers);
        tvPoints=findViewById(R.id.tvPoints);
        Functions.setData(activity,spinGame);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        biddingUsersAdapter = new BiddingUsersAdapter(activity, transactions);

        spinGame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Game game = (Game) adapterView.getSelectedItem();
                spinGameName = game.getGamename();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinGame.getSelectedItemPosition() == 0){
                    Toast.makeText(activity, "Select Game", Toast.LENGTH_SHORT).show();
                }
                else {
                    day = ""+picker.getDayOfMonth();
                    day = String.format(format, Long.parseLong(day));
                    month = ""+(picker.getMonth() + 1);
                    month = String.format(format, Long.parseLong(month));
                    year = ""+picker.getYear();
                    year = String.format(format, Long.parseLong(year));
                    date = year +"-"+month + "-"+day;

                    winnerList(spinGameName,date);



                }

            }
        });



    }
    private void winnerList(String spinGameName, String date)
    {
        transactions.clear();
        biddingUsersAdapter.notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.DATE, date);
        params.put(Constant.GAME_NAME, spinGameName);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvUsers.setText("Users \n"+jsonObject.getString(Constant.TOTALUSERS));
                        tvPoints.setText("Total Points \n"+jsonObject.getString(Constant.TOTAL_POINTS));
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
                        recyclerView.setAdapter(biddingUsersAdapter);
                    }
                    else {
                        tvUsers.setText("Users \n0");
                        tvPoints.setText("Total Points \n0");

                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.BIDS_USER_URL, params, true);
    }
}