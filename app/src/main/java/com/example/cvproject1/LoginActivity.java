package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText mail;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
    }

    public void submitLogIn(View view) {
        String userMail = mail.getText() + "";
        String userPass = pass.getText() + "";
    }
}