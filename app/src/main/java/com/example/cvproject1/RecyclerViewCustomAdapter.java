package com.example.cvproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    ArrayList<FoodUnit> meals;
    private LayoutInflater mInflater;
    Context context;
    public RecyclerViewCustomAdapter(Context context, ArrayList<FoodUnit> meals) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.meals = meals;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodcard, parent, false);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        Picasso.get().load(meals.get(position).getLink()).into(holder.foodImage);
        holder.name.setText(meals.get(position).getName());
        holder.description.setText(meals.get(position).getDescription());
        holder.price.setText(meals.get(position).getPrice() + "$");
        holder.rating.setText(meals.get(position).getRating() + "");
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.addFoodUnit(meals.get(position));
                Cart.writeTOSharedPreference(context);
                ((BaseActivity) context).updateCartQuantity();
                ((BaseActivity) context).showSnackBar("Item Added to Cart");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.meals.size();
    }
}
