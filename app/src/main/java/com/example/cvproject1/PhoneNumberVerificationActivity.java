package com.example.cvproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PhoneNumberVerificationActivity extends AppCompatActivity {

    TextView counter;
    TextView codeTextView;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    Timer T = new Timer();
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verfication);
        counter = findViewById(R.id.counter);
        codeTextView = findViewById(R.id.code);

        sendVerificationCode(UserHandler.getLoggedInUser().getUserPhone());
    }

    public void resendCode(View view) {
        sendVerificationCode(UserHandler.getLoggedInUser().getUserPhone());
    }

    private void startCounter(){
        count = 60;
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        count--;
                        if(count == -1){
                            stopCounter();
                            return;
                        }
                        counter.setText("00:" + String.format("%02d", count));
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopCounter(){
        T.cancel();
        T = new Timer();
    }

    public void confirmPhoneNumberBtn(View view) {
        String code = codeTextView.getText().toString().trim();
        if (code.isEmpty() || code.length() < 6) {
            codeTextView.setError("Enter valid code");
            codeTextView.requestFocus();
            return;
        }
        verifyVerificationCode(code);
    }

    private void sendVerificationCode(String mobile) {
        stopCounter();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+20" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        startCounter();
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@Nullable PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@Nullable FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };
    private void verifyVerificationCode(String otp) {
        if(count < 0){
            Toast.makeText(this, "Time Ended, Please resend Code", Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        if(credential.getSmsCode().equals(otp)){
            User user = UserHandler.getLoggedInUser();
            user.setPhoneVerified(true);
            FirebaseListener.addUser(user);
            UserHandler.logIn(getApplicationContext(), user);
            finish();
        }else{
            Toast.makeText(this, "Wrong Code!", Toast.LENGTH_SHORT).show();
        }
    }
}