package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.ResultsAdapter;
import com.example.bigbillionadmin.adapter.UsersAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Functions;
import com.example.bigbillionadmin.model.Game;
import com.example.bigbillionadmin.model.Results;
import com.example.bigbillionadmin.model.Users;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Declare_resultActivity extends AppCompatActivity {

    String format = "%1$02d";
    DatePicker picker;
    String date;
    String spinGameName;
    Spinner spinGame;
    Activity activity;
    Button btnUpdate,btnResult;
    String day,month,year;
    RecyclerView recyclerView;
    ResultsAdapter resultsAdapter;
    ArrayList<Results> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_result);
        activity = Declare_resultActivity.this;
        picker=(DatePicker)findViewById(R.id.datePicker1);
        recyclerView = findViewById(R.id.recyclerView);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnResult=findViewById(R.id.btnResult);
        spinGame=findViewById(R.id.spinGame);
        Functions.setData(activity,spinGame);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        resultsAdapter = new ResultsAdapter(activity, results);
        resultsList();

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

                    showResult(spinGameName,date);



                }

            }
        });

    }

    public void showResult(String spinGameName, String date)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.DATE, date);
        params.put(Constant.GAME_NAME, spinGameName);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        deleteResultDialogue(jsonObject.getString(Constant.RESULT),jsonObject.getString(Constant.ID));
                    } else {
                        addResultDialogue();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, String.valueOf(response) + String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
            //pass url
        }, activity, Constant.RESULT_LIST_URL, params, true);

    }

    private void resultsList()
    {
        results.clear();
        resultsAdapter.notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
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
                                Results group = g.fromJson(jsonObject1.toString(), Results.class);
                                results.add(group);
                            } else {
                                break;
                            }
                        }

                        recyclerView.setAdapter(resultsAdapter);
                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.ALL_RESULT_URL, params, true);
    }

    private void deleteResultDialogue(String result, String result_id)
    {
        final EditText etResult = new EditText(activity);
        etResult.setEnabled(false);
        etResult.setText(result);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Do you want to Delete Result ?")
                .setMessage(spinGame.getSelectedItem().toString().trim() +" - " + date)
                .setCancelable(false)
                .setView(etResult)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map<String, String> params = new HashMap<>();
                        params.put(Constant.ID, result_id);
                        ApiConfig.RequestToVolley((result, response) -> {
                            Log.d("DELETE_RESULT",response);
                            if (result) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                                        dialog.cancel();
                                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(activity,HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Toast.makeText(activity, String.valueOf(response) + String.valueOf(result), Toast.LENGTH_SHORT).show();

                            }
                            //pass url
                        }, activity, Constant.DELETE_RESULT_URL, params, true);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void addResultDialogue()
    {
        final EditText etResult = new EditText(activity);
        etResult.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enter Result to Announce")
                .setMessage(spinGame.getSelectedItem().toString().trim() +" - " + date)
                .setCancelable(false)
                .setView(etResult)
                .setPositiveButton("Annonce", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (etResult.getText().toString().trim().equals("")){
                            etResult.setError("Enter Result");

                        }
                        else {
                            addResult(etResult.getText().toString().trim(),dialog);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void addResult(String result_num, DialogInterface dialog)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.DATE, date);
        params.put(Constant.RESULT, result_num);
        params.put(Constant.DAY, day);
        params.put(Constant.MONTH, month);
        params.put(Constant.YEAR, year);
        params.put(Constant.GAME_NAME, spinGameName);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("ANNOUNCE_RESULT",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        dialog.cancel();
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, String.valueOf(response) + String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
            //pass url
        }, activity, Constant.ADD_RESULT_URL, params, true);

    }


}