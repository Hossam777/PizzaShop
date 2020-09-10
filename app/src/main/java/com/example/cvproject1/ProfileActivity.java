package com.example.cvproject1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends BaseActivity {

    EditText mail;
    EditText name;
    EditText phone;
    Button updateBtn;
    Button loginBtn;
    Button logoutBtn;
    Button signUpBtn;
    private void showProfileView(){
        loginBtn.setVisibility(View.GONE);
        signUpBtn.setVisibility(View.GONE);
        logoutBtn.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        mail.setVisibility(View.VISIBLE);
        phone.setText(UserHandler.getLoggedInUser().getUserPhone());
        name.setText(UserHandler.getLoggedInUser().getUserName());
        mail.setText(UserHandler.getLoggedInUser().getUserMail());
        userTextName.setText(UserHandler.getLoggedInUser().getUserName());
    }
    private void hideProfileView(){
        loginBtn.setVisibility(View.VISIBLE);
        signUpBtn.setVisibility(View.VISIBLE);
        logoutBtn.setVisibility(View.GONE);
        updateBtn.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        mail.setVisibility(View.GONE);
        userTextName.setText("USER");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_profile);
        activityName = "Profile";
        mail = findViewById(R.id.mail);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        updateBtn = findViewById(R.id.updateBtn);
        loginBtn = findViewById(R.id.loginBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        if(UserHandler.isUserLoggedIn()){
            showProfileView();
        }else{
            hideProfileView();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(UserHandler.isUserLoggedIn()){
            showProfileView();
        }
    }

    public void submitUpdate(View view) {
        String userMail = mail.getText() + "";
        String userName = name.getText() + "";
        String userPhone = phone.getText() + "";
    }

    public void logout(View view) {
        UserHandler.logout(getApplicationContext());
        hideProfileView();
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void signUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}