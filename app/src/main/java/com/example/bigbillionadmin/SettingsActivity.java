package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    EditText whatsapp,youtube,upi,newsinfo,etMinWithdrawal,etMaxWithdrawal,etMinDeposite;
    Button btnUpdate;
    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        activity = SettingsActivity.this;
        session = new Session(activity);
        whatsapp = findViewById(R.id.whatsapp);
        youtube = findViewById(R.id.youtube);
        etMinDeposite = findViewById(R.id.etMinDeposite);
        etMinWithdrawal = findViewById(R.id.etMinWithdrawal);
        etMaxWithdrawal = findViewById(R.id.etMaxWithdrawal);
        upi = findViewById(R.id.upi);
        newsinfo = findViewById(R.id.newsinfo);
        btnUpdate = findViewById(R.id.btnUpdate);
        whatsapp.setText(session.getData(Constant.WHATSAPP_NUM));
        youtube.setText(session.getData(Constant.YOUTUBE_LINK));
        upi.setText(session.getData(Constant.UPI));
        newsinfo.setText(session.getData(Constant.NEWSINFO));
        etMinWithdrawal.setText(session.getData(Constant.MIN_WITHDRAWAL));
        etMaxWithdrawal.setText(session.getData(Constant.MAX_WITHDRAWAL));
        etMinDeposite.setText(session.getData(Constant.MIN_DEPOSIT));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings();
            }
        });

    }
    private void updateSettings()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.WHATSAPP_NUM, whatsapp.getText().toString().trim());
        params.put(Constant.YOUTUBE_LINK, youtube.getText().toString().trim());
        params.put(Constant.UPI, upi.getText().toString().trim());
        params.put(Constant.NEWSINFO, newsinfo.getText().toString().trim());
        params.put(Constant.MIN_DEPOSIT, etMinDeposite.getText().toString().trim());
        params.put(Constant.MIN_WITHDRAWAL, etMinWithdrawal.getText().toString().trim());
        params.put(Constant.MAX_WITHDRAWAL, etMaxWithdrawal.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.WHATSAPP_NUM,jsonArray.getJSONObject(0).getString(Constant.WHATSAPP_NUM));
                        session.setData(Constant.YOUTUBE_LINK,jsonArray.getJSONObject(0).getString(Constant.YOUTUBE_LINK));
                        session.setData(Constant.UPI,jsonArray.getJSONObject(0).getString(Constant.UPI));
                        session.setData(Constant.NEWSINFO,jsonArray.getJSONObject(0).getString(Constant.NEWSINFO));
                        session.setData(Constant.MIN_WITHDRAWAL,jsonArray.getJSONObject(0).getString(Constant.MIN_WITHDRAWAL));
                        session.setData(Constant.MAX_WITHDRAWAL,jsonArray.getJSONObject(0).getString(Constant.MAX_WITHDRAWAL));
                        session.setData(Constant.MIN_DEPOSIT,jsonArray.getJSONObject(0).getString(Constant.MIN_DEPOSIT));
                        Toast.makeText(this, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, HomeActivity.class);
                        startActivity(intent);
                        finish();
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
        }, activity, Constant.UPDATE_SETTINGS_URL, params, true);

    }
}