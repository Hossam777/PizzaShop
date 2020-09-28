package com.example.cvproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryRecyclerViewCustomAdapter extends RecyclerView.Adapter<HistoryRecyclerViewHolder> {
    ArrayList<Reset> resets;
    private LayoutInflater mInflater;
    Context context;
    NumberFormat numberFormat;
    public HistoryRecyclerViewCustomAdapter(Context context, ArrayList<Reset> resets) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.resets = resets;
    }

    @NonNull
    @Override
    public HistoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView;
        if(LanguageLocaleHelper.getLanguage(context).equals("ar")) {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resetcard_rtl, parent, false);
            numberFormat = NumberFormat.getInstance(new Locale("ar"));
        }
        else {
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resetcard, parent, false);
            numberFormat = NumberFormat.getInstance(new Locale("en","US"));
        }
        HistoryRecyclerViewHolder rcv = new HistoryRecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerViewHolder holder, final int position) {
        holder.locationAddress.setText(resets.get(position).getLocationAddress());
        holder.phone.setText(resets.get(position).getPhone());
        holder.subtotalMoney.setText(numberFormat.format(Float.parseFloat(resets.get(position).getSubtotalMoney())));
        holder.barCode.setText(resets.get(position).getBarCode());
        holder.barCode.setText(resets.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return this.resets.size();
    }
}
