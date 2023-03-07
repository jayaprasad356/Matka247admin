package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.DepositPointsAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManageWalletActivity extends AppCompatActivity {
    RadioButton rCredit,rDebit;
    Button btnUpdate;
    EditText etReason,etPoints;
    Activity activity;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_wallet);
        user_id = getIntent().getStringExtra("id");
        activity = ManageWalletActivity.this;
        rCredit = findViewById(R.id.rCredit);
        rDebit = findViewById(R.id.rDebit);
        etReason = findViewById(R.id.etReason);
        etPoints = findViewById(R.id.etPoints);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPoints.getText().toString().trim().equals("") || etPoints.getText().toString().trim().equals("0")){
                    etPoints.setError("Enter Points");
                    etPoints.requestFocus();
                }
                else if (etReason.getText().toString().trim().equals("")){
                    etReason.setError("Enter Reason");
                    etReason.requestFocus();
                }
                else{
                    updateWallet();
                }

            }
        });
    }

    private void updateWallet()
    {
        String Type = "";
        if (rCredit.isChecked()){
            Type = "credit";
        }
        else {
            Type = "debit";
        }
        Map<String, String> params = new HashMap<>();
        params.put(Constant.TYPE, Type);
        params.put(Constant.USER_ID, user_id);
        params.put(Constant.POINTS, etPoints.getText().toString().trim());
        params.put(Constant.REASON, etReason.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
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
        }, activity, Constant.MANAGE_WALLET_URL, params, true);

    }
}