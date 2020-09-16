package com.example.cvproject1;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BagActivity extends BaseActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_bag);
        activityName = "Cart";

        recyclerView = (RecyclerView) findViewById(R.id.bagRecycler);
        lLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lLayout);
        final ArrayList<FoodUnit> meals = Cart.getFoodList();
        BagRecyclerViewCustomAdapter rcAdapter = new BagRecyclerViewCustomAdapter(this, meals);
        recyclerView.setAdapter(rcAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ArrayList<FoodUnit> meals = Cart.getFoodList();
        BagRecyclerViewCustomAdapter rcAdapter = new BagRecyclerViewCustomAdapter(this, meals);
        recyclerView.setAdapter(rcAdapter);
    }
    public void checkOut(View view){
        startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
    }
}