package com.example.bigbillionadmin.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbillionadmin.R;
import com.example.bigbillionadmin.Update_depositActivity;
import com.example.bigbillionadmin.model.DepositPoints;
import com.example.bigbillionadmin.model.SharePoints;

import java.util.ArrayList;


public class SharePointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<SharePoints> sharePoints;

    public SharePointsAdapter(Activity activity, ArrayList<SharePoints> sharePoints) {
        this.activity = activity;
        this.sharePoints = sharePoints;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.share_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final SharePoints sharePoints1 = sharePoints.get(position);
        holder.mobile.setText(sharePoints1.getMobile());
        holder.shared_mobile.setText(sharePoints1.getShared_mobile());
        holder.points.setText(sharePoints1.getPoints());
        holder.tvDateTime.setText(sharePoints1.getDate_created());


    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return sharePoints.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView mobile,shared_mobile,points,tvDateTime;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mobile = itemView.findViewById(R.id.mobile);
            shared_mobile = itemView.findViewById(R.id.shared_mobile);
            points = itemView.findViewById(R.id.points);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);


        }
    }
}

