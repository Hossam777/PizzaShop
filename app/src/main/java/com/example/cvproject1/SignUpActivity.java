package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    EditText mail;
    EditText pass;
    EditText name;
    EditText phone;
    TextView headWarningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        headWarningText = findViewById(R.id.headWarningText);
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(!validateMail(s + "")){
                    mail.setError(getString(R.string.wrong_mail_string));
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
                if(!validatePass(s + "")){
                    pass.setError(getString(R.string.password_at_least_string));
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
                    FirebaseListener.loadUser(s + "", new FirebaseListener.UserInterface() {
                        @Override
                        public void onUserReceived(User user) {
                            if(user != null){
                                phone.setError(getString(R.string.this_phone_already_string));
                            }
                        }
                    });
                }else{
                    phone.setError(getString(R.string.wrong_phone_number_string));
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
                if(!validateName(s + "")){
                    name.setError(getString(R.string.name_must_be_string));
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
                        headWarningText.setText(R.string.fix_issues_string);
                    }else{
                        User newUser = new User(userName, userMail, userPass, userPhone);
                        FirebaseListener.addUser(newUser);
                        UserHandler.logIn(getApplicationContext(), newUser);
                        finish();
                    }
                }
            });
        }else{
            headWarningText.setText(R.string.fill_data_string);
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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageLocaleHelper.onAttach(newBase));
    }
}