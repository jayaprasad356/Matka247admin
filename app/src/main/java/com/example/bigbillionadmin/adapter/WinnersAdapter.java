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

import com.example.bigbillionadmin.ManageWalletActivity;
import com.example.bigbillionadmin.R;
import com.example.bigbillionadmin.TransactionListsActivity;
import com.example.bigbillionadmin.helper.Constant;
import com.example.bigbillionadmin.model.Users;
import com.example.bigbillionadmin.model.Winners;

import java.util.ArrayList;


public class WinnersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Winners> winners;

    public WinnersAdapter(Activity activity, ArrayList<Winners> winners) {
        this.activity = activity;
        this.winners = winners;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.winner_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Winners winner = winners.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copy to Clipboard", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", winner.getMobile());
                clipboard.setPrimaryClip(clip);
                return false;
            }
        });
        holder.tvMobile.setText(winner.getMobile());
        holder.tvName.setText(winner.getName());
        holder.tvPoints.setText(winner.getPoints());
        holder.tvBid.setText(winner.getResult());
        holder.tvDate.setText(winner.getDate());
        holder.tvGame.setText(winner.getGame_name());

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return winners.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvMobile,tvPoints,tvBid,tvDate,tvGame;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvBid = itemView.findViewById(R.id.tvBid);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvGame = itemView.findViewById(R.id.tvGame);


        }
    }
}

