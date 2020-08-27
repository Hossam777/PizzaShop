package com.example.cvproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BagActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;
    private static DrawerLayout drawerLayout;
    private Toolbar toolbar;
    static NavigationView navigationView;
    static TextView cartQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        navigationView.getMenu().findItem(R.id.cartItem).setActionView(R.layout.cart_counter);
        cartQuantity = (TextView) navigationView.getMenu().findItem(R.id.cartItem).getActionView();
        cartQuantity.setText(Cart.getFoodList().size() + "");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.bagRecycler);
        lLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lLayout);
        final ArrayList<FoodUnit> meals = Cart.getFoodList();
        BagRecyclerViewCustomAdapter rcAdapter = new BagRecyclerViewCustomAdapter(getApplicationContext(), meals);
        recyclerView.setAdapter(rcAdapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                if(item.getTitle().equals("Shop")){
                    finish();
                }else if(item.getTitle().equals("Cart")){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if(item.getTitle().equals("History")){
                }
                return false;
            }
        });
    }
    public static void updateCartQuantity(){
        cartQuantity.setText(Cart.getFoodList().size() + "");
    }
    public static void showSnackbar() {
        Snackbar snackbar = Snackbar.make(drawerLayout, "Item Removed", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}