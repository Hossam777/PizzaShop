package com.example.cvproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    ArrayList<FoodUnit> meals;
    Class activityClass;
    private LayoutInflater mInflater;
    Context context;
    public RecyclerViewCustomAdapter(Context context, ArrayList<FoodUnit> meals, Class activityClass) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.meals = meals;
        this.activityClass = activityClass;
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
        holder.favbtn.setOnCheckedChangeListener(null);
        holder.favbtn.setChecked(false);
        if(UserHandler.checkInFavourites(meals.get(position).getId())){
            holder.favbtn.setChecked(true);
        }
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.addFoodUnit(meals.get(position));
                Cart.writeTOSharedPreference(context);
                if(activityClass == BaseActivity.class){
                    ((BaseActivity) context).updateCartQuantity();
                    ((BaseActivity) context).showSnackBar(context.getString(R.string.ItemAddedtoCart_string));
                }else if(activityClass == SearchActivity.class){
                    ((SearchActivity) context).showSnackBar(context.getString(R.string.ItemAddedtoCart_string));
                }
            }
        });
        holder.favbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UserHandler.addToFavourites(meals.get(position).getId());
                    UserHandler.updateUserFavouritesInSharedPreference(context);
                }else{
                    UserHandler.removeFromFavourites(meals.get(position).getId());
                    UserHandler.updateUserFavouritesInSharedPreference(context);
                    if(activityClass == FavouritesActivity.class){
                        meals.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.meals.size();
    }
}
