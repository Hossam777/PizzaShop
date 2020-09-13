package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<FoodUnit> allMeals = new ArrayList<>();
    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;
    TextView mealNameTextView;
    NumberPicker ratingNumberPicker;
    RadioGroup mealTypeRadioGroup;
    String foodType = "all";
    String mealName = "";
    float minimumMealRating = 0;
    ConstraintLayout searchOptionsConstraintLayout;
    ConstraintLayout recyclerConstraintLayout;
    ConstraintLayout android_search_ConstraintLayout;
    Boolean settingsClosed = false;
    ImageView settingsIcon;
    int ANIMATION_DURATION = 1000;
    LayoutTransition layoutTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.searchRecycler);
        lLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lLayout);
        ratingNumberPicker = findViewById(R.id.ratingNumberPicker);
        mealNameTextView = findViewById(R.id.search_text);
        mealTypeRadioGroup = findViewById(R.id.radio_group);
        searchOptionsConstraintLayout = findViewById(R.id.searchOptionsConstraintLayout);
        recyclerConstraintLayout = findViewById(R.id.recyclerConstraintLayout);
        android_search_ConstraintLayout = findViewById(R.id.android_search_ConstraintLayout);
        settingsIcon = findViewById(R.id.search_settings_icon);
        layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(ANIMATION_DURATION);
        android_search_ConstraintLayout.setLayoutTransition(layoutTransition);
        fillAllMealsArray();
        setupView();
    }

    private void filterMeals(){
        ArrayList<FoodUnit> newMeals = new ArrayList<>();
        for(int i = 0; i < allMeals.size(); i++){
            if(!foodType.equals("all") && !allMeals.get(i).getId().contains(foodType))
                continue;
            if(!mealName.equals("") && !allMeals.get(i).getName().toLowerCase().contains(mealName))
                continue;
            if(allMeals.get(i).getRating() < minimumMealRating)
                continue;
            newMeals.add(allMeals.get(i));
        }
        updateRecycleView(newMeals);
    }

    private void setupView(){
        String[] numberPickerArray = new String[50];
        for(int i = 0; i < 50; i++){
            numberPickerArray[i] = ((float)i / 10) + "";
        }
        ratingNumberPicker.setMaxValue(49);
        ratingNumberPicker.setMinValue(0);
        ratingNumberPicker.setWrapSelectorWheel(false);
        ratingNumberPicker.setDisplayedValues(numberPickerArray);
        ratingNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mealNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mealName = s.toString().toLowerCase() + "";
                filterMeals();
            }
        });
        mealTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.allRadioButton:
                        foodType = "all";
                        break;
                    case R.id.pizzaRadioButton:
                        foodType = "pizza";
                        break;
                    case R.id.pastaRadioButton:
                        foodType = "pasta";
                        break;
                    case R.id.riceRadioButton:
                        foodType = "rice";
                        break;
                }
                filterMeals();
            }
        });
        ratingNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minimumMealRating = ((float)newVal / 10);
                filterMeals();
            }
        });
    }
    private void fillAllMealsArray() {
        FirebaseListener.getData("Food/Pizza", new FirebaseListener.MealsInterface() {
            @Override
            public void onDataChange(final ArrayList<FoodUnit> meals) {
                if(meals != null){
                    allMeals.addAll(meals);
                }
            }
        });
        FirebaseListener.getData("Food/Pasta", new FirebaseListener.MealsInterface() {
            @Override
            public void onDataChange(final ArrayList<FoodUnit> meals) {
                if(meals != null){
                    allMeals.addAll(meals);
                }
            }
        });
        FirebaseListener.getData("Food/Rice", new FirebaseListener.MealsInterface() {
            @Override
            public void onDataChange(final ArrayList<FoodUnit> meals) {
                if(meals != null){
                    allMeals.addAll(meals);
                    filterMeals();
                }
            }
        });
    }

    private void updateRecycleView(ArrayList<FoodUnit> meals){
        if(meals == null){
            recyclerView.setAdapter(null);
            return;
        }
        RecyclerViewCustomAdapter rcAdapter = new RecyclerViewCustomAdapter(this, meals, SearchActivity.class);
        recyclerView.setAdapter(rcAdapter);
    }

    public void swipe_settings_icon(View view) {
        if(settingsClosed){
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movedown);
            Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateright);
            animation.setDuration(ANIMATION_DURATION);
            animation3.setDuration(ANIMATION_DURATION);
            searchOptionsConstraintLayout.startAnimation(animation);
            settingsIcon.startAnimation(animation3);
            searchOptionsConstraintLayout.setVisibility(View.VISIBLE);
        }else{
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
            Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateleft);
            animation.setDuration(ANIMATION_DURATION);
            animation3.setDuration(ANIMATION_DURATION);
            searchOptionsConstraintLayout.startAnimation(animation);
            settingsIcon.startAnimation(animation3);
            searchOptionsConstraintLayout.setVisibility(View.GONE);
        }
        settingsClosed = !settingsClosed;
    }
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(android_search_ConstraintLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}