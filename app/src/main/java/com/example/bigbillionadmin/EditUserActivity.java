package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Session;
import com.example.bigbillionadmin.model.Users;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    EditText Name;
    String getname,getid;
    Button btnUpdate;
    Activity activity;
    Session session;

    ImageButton backbtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        activity = EditUserActivity.this;
        session = new Session(activity);


        getid = getIntent().getStringExtra("id");
        getname = getIntent().getStringExtra("name");

        Name = findViewById(R.id.Name);
        btnUpdate = findViewById(R.id.btnUpdate);
        backbtn = findViewById(R.id.backbtn);

        backbtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserActivity.this, UsersActivity.class);
                startActivity(intent);

            }
        });

        Name.setText(getname);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, String> params = new HashMap<>();
                params.put(Constant.USER_ID, getid);
                params.put(Constant.NAME, Name.getText().toString());
                ApiConfig.RequestToVolley((result, response) -> {
                    if (result) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean(Constant.SUCCESS)) {
                                Toast.makeText(activity, ""+jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, activity, Constant.UPDATE_USERS_ADMIN_URL, params, true);

            }
        });

    }
}