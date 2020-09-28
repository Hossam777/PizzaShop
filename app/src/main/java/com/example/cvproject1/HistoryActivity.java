package com.example.cvproject1;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_history);
        activityID = R.id.historyItem;
        toolbar.setTitle(getString(R.string.history_string));

        if(!UserHandler.isUserLoggedIn()){
            return;
        }
        recyclerView = (RecyclerView) findViewById(R.id.historyRecycler);
        lLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lLayout);
        FirebaseListener.loadResets("Resets/" + UserHandler.getLoggedInUser().getUserPhone(), new FirebaseListener.ResetsInterface() {
            @Override
            public void onResetsReceived(ArrayList<Reset> resets) {
                HistoryRecyclerViewCustomAdapter rcAdapter = new HistoryRecyclerViewCustomAdapter(getApplicationContext(), resets);
                recyclerView.setAdapter(rcAdapter);
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageLocaleHelper.onAttach(newBase));
    }
}