package com.example.cvproject1;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BagActivity extends BaseActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;
    TextView itemCounter;
    TextView subtotalMoney;
    TextView emptyBagText;
    double totalMoney = 0;
    NumberFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_bag);
        activityID = R.id.cartItem;
        toolbar.setTitle(getString(R.string.cart_string));
        if(LanguageLocaleHelper.getLanguage(getApplicationContext()).equals("ar")) {
            numberFormat = NumberFormat.getInstance(new Locale("ar"));
        }
        else {
            numberFormat = NumberFormat.getInstance(new Locale("en","US"));
        }

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
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, getString(R.string.cant_check_out), getString(R.string.please_login), "Login", new CustomAlertDialog.CallbackPositive() {
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
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, getString(R.string.cant_check_out), getString(R.string.please_select_food), "Shop", new CustomAlertDialog.CallbackPositive() {
                @Override
                public void doPositive() {
                    finish();
                }

                @Override
                public void doNegative() {

                }
            });
            customAlertDialog.show();
        }else if(!UserHandler.getLoggedInUser().getPhoneVerified()){
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, getString(R.string.cant_check_out), getString(R.string.verify_phone_string), "Verify", new CustomAlertDialog.CallbackPositive() {
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
        subtotalMoney.setText(numberFormat.format(totalMoney));
        itemCounter.setText("(" + numberFormat.format(meals.size()) + getString(R.string.items_string) + ")");
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageLocaleHelper.onAttach(newBase));
    }
}