package com.example.bigbillionadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.AllBidsAdapter;
import com.example.bigbillionadmin.adapter.BiddingUsersAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Functions;
import com.example.bigbillionadmin.model.BIDS;
import com.example.bigbillionadmin.model.Game;
import com.example.bigbillionadmin.model.HarufBids;
import com.example.bigbillionadmin.model.Users;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class All_biddingsActivity extends AppCompatActivity {
    String format = "%1$02d";
    DatePicker picker;
    String date;
    String spinGameName;
    Spinner spinGame;
    Activity activity;
    Button btnResult;
    String day,month,year;
    ArrayList<BIDS> transactions = new ArrayList<>();
    AllBidsAdapter allBidsAdapter;
    RecyclerView recyclerView;
    TextView tvJodi,tvQuickcross,tvOddEven,tvHaruf,tvUsers,tvAmount;
    ArrayList<HarufBids> harufBids = new ArrayList<>();
    ArrayList<HarufBids> andarBids = new ArrayList<>();
    ArrayList<HarufBids> baharBids = new ArrayList<>();
    ArrayList<String> number = new ArrayList<>();
    ArrayList<String> andarnum = new ArrayList<>();
    ArrayList<String> baharnum = new ArrayList<>();
    ArrayList<BIDS> bids2 = new ArrayList<>();
    String innerresponse = "";
    LinearLayout bidsl1;
    boolean allbid = false;
    boolean harufbid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_biddings);
        activity = All_biddingsActivity.this;
        bidsl1 = findViewById(R.id.bidsl1);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnResult=findViewById(R.id.btnResult);
        recyclerView=findViewById(R.id.recyclerView);
        spinGame=findViewById(R.id.spinGame);
        tvJodi=findViewById(R.id.tvJodi);
        tvUsers=findViewById(R.id.tvUsers);
        tvAmount=findViewById(R.id.tvAmount);
        tvQuickcross=findViewById(R.id.tvQuickcross);
        tvOddEven=findViewById(R.id.tvOddEven);
        tvHaruf=findViewById(R.id.tvHaruf);
        Functions.setData(activity,spinGame);

        innerresponse = "{\n" +
                "    \"success\": true,\n" +
                "    \"message\": \"Bids listed Successfully\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"260\",\n" +
                "            \"user_id\": \"7\",\n" +
                "            \"game_name\": \"FD\",\n" +
                "            \"game_type\": \"jodi\",\n" +
                "            \"game_method\": \"cross\",\n" +
                "            \"number\": \"0\",\n" +
                "            \"points\": \"125\",\n" +
                "            \"game_date\": \"2022-05-20\",\n" +
                "            \"last_updated\": \"2022-05-20 18:31:35\",\n" +
                "            \"date_created\": \"2022-05-20 18:28:57\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"261\",\n" +
                "            \"user_id\": \"7\",\n" +
                "            \"game_name\": \"FD\",\n" +
                "            \"game_type\": \"jodi\",\n" +
                "            \"game_method\": \"cross\",\n" +
                "            \"number\": \"5\",\n" +
                "            \"points\": \"50\",\n" +
                "            \"game_date\": \"2022-05-20\",\n" +
                "            \"last_updated\": \"2022-05-26 13:04:06\",\n" +
                "            \"date_created\": \"2022-05-20 18:31:35\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";


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

                    //winnerList(spinGameName,date);
                    harufbidsList();



                }

            }
        });



    }

    private void harufbidsList()
    {
        bids2.clear();
        number.clear();
        andarnum.clear();
        baharnum.clear();
        andarBids.clear();
        baharBids.clear();
        harufBids.clear();
        tvJodi.setText("Jodi \n0");
        tvQuickcross.setText("Quick Cross \n0");
        tvOddEven.setText("Odd Even \n0");
        tvHaruf.setText("Haruf \n0");
        tvUsers.setText("Total Users = 0");
        tvAmount.setText("Total Amount = 0");
        Map<String, String> params2 = new HashMap<>();
        params2.put(Constant.GAME_NAME,spinGameName);
        params2.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvJodi.setText("Jodi \n"+jsonObject.getString(Constant.JODI));
                        tvQuickcross.setText("Quick Cross \n"+jsonObject.getString(Constant.QUICKCROSS));
                        tvOddEven.setText("Odd Even \n"+jsonObject.getString(Constant.ODDEVEN));
                        tvHaruf.setText("Haruf \n"+jsonObject.getString(Constant.HARUF));
                        tvUsers.setText("Total Users = "+jsonObject.getString(Constant.TOTALUSERS));
                        tvAmount.setText("Total Amount = "+jsonObject.getString(Constant.TOTAL_POINTS));

                    }
                    else {

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(BidsHistoryActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.COUNTBIDSLIST_URL, params2, false);

        Map<String, String> params = new HashMap<>();
        params.put(Constant.GAME_NAME,spinGameName);
        params.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        harufbid = true;
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                HarufBids group = g.fromJson(jsonObject1.toString(), HarufBids.class);
                                if (group.getGame_type().equals("andar")){
                                    andarnum.add(group.getNumber());
                                    andarBids.add(group);
                                }else {
                                    baharnum.add(group.getNumber());
                                    baharBids.add(group);

                                }

                                harufBids.add(group);
                            } else {
                                break;
                            }
                        }
                        bidsList();
                    }
                    else {
                        bidsList();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(BidsHistoryActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.ALLHARUFBIDSLIST_URL, params, true);

        bidsList();
    }

    private void bidsList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.GAME_NAME,spinGameName);
        params.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("ALL_BIDS",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                    JSONObject innerjsonObject = new JSONObject(innerresponse);
                    JSONArray innerjsonArray = innerjsonObject.getJSONArray(Constant.DATA);
                    Gson g = new Gson();
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        allbid = true;
                        bidsl1.setVisibility(View.VISIBLE);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {

                                BIDS group = g.fromJson(jsonObject1.toString(), BIDS.class);
                                number.add(group.getNumber());
                                bids2.add(group);

                            } else {
                                break;
                            }
                        }


                    }
                    ArrayList<BIDS> bids = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {
                        JSONObject jsonObject1 = innerjsonArray.getJSONObject(0);

                        if (jsonObject1 != null) {
                            BIDS group = g.fromJson(jsonObject1.toString(), BIDS.class);
                            group.setPoints("0");
                            group.setNumber(""+i);
                            bids.add(group);

                        } else {
                            break;
                        }
                    }
                    if (harufbid || allbid){
                        allBidsAdapter = new AllBidsAdapter(activity, bids,bids2,number,andarnum,baharnum,andarBids,baharBids);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(allBidsAdapter);

                        bidsl1.setVisibility(View.VISIBLE);
                    }
                    else {
                        bidsl1.setVisibility(View.GONE);
                        Toast.makeText(activity, "No Bids Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.ALL_BIDS_URL, params, true);

    }

}