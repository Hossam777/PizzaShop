package com.example.cvproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BagRecyclerViewCustomAdapter extends RecyclerView.Adapter<BagRecyclerViewHolder> {
    ArrayList<FoodUnit> meals;
    private LayoutInflater mInflater;
    Context context;
    public BagRecyclerViewCustomAdapter(Context context, ArrayList<FoodUnit> meals) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.meals = meals;
    }

    @NonNull
    @Override
    public BagRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bagcard, parent, false);
        BagRecyclerViewHolder rcv = new BagRecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull BagRecyclerViewHolder holder, final int position) {
        Picasso.get().load(meals.get(position).getLink()).into(holder.foodImage);
        holder.name.setText(meals.get(position).getName());
        holder.price.setText(meals.get(position).getPrice() + "$");
        holder.quantity.setText("x" + meals.get(position).getQuantity());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.removeFoodUnit(meals.get(position).getId());
                Cart.writeTOSharedPreference(context);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                HomeActivity.updateCartQuantity();
                BagActivity.showSnackbar();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.meals.size();
    }
}
