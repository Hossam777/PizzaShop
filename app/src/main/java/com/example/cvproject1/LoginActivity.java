package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText phone;
    EditText pass;
    TextView warningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        warningText = findViewById(R.id.warningText);
    }

    public void submitLogIn(View view) {
        String userPhone = phone.getText() + "";
        final String userPass = pass.getText() + "";
        FirebaseListener.loadUser(userPhone, new FirebaseListener.UserInterface() {
            @Override
            public void onUserReceived(User user) {
                if(user != null && user.getUserPass().equals(userPass)){
                    UserHandler.logIn(getApplicationContext(), user);
                    finish();
                }else{
                    warningText.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}