package com.example.cvproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryRecyclerViewCustomAdapter extends RecyclerView.Adapter<HistoryRecyclerViewHolder> {
    ArrayList<Reset> resets;
    private LayoutInflater mInflater;
    Context context;
    public HistoryRecyclerViewCustomAdapter(Context context, ArrayList<Reset> resets) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.resets = resets;
    }

    @NonNull
    @Override
    public HistoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resetcard, parent, false);
        HistoryRecyclerViewHolder rcv = new HistoryRecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerViewHolder holder, final int position) {
        holder.locationAddress.setText(resets.get(position).getLocationAddress());
        holder.phone.setText(resets.get(position).getPhone());
        holder.subtotalMoney.setText(resets.get(position).getSubtotalMoney());
        holder.barCode.setText(resets.get(position).getBarCode());
    }

    @Override
    public int getItemCount() {
        return this.resets.size();
    }
}
