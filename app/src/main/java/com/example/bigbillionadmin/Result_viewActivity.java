package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Result_viewActivity extends AppCompatActivity {

    TextView add_result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        add_result_tv = findViewById(R.id.add_result_tv);

        add_result_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Result_viewActivity.this,Declare_resultActivity.class);
                startActivity(intent);
            }
        });
    }
}