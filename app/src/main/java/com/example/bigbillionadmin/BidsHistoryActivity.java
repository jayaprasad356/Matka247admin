package com.example.bigbillionadmin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bigbillionadmin.adapter.BidAdapter;
import com.example.bigbillionadmin.adapter.HarufBidAdapter;
import com.example.bigbillionadmin.helper.ApiConfig;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.helper.Functions;
import com.example.bigbillionadmin.helper.Session;
import com.example.bigbillionadmin.model.BIDS;
import com.example.bigbillionadmin.model.Game;
import com.example.bigbillionadmin.model.HarufBids;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BidsHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BidAdapter bidAdapter;
    Activity activity;
    Button btnSubmit;
    Spinner spinGame,spinDay;
    Session session;
    String date;
    LinearLayout bidsl1;
    ArrayList<BIDS> bids = new ArrayList<>();
    ArrayList<HarufBids> harufBids = new ArrayList<>();
    ArrayList<HarufBids> andarBids = new ArrayList<>();
    ArrayList<HarufBids> baharBids = new ArrayList<>();
    String spinGameName;
    Button btnDelete;
    ArrayList<String> number = new ArrayList<>();
    ArrayList<String> andarnum = new ArrayList<>();
    ArrayList<String> baharnum = new ArrayList<>();
    ArrayList<BIDS> bids2 = new ArrayList<>();
    String innerresponse = "";
    boolean harufbid = false;
    boolean allbid = false;
    boolean deletestatus = false;
    Date gameTime_date,currentTime_date;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_history);
        activity = BidsHistoryActivity.this;
        Id = getIntent().getStringExtra("id");
        session = new Session(activity);

        bidsl1 = findViewById(R.id.bidsl1);
        recyclerView = findViewById(R.id.recyclerView);
        btnSubmit = findViewById(R.id.btnSubmit);
        spinGame = findViewById(R.id.spinGame);
        spinDay = findViewById(R.id.spinDay);
        btnDelete = findViewById(R.id.btnDelete);
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
                //Toast.makeText(activity, ""+game.getGamename(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (spinGame.getSelectedItemPosition() == 0){
                    Toast.makeText(activity, "Please,Select Game", Toast.LENGTH_SHORT).show();
                }
                else if (spinDay.getSelectedItemPosition() == 0  || spinGame.getSelectedItemPosition() == 4){
                    Toast.makeText(activity, "Please,Select Day", Toast.LENGTH_SHORT).show();
                }
                else {

                    harufbid = false;
                    allbid = false;
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                    if (spinDay.getSelectedItemPosition() == 1){

                        date = simpleDateFormat.format(getMeYesterday());


                    }
                    else if (spinDay.getSelectedItemPosition() == 2){
                        date = simpleDateFormat.format(new Date());


                    }
                    else if (spinDay.getSelectedItemPosition() == 3){
                        date = simpleDateFormat.format(getMeTomorrow());


                    }
                    bidsl1.setVisibility(View.GONE);
                    try {
                        deletestatus = isDeletable();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    harufbidsList();
                    //bidsList();

                }


            }
        });



    }
    private boolean isDeletable() throws ParseException {
        String[] separated = spinGame.getSelectedItem().toString().split("-");
        String gameTimestr = separated[1];
        gameTimestr = date +" "+gameTimestr;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Calendar currnetDateTime = Calendar.getInstance();
        try {
            gameTime_date = df.parse(gameTimestr);
            currentTime_date = df.parse(df.format(currnetDateTime.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = gameTime_date.getTime() - currentTime_date.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        if (minutes <= 2){
            return true;
            //deletestatus = true;

        }
        else {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat cdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = cdf.format(c);
            if (date.equals(formattedDate))
            {
                return false;

            }else {
                return true;
            }

            //deletestatus = false;
        }

    }

    private void harufbidsList()
    {
        bids.clear();
        bids2.clear();
        number.clear();
        andarnum.clear();
        baharnum.clear();
        andarBids.clear();
        baharBids.clear();

        harufBids.clear();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,Id);
        params.put(Constant.GAME_NAME,spinGameName);
        params.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        if (deletestatus){
                            btnDelete.setVisibility(View.GONE);
                        }
                        else {
                            btnDelete.setVisibility(View.VISIBLE);

                        }
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
        }, activity, Constant.HARUFBIDSLIST_URL, params, true);

    }

    private Date getMeTomorrow(){
        return new Date(System.currentTimeMillis()+24*60*60*1000);
    }
    private Date getMeYesterday(){
        return new Date(System.currentTimeMillis()-24*60*60*1000);
    }
    private void bidsList()
    {

        bids.clear();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,Id);
        params.put(Constant.GAME_NAME,spinGameName);
        params.put(Constant.DATE,date);
        ApiConfig.RequestToVolley((result, response) -> {

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
                        if (deletestatus){
                            btnDelete.setVisibility(View.GONE);
                        }
                        else {
                            btnDelete.setVisibility(View.VISIBLE);

                        }

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
                        if (deletestatus){
                            btnDelete.setVisibility(View.GONE);
                        }
                        else {
                            btnDelete.setVisibility(View.VISIBLE);

                        }
                        bidAdapter = new BidAdapter(activity, bids,bids2,number,andarnum,baharnum,andarBids,baharBids);
                        recyclerView.setAdapter(bidAdapter);

                        bidsl1.setVisibility(View.VISIBLE);
                    }
                    else {
                        bidsl1.setVisibility(View.GONE);
                        btnDelete.setVisibility(View.GONE);
                        Toast.makeText(activity, "No Bids Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.BIDSLIST_URL, params, true);
    }


}