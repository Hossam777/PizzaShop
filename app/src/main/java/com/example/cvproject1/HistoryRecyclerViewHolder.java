package com.example.cvproject1;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class HistoryRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView locationAddress;
    TextView subtotalMoney;
    TextView phone;
    TextView barCode;
    TextView dateTime;
    public HistoryRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        locationAddress = itemView.findViewById(R.id.locationAddress);
        subtotalMoney = itemView.findViewById(R.id.subtotalMoney);
        phone = itemView.findViewById(R.id.phone);
        barCode = itemView.findViewById(R.id.barCode);
        dateTime = itemView.findViewById(R.id.estTime);
    }
}
