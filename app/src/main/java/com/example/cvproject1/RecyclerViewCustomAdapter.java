package com.example.cvproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    ArrayList<FoodUnit> meals;
    private LayoutInflater mInflater;
    Context context;
    RecyclerViewCustomAdapter(Context context, ArrayList<FoodUnit> meals) {
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
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Picasso.get().load(meals.get(position).getLink()).into(holder.foodImage);
        holder.name.setText(meals.get(position).getName());
        holder.description.setText(meals.get(position).getDescription());
        holder.price.setText(meals.get(position).getPrice() + "$");
    }

    @Override
    public int getItemCount() {
        return this.meals.size();
    }
}
