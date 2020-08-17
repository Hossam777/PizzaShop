package com.example.cvproject1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    ImageView foodImage;
    TextView name;
    TextView description;
    TextView price;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        foodImage = (ImageView)itemView.findViewById(R.id.foodImage);
        name = (TextView) itemView.findViewById(R.id.foodName);
        description = (TextView) itemView.findViewById(R.id.foodDescription);
        price = (TextView) itemView.findViewById(R.id.foodPrice);
    }
}
