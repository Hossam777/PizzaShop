package com.example.cvproject1;

import android.os.Bundle;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_profile);
        activityName = "Profile";
    }
}