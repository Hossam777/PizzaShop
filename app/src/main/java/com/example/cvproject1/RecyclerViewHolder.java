package com.example.cvproject1;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvproject1.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    ImageView foodImage;
    TextView name;
    TextView description;
    TextView price;
    TextView rating;
    ImageButton buyBtn;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        foodImage = (ImageView)itemView.findViewById(R.id.foodImage);
        name = (TextView) itemView.findViewById(R.id.foodName);
        description = (TextView) itemView.findViewById(R.id.foodDescription);
        price = (TextView) itemView.findViewById(R.id.foodPrice);
        rating = (TextView) itemView.findViewById(R.id.foodRating);
        buyBtn = (ImageButton) itemView.findViewById(R.id.buybtn);
    }
}
