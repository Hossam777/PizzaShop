package com.example.cvproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.net.InetAddress;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    protected int activityID;
    protected DrawerLayout drawerLayout;
    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected TextView cartQuantity;
    protected ViewStub viewStub;
    protected TextView userTextName;
    static protected boolean langChanged;
    protected static String foodLink = "Food";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onCreateDrawer() {
        setContentView(R.layout.activity_base);
        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        navigationView.getMenu().findItem(R.id.cartItem).setActionView(R.layout.cart_counter);
        cartQuantity = (TextView) navigationView.getMenu().findItem(R.id.cartItem).getActionView();
        Cart.readFromSharedPreference(getApplicationContext());
        userTextName = ((TextView)navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerHeaderUserName));
        userTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        if(UserHandler.loadUserData(getApplicationContext())){
            userTextName.setText(UserHandler.getLoggedInUser().getUserName().toUpperCase());
        }
        cartQuantity.setText(Cart.getFoodList().size() + "");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.actionbar_cart && !(activityID == R.id.cartItem)){
                    startActivity(new Intent(getApplicationContext(), BagActivity.class));
                }else if(item.getItemId() == R.id.actionbar_searchID){
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                if(item.getItemId() == activityID){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }else if(item.getItemId() == R.id.shopItem){
                    finish();
                    return false;
                }else if(item.getItemId() == R.id.cartItem){
                    startActivity(new Intent(getApplicationContext(), BagActivity.class));
                }else if(item.getItemId() == R.id.historyItem){
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                }else if(item.getItemId() == R.id.profileItem){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }else if(item.getItemId() == R.id.favItem){
                    startActivity(new Intent(getApplicationContext(), FavouritesActivity.class));
                }else if(item.getItemId() == R.id.settingsItem){
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                }
                if(!(activityID == R.id.shopItem))
                    finish();
                return false;
            }
        });
        if(UserHandler.loadUserData(getApplicationContext())){
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerHeaderUserName)).setText(UserHandler.getLoggedInUser().getUserName().toUpperCase());
        }
        viewStub = findViewById(R.id.content_frame);
    }
    public void updateView(int viewId){
        viewStub.setLayoutResource(viewId);
        viewStub.inflate();
    }
    public void updateCartQuantity(){
        cartQuantity.setText(Cart.getFoodList().size() + "");
    }
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(drawerLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    protected void loadMeals(){
        MealsHandler.clearData();
        FirebaseListener.getData(foodLink + "/" + "Pizza", new FirebaseListener.MealsInterface() {
            @Override
            public void onDataChange(final ArrayList<FoodUnit> meals) {
                if(meals != null){
                    MealsHandler.setPizza(meals);
                }
            }
        });
        FirebaseListener.getData(foodLink + "/" + "Pasta", new FirebaseListener.MealsInterface() {
            @Override
            public void onDataChange(final ArrayList<FoodUnit> meals) {
                if(meals != null){
                    MealsHandler.setPasta(meals);
                }
            }
        });
        FirebaseListener.getData(foodLink + "/" + "Rice", new FirebaseListener.MealsInterface() {
            @Override
            public void onDataChange(final ArrayList<FoodUnit> meals) {
                if(meals != null){
                    MealsHandler.setRice(meals);
                }
            }
        });
    }
    static boolean isNetworkConnected() {
        try {
            InetAddress inetAddress = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !inetAddress.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageLocaleHelper.onAttach(newBase));
    }
}