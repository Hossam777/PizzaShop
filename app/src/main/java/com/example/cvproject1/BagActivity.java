package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BagActivity extends AppCompatActivity {

    static ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        recyclerView = (RecyclerView) findViewById(R.id.bagRecycler);
        lLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lLayout);
        ArrayList<FoodUnit> meals = Cart.getFoodList();
        BagRecyclerViewCustomAdapter rcAdapter = new BagRecyclerViewCustomAdapter(getApplicationContext(), meals);
        recyclerView.setAdapter(rcAdapter);
    }
    public static void showSnackbar() {
        Snackbar snackbar = Snackbar.make(constraintLayout, "Item Removed", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}