package com.example.cvproject1;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class BagActivity extends BaseActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;
    TextView itemCounter;
    TextView subtotalMoney;
    TextView emptyBagText;
    double totalMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_bag);
        activityName = "Cart";

        recyclerView = (RecyclerView) findViewById(R.id.bagRecycler);
        emptyBagText = findViewById(R.id.emptyBagText);
        itemCounter = findViewById(R.id.itemCounter);
        subtotalMoney = findViewById(R.id.subtotalMoney);
        lLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lLayout);
        final ArrayList<FoodUnit> meals = Cart.getFoodList();
        if(meals.size() == 0)
            emptyBagText.setVisibility(View.VISIBLE);
        BagRecyclerViewCustomAdapter rcAdapter = new BagRecyclerViewCustomAdapter(this, meals);
        recyclerView.setAdapter(rcAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ArrayList<FoodUnit> meals = Cart.getFoodList();
        if(meals.size() == 0)
            emptyBagText.setVisibility(View.VISIBLE);
        updateUI(meals);
        BagRecyclerViewCustomAdapter rcAdapter = new BagRecyclerViewCustomAdapter(this, meals);
        recyclerView.setAdapter(rcAdapter);
    }

    public void checkOut(View view){
        if(!UserHandler.isUserLoggedIn()){
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, "Can't Checkout", "Please login first", "Login", new CustomAlertDialog.CallbackPositive() {
                @Override
                public void doPositive() {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    finish();
                }

                @Override
                public void doNegative() {

                }
            });
            customAlertDialog.show();
        }else if(totalMoney == 0){
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, "Can't Checkout", "Please select food from shop menu first", "Shop", new CustomAlertDialog.CallbackPositive() {
                @Override
                public void doPositive() {
                    finish();
                }

                @Override
                public void doNegative() {

                }
            });
            customAlertDialog.show();
        }else{
            startActivity(new Intent(getApplicationContext(), CheckoutActivity.class).putExtra("totalMoney", totalMoney));
        }
    }

    public void updateUI(ArrayList<FoodUnit> meals){
        totalMoney = 0;
        if(meals.size() == 0)
            emptyBagText.setVisibility(View.VISIBLE);
        for(FoodUnit meal : meals){
            totalMoney += meal.getPrice() * meal.getQuantity();
        }
        subtotalMoney.setText(totalMoney + "");
        itemCounter.setText("(" + meals.size() + getString(R.string.items_string) + ")");
    }
}