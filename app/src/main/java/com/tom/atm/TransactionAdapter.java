package com.tom.atm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tom on 2016/4/12.
 */
public class TransactionAdapter
        extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    private List<Transaction> trans;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView dateTextView;
        private final TextView amountTextView;
        private final TextView typeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.col_date);
            amountTextView =
                    (TextView) itemView.findViewById(R.id.col_amount);
            typeTextView = (TextView) itemView.findViewById(R.id.col_type);
        }
    }

    public TransactionAdapter(List<Transaction> trans) {
        this.trans = trans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_trans, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("TRANS", position+"");
        Transaction tran = trans.get(position);
        holder.dateTextView.setText(tran.getDate());
        holder.amountTextView.setText(tran.getAmount()+"");
        holder.typeTextView.setText(tran.getType()+"");
    }

    @Override
    public int getItemCount() {
        return trans.size();
    }
}
