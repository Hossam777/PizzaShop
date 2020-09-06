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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity {

    protected String activityName;
    protected DrawerLayout drawerLayout;
    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected TextView cartQuantity;
    protected ViewStub viewStub;

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
        if(User.loadUserDataIfLoggedIn(getApplicationContext())){
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerHeaderUserName)).setText(User.getUserName().toUpperCase());
        }
        cartQuantity.setText(Cart.getFoodList().size() + "");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), SharedPreferenceInterface.readString(getApplicationContext(), SharedPreferenceInterface.bagKey), Toast.LENGTH_LONG).show();
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
                if(item.getTitle().equals(activityName)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }else if(item.getTitle().equals("Shop")){
                    finish();
                    return false;
                }else if(item.getTitle().equals("Cart")){
                    startActivity(new Intent(getApplicationContext(), BagActivity.class));
                }else if(item.getTitle().equals("History")){
                    //startActivity(new Intent(getApplicationContext(), BagActivity.class));
                }else if(item.getTitle().equals("Profile")){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
                if(!activityName.equals("Shop"))
                    finish();
                return false;
            }
        });
        if(User.loadUserDataIfLoggedIn(getApplicationContext())){
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.navigationDrawerHeaderUserName)).setText(User.getUserName().toUpperCase());
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
}