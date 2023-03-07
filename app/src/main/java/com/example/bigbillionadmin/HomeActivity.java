package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    RelativeLayout deposit_req,withdrawal,declare_result,show_biddings,sharing_point,winners,users,settings,all_biddings;
    Activity activity;
    Session session;
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = HomeActivity.this;
        session = new Session(activity);
        deposit_req = findViewById(R.id.deposit_req);
        withdrawal = findViewById(R.id.withdrawal);
        declare_result = findViewById(R.id.declare_result);
        show_biddings = findViewById(R.id.show_biddings);
        sharing_point = findViewById(R.id.sharing_point);
        winners = findViewById(R.id.winners);
        users = findViewById(R.id.users);
        settings = findViewById(R.id.settings);
        all_biddings = findViewById(R.id.all_biddings);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setBoolean("is_logged_in", false);
               session.logoutUser(activity);

            }
        });

        deposit_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Deposit_requestActivity.class);
                startActivity(intent);

            }
        });
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, WithdrawalActivity.class);
                startActivity(intent);

            }
        });
        declare_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Declare_resultActivity.class);
                startActivity(intent);

            }
        });
        show_biddings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Show_biddinsActivity.class);
                startActivity(intent);


            }
        });
        sharing_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Sharing_pointsActivity.class);
                startActivity(intent);

            }
        });
        winners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, WinnersActivity.class);
                startActivity(intent);

            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UsersActivity.class);
                startActivity(intent);

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SettingsActivity.class);
                startActivity(intent);

            }
        });
        all_biddings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, All_biddingsActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.WHATSAPP_NUM,jsonArray.getJSONObject(0).getString(Constant.WHATSAPP_NUM));
                        session.setData(Constant.YOUTUBE_LINK,jsonArray.getJSONObject(0).getString(Constant.YOUTUBE_LINK));
                        session.setData(Constant.UPI,jsonArray.getJSONObject(0).getString(Constant.UPI));
                        session.setData(Constant.MIN_WITHDRAWAL,jsonArray.getJSONObject(0).getString(Constant.MIN_WITHDRAWAL));
                        session.setData(Constant.MAX_WITHDRAWAL,jsonArray.getJSONObject(0).getString(Constant.MAX_WITHDRAWAL));
                        session.setData(Constant.MIN_DEPOSIT,jsonArray.getJSONObject(0).getString(Constant.MIN_DEPOSIT));

                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, String.valueOf(response) + String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
            //pass url
        }, activity, Constant.SETTINGS_URL, params, true);
    }
}