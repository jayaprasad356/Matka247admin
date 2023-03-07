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

import com.example.bigbillionadmin.Declare_resultActivity;
import com.example.bigbillionadmin.ManageWalletActivity;
import com.example.bigbillionadmin.R;
import com.example.bigbillionadmin.TransactionListsActivity;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.Results;
import com.example.bigbillionadmin.model.Users;

import java.util.ArrayList;


public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Results> results;

    public ResultsAdapter(Activity activity, ArrayList<Results> results) {
        this.activity = activity;
        this.results = results;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.result_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Results result = results.get(position);
        holder.tvDate.setText(result.getDate());
        holder.tvGame.setText(result.getGame_name());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Declare_resultActivity)activity).showResult(result.getGame_name(),result.getDate());
            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvGame;
        Button btnDelete;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvGame = itemView.findViewById(R.id.tvGame);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

