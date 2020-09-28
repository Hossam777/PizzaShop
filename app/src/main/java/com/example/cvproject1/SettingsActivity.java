package com.example.cvproject1;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class SettingsActivity extends BaseActivity {

    ToggleButton langToggleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.settings_activity);
        activityID = R.id.settingsItem;
        toolbar.setTitle(getString(R.string.settings));

        langToggleBtn = findViewById(R.id.langToggleBtn);
        if(LanguageLocaleHelper.getLanguage(getApplicationContext()).equals("en")){
            langToggleBtn.setChecked(true);
        }
        langToggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                langChanged = true;
                switch (LanguageLocaleHelper.getLanguage(getApplicationContext())){
                    case "en":
                        LanguageLocaleHelper.setLocale(getApplicationContext(), "ar");
                        break;
                    case "ar":
                        LanguageLocaleHelper.setLocale(getApplicationContext(), "en");
                        break;
                }
                recreate();
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageLocaleHelper.onAttach(newBase));
    }

}