package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText mail;
    EditText pass;
    EditText name;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
    }

    public void submitSignUp(View view) {
        String userMail = mail.getText() + "";
        String userPass = pass.getText() + "";
        String userName = name.getText() + "";
        String userPhone = phone.getText() + "";
    }
}