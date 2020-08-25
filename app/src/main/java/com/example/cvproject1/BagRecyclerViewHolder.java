package com.example.cvproject1;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvproject1.R;


public class BagRecyclerViewHolder extends RecyclerView.ViewHolder {
    ImageView foodImage;
    TextView name;
    TextView price;
    TextView quantity;
    ImageButton deleteBtn;
    public BagRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        foodImage = (ImageView)itemView.findViewById(R.id.foodImage);
        name = (TextView) itemView.findViewById(R.id.foodName);
        price = (TextView) itemView.findViewById(R.id.foodPrice);
        quantity = (TextView) itemView.findViewById(R.id.quantity);
        deleteBtn = (ImageButton) itemView.findViewById(R.id.buybtn);
    }
}
