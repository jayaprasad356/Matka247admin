package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update_withdrawalActivity extends AppCompatActivity {
    Button btnUpdate;
    RadioButton rPaid,rCancel;
    String Id;
    Activity activity;
    TextView tvAccountNumber,tvIFSC,tvHolderName,tvPaytm,tvPhonepe;
    String tvAccountNumber_,tvIFSC_,tvHolderName_,tvPaytm_,tvPhonepe_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_withdrawal);
        activity = Update_withdrawalActivity.this;
        Id = getIntent().getStringExtra("id");
        tvAccountNumber_ = getIntent().getStringExtra(Constant.ACCOUNT_NO);
        tvIFSC_ = getIntent().getStringExtra(Constant.IFSC_CODE);
        tvHolderName_ = getIntent().getStringExtra(Constant.HOLDER_NAME);
        tvPaytm_ = getIntent().getStringExtra(Constant.PAYTM);
        tvPhonepe_ = getIntent().getStringExtra(Constant.PHONEPE);
        btnUpdate = findViewById(R.id.btnUpdate);
        rPaid = findViewById(R.id.rPaid);
        rCancel = findViewById(R.id.rCancel);
        tvAccountNumber = findViewById(R.id.tvAccountNumber);
        tvIFSC = findViewById(R.id.tvIFSC);
        tvHolderName = findViewById(R.id.tvHolderName);
        tvPaytm = findViewById(R.id.tvPaytm);
        tvPhonepe = findViewById(R.id.tvPhonepe);
        tvAccountNumber.setText(tvAccountNumber_);
        tvIFSC.setText(tvIFSC_);
        tvHolderName.setText(tvHolderName_);
        tvPaytm.setText(tvPaytm_);
        tvPhonepe.setText(tvPhonepe_);

        tvAccountNumber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copy to Clipboard", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvAccountNumber_);
                clipboard.setPrimaryClip(clip);
                return false;
            }
        });
        tvIFSC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copy to Clipboard", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvIFSC_);
                clipboard.setPrimaryClip(clip);
                return false;
            }
        });
        tvHolderName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copy to Clipboard", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvHolderName_);
                clipboard.setPrimaryClip(clip);
                return false;
            }
        });
        tvPaytm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copy to Clipboard", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvPaytm_);
                clipboard.setPrimaryClip(clip);
                return false;
            }
        });
        tvPhonepe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copy to Clipboard", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tvPhonepe_);
                clipboard.setPrimaryClip(clip);
                return false;
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rPaid.isChecked() || rCancel.isChecked()){
                    updateStatus();

                }
                else {
                    Toast.makeText(Update_withdrawalActivity.this, "Please Select Option", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    private void updateStatus()
    {
        String Status = "0";
        if (rPaid.isChecked()){
            Status = "1";

        }
        else {
            Status = "2";

        }
        Map<String, String> params = new HashMap<>();
        params.put(Constant.STATUS, Status);
        params.put(Constant.WITHDRAWAL_ID, Id);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(this, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, WithdrawalActivity.class);
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
        }, activity, Constant.UPDATE_WITHDRAWALS_URL, params, true);

    }
}