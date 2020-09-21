package com.example.cvproject1;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomAlertDialog implements android.view.View.OnClickListener  {
    public Activity activity;
    public Button alertPositiveButton, alertNegativeButton;
    TextView alertTitle, alertMessage;
    AlertDialog dialog;
    String title, message, positiveButtonText;
    interface CallbackPositive {
        public void doPositive();
        public void doNegative();
    }
    CallbackPositive callbackPositive;

    public CustomAlertDialog(@NonNull Activity activity, String title, String message, String positiveButtonText, CallbackPositive callbackPositive) {
        this.activity = activity;
        this.callbackPositive = callbackPositive;
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View customLayout = activity.getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        builder.setView(customLayout);
        alertPositiveButton = customLayout.findViewById(R.id.alertPositiveButton);
        alertNegativeButton = customLayout.findViewById(R.id.alertNegativeButton);
        alertTitle = customLayout.findViewById(R.id.alertTitle);
        alertMessage = customLayout.findViewById(R.id.alertMessage);
        if(callbackPositive != null) {
            alertPositiveButton.setVisibility(View.VISIBLE);
            alertPositiveButton.setText(positiveButtonText);
        }
        alertTitle.setText(title);
        alertMessage.setText(message);
        alertPositiveButton.setOnClickListener(this);
        alertNegativeButton.setOnClickListener(this);
        dialog = builder.create();
    }

    public void show(){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alertPositiveButton:
                callbackPositive.doPositive();
                break;
            case R.id.alertNegativeButton:
                callbackPositive.doNegative();
                break;
            default:
                break;
        }
        dismiss();
    }
}
