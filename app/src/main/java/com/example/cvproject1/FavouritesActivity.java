package com.example.cvproject1;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class FavouritesActivity extends BaseActivity {

    ArrayList<FoodUnit> allMeals = new ArrayList<>();
    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_favourites);
        activityID = R.id.favItem;
        toolbar.setTitle(getString(R.string.favourites));

        recyclerView = findViewById(R.id.favRecycler);
        lLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lLayout);
        setupRecycler();
    }

    private void setupRecycler(){
        System.out.println(MealsHandler.getPizza().size());
        allMeals.addAll(MealsHandler.getPizza());
        allMeals.addAll(MealsHandler.getPasta());
        allMeals.addAll(MealsHandler.getRice());
        ArrayList<FoodUnit> meals = new ArrayList<>();
        for(int i = 0; i < allMeals.size(); i++){
            if(UserHandler.checkInFavourites(allMeals.get(i).getId())){
                meals.add(allMeals.get(i));
            }
        }
        if(meals == null){
            recyclerView.setAdapter(null);
            return;
        }
        RecyclerViewCustomAdapter rcAdapter = new RecyclerViewCustomAdapter(this, meals, FavouritesActivity.class);
        recyclerView.setAdapter(rcAdapter);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageLocaleHelper.onAttach(newBase));
    }
}