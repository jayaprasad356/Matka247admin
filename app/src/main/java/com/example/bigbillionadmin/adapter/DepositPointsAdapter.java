package com.example.bigbillionadmin.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbillionadmin.Deposit_requestActivity;
import com.example.bigbillionadmin.R;
import com.example.bigbillionadmin.Update_depositActivity;
import com.example.bigbillionadmin.model.DepositPoints;

import java.util.ArrayList;


public class DepositPointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    Button btnSubmit;
    final ArrayList<DepositPoints> depositPoints;

    public DepositPointsAdapter(Activity activity, ArrayList<DepositPoints> depositPoints) {
        this.activity = activity;
        this.depositPoints = depositPoints;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deposit_points_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final DepositPoints depositPoints1 = depositPoints.get(position);
        holder.tvMobile.setText(depositPoints1.getMobile());
        holder.tvName.setText(depositPoints1.getName());
        holder.tvPoints.setText(depositPoints1.getPoints());
        holder.tvDateTime.setText(depositPoints1.getDate_created());
        if (depositPoints1.getStatus().equals("0")){
            holder.tvStatus.setText("Pending");
        }
        else if (depositPoints1.getStatus().equals("1")){
            holder.tvStatus.setText("Completed");

        }
        else {
            holder.tvStatus.setText("Cancelled");

        }
//        if (depositPoints1.getStatus().equals("0")){
//            holder.btnUpdate.setVisibility(View.VISIBLE);
//        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Mobile Number", depositPoints1.getMobile());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, "Mobile Number Copied to Clipboard", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Update_depositActivity.class);
                intent.putExtra("id",depositPoints1.getId());
                intent.putExtra("user_id",depositPoints1.getUser_id());
                activity.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return depositPoints.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvMobile,tvPoints,tvStatus,tvDateTime;
        Button btnUpdate;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvPoints = itemView.findViewById(R.id.points);
            tvMobile = itemView.findViewById(R.id.mobile);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);


        }
    }
}

