package com.example.bigbillionadmin.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bigbillionadmin.R;
import com.example.bigbillionadmin.helper.Session;
import com.example.bigbillionadmin.model.BIDS;
import com.example.bigbillionadmin.model.HarufBids;

import java.util.ArrayList;

public class BidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    Button btnSubmit;
    Session session;
    final ArrayList<BIDS> bids;
    final ArrayList<BIDS> bids2;
    final ArrayList<String> number;
    final ArrayList<String> andarnum;
    final ArrayList<String> baharnum;
    final ArrayList<HarufBids> andarBids;
    final ArrayList<HarufBids> baharBids;


    public BidAdapter(Activity activity, ArrayList<BIDS> bids, ArrayList<BIDS> bids2, ArrayList<String> number, ArrayList<String> andarnum,ArrayList<String> baharnum, ArrayList<HarufBids> andarBids, ArrayList<HarufBids> baharBids) {
        this.activity = activity;
        this.bids = bids;
        this.bids2 = bids2;
        this.number = number;
        this.andarnum = andarnum;
        this.baharnum = baharnum;
        this.andarBids = andarBids;
        this.baharBids = baharBids;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.bids_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        session = new Session(activity);
        final ItemHolder holder = (ItemHolder) holderParent;
        final BIDS bids1 = bids.get(position);

        int numpos = -1;
        numpos = number.indexOf(""+bids1.getNumber());
        if (numpos == -1) {
            holder.tvPoints.setText("0");
        } else {
            final BIDS bidsva = bids2.get(numpos);
            holder.tvPoints.setText(bidsva.getPoints());
        }

        holder.tvNumber.setText(bids1.getNumber());
        if (position < 10){
            int andarpos = -1;
            andarpos = andarnum.indexOf(""+bids1.getNumber());
            if (andarpos == -1) {
                holder.tvAndar.setText("0");
            } else {
                final HarufBids andarBids1 = andarBids.get(andarpos);
                holder.tvAndar.setText(andarBids1.getPoints());
            }
            int baharpos = -1;
            baharpos = baharnum.indexOf(""+bids1.getNumber());
            if (baharpos == -1) {
                holder.tvBahar.setText("0");
            } else {
                final HarufBids baharBids1 = baharBids.get(baharpos);
                holder.tvBahar.setText(baharBids1.getPoints());
            }

        }




    }

    private int getNumberPos(String number) {
        return number.indexOf(number);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return bids.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvNumber,tvPoints,tvAndar,tvBahar;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvAndar = itemView.findViewById(R.id.tvAndar);
            tvBahar = itemView.findViewById(R.id.tvBahar);


        }
    }
}

