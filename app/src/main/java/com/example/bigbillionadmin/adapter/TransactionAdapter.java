package com.example.bigbillionadmin.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbillionadmin.helper.Session;
import com.example.bigbillionadmin.model.Transaction;
import com.example.bigbillionadmin.R;

import java.util.ArrayList;


public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    Session session;
    final ArrayList<Transaction> transactions;

    public TransactionAdapter(Activity activity, ArrayList<Transaction> transactions) {
        this.activity = activity;
        this.transactions = transactions;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.transaction_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        session = new Session(activity);
        final ItemHolder holder = (ItemHolder) holderParent;
        final Transaction transaction = transactions.get(position);
        if (transaction.getType().equals("game")){
            if (transaction.getGame_type().equals("jodi")){
                holder.transimg.setImageResource(R.drawable.dice);

            }
            else if (transaction.getGame_type().equals("andar") || transaction.getGame_type().equals("bahar")){
                holder.transimg.setImageResource(R.drawable.casino);

            }
            else if (transaction.getGame_type().equals("quick_cross")){
                holder.transimg.setImageResource(R.drawable.hockeysticks);

            }
            else if (transaction.getGame_type().equals("odd_even")){
                holder.transimg.setImageResource(R.drawable.balanced);

            }
            holder.tvtitle.setText("Deducted On "+transaction.getGame_type() +" \n"+transaction.getGame_name());
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("withdrawal")){
            holder.transimg.setImageResource(R.drawable.withdraw);
            holder.tvtitle.setText("Withdrawal");
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("sharepoints")){
            holder.transimg.setImageResource(R.drawable.sharepoints);
            if (transaction.getShare_from() != null){
                holder.tvtitle.setText("Share Points received from \n"+transaction.getShare_from());

            }
            else {
                holder.tvtitle.setText("Share Points sent to \n"+transaction.getShare_to());


            }

            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("deposit")){
            holder.transimg.setImageResource(R.drawable.deposit);
            holder.tvtitle.setText("Deposit Points");
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("delete_bids")){
            holder.transimg.setImageResource(R.drawable.coins);
            holder.tvtitle.setText("Credited  for Deleting Bids In "+transaction.getGame_name());
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("credit")){
            holder.transimg.setImageResource(R.drawable.coins);
            holder.tvtitle.setText("Credited - "+transaction.getReason());
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());
        }
        else if (transaction.getType().equals("debit")){
            holder.transimg.setImageResource(R.drawable.coins);
            holder.tvtitle.setText("Debited - "+transaction.getReason());
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("wrongresult")){
            holder.transimg.setImageResource(R.drawable.cross);
            holder.tvtitle.setText("points deducted due to wrong result of "+transaction.getGame_name()+" for "+transaction.getGame_type());
            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }
        else if (transaction.getType().equals("correctresult")){
            holder.transimg.setImageResource(R.drawable.trophy);
            holder.tvtitle.setText("credited for winning of "+transaction.getGame_name()+" for "+transaction.getGame_type());

            if (transaction.getReason()!=null){
                if (transaction.getReason().equals("andar")){
                    holder.tvtitle.setText("credited for winning of "+transaction.getGame_name()+" for andar");

                }
                else if (transaction.getReason().equals("bahar")){
                    holder.tvtitle.setText("credited for winning of "+transaction.getGame_name()+" for bahar");

                }

            }

            holder.tvTime.setText(transaction.getDate_created());
            holder.tvPoints.setText(transaction.getPoints());
            holder.tvBalance.setText("Balanace "+ transaction.getBalance());

        }

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvtitle,tvTime,tvPoints,tvBalance;
        ImageView transimg;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvBalance = itemView.findViewById(R.id.tvBalance);
            transimg = itemView.findViewById(R.id.transimg);


        }
    }
}

