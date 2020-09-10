package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText mail;
    EditText pass;
    EditText name;
    EditText phone;
    TextView headWarningText;
    TextView mailWarningText;
    TextView passWarningText;
    TextView nameWarningText;
    TextView phoneWarningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        headWarningText = findViewById(R.id.headWarningText);
        mailWarningText = findViewById(R.id.mailWarningText);
        passWarningText = findViewById(R.id.passWarningText);
        nameWarningText = findViewById(R.id.nameWarningText);
        phoneWarningText = findViewById(R.id.phoneWarningText);
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(validateMail(s + "")){
                   mailWarningText.setText("");
                }else{
                    mailWarningText.setText("Wrong Mail");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(validatePass(s + "")){
                    passWarningText.setText("");
                }else{
                    passWarningText.setText("Password at least must be 8 Characters");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(validatePhone(s + "")){
                    phoneWarningText.setText("");
                    FirebaseListener.loadUser(s + "", new FirebaseListener.UserInterface() {
                        @Override
                        public void onUserReceived(User user) {
                            if(user != null){
                                phoneWarningText.setText("This Phone is already SignedUp");
                            }
                        }
                    });
                }else{
                    phoneWarningText.setText("Wrong Phone Number");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(validateName(s + "")){
                    nameWarningText.setText("");
                }else{
                    nameWarningText.setText("Name must be at least 2 Characters");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    public void submitSignUp(View view) {
        final String userMail = mail.getText() + "";
        final String userPass = pass.getText() + "";
        final String userName = name.getText() + "";
        final String userPhone = phone.getText() + "";
        if(validateAll(userMail, userPass, userName, userPhone)){
            FirebaseListener.loadUser(userPhone, new FirebaseListener.UserInterface() {
                @Override
                public void onUserReceived(User user) {
                    if(user != null){
                        headWarningText.setText("Fix Issues First Please!");
                    }else{
                        User newUser = new User(userName, userMail, userPass, userPhone);
                        FirebaseListener.addUser(newUser);
                        UserHandler.logIn(getApplicationContext(), newUser);
                        finish();
                    }
                }
            });
        }else{
            headWarningText.setText("Fill valid Data First Please!");
        }
    }

    private boolean validateAll(String mail, String pass, String name, String phone){
        return validateMail(mail) && validatePass(pass) && validateName(name) && validatePhone(phone);
    }

    Boolean validateMail(String mail){
        if(mail.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            return false;
        }
        if(!mail.contains(".com"))
            return false;
        return true;
    }

    Boolean validatePass(String pass){
        if(pass.length() < 8){
            return false;
        }
        return true;
    }

    Boolean validatePhone(String phone){
        if(phone.length() != 11 || !phone.substring(0, 2).equals("01")){
            return false;
        }
        try {
            Integer.parseInt(phone);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    Boolean validateName(String name){
        if(name.length() < 2){
            return false;
        }
        return true;
    }
}