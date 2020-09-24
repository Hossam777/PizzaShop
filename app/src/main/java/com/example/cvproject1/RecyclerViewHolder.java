package com.example.cvproject1;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    interface CheckBoxState{
        public void onStateChanged();
    }
    ImageView foodImage;
    TextView name;
    TextView description;
    TextView price;
    TextView rating;
    ImageButton buyBtn;
    CheckBox favbtn;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        foodImage = (ImageView)itemView.findViewById(R.id.foodImage);
        name = (TextView) itemView.findViewById(R.id.foodName);
        description = (TextView) itemView.findViewById(R.id.foodDescription);
        price = (TextView) itemView.findViewById(R.id.foodPrice);
        rating = (TextView) itemView.findViewById(R.id.foodRating);
        buyBtn = (ImageButton) itemView.findViewById(R.id.buybtn);
        favbtn = itemView.findViewById(R.id.favbtn);
    }
}
