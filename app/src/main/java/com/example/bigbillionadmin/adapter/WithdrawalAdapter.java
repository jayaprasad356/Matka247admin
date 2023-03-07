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
import com.example.bigbillionadmin.Update_withdrawalActivity;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.DepositPoints;
import com.example.bigbillionadmin.model.Withdrawal;

import java.util.ArrayList;


public class WithdrawalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    Button btnSubmit;
    final ArrayList<Withdrawal> withdrawals;

    public WithdrawalAdapter(Activity activity, ArrayList<Withdrawal> withdrawals) {
        this.activity = activity;
        this.withdrawals = withdrawals;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.withdrawal_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Withdrawal withdrawal = withdrawals.get(position);
        holder.tvMobile.setText(withdrawal.getMobile());
        holder.tvName.setText(withdrawal.getName());
        holder.tvPoints.setText(withdrawal.getPoints());
        holder.tvDateTime.setText(withdrawal.getDate_created());
        if (withdrawal.getStatus().equals("0")){
            holder.tvStatus.setText("Pending");
        }
        else if (withdrawal.getStatus().equals("1")){
            holder.tvStatus.setText("Completed");

        }
        else {
            holder.tvStatus.setText("Cancelled");

        }
        if (withdrawal.getStatus().equals("0")){
            holder.btnUpdate.setVisibility(View.VISIBLE);
        }
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Update_withdrawalActivity.class);
                intent.putExtra("id",withdrawal.getId());
                intent.putExtra(Constant.ACCOUNT_NO,withdrawal.getAccount_number());
                intent.putExtra(Constant.IFSC_CODE,withdrawal.getIfsc_code());
                intent.putExtra(Constant.HOLDER_NAME,withdrawal.getHolder_name());
                intent.putExtra(Constant.PAYTM,withdrawal.getPaytm());
                intent.putExtra(Constant.PHONEPE,withdrawal.getPhonepe());
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
        return withdrawals.size();
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

