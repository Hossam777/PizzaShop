package com.example.cvproject1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RiceFragment extends Fragment {

    RecyclerView recyclerView;
    private LinearLayoutManager lLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_rice, null);
        recyclerView = (RecyclerView) root.findViewById(R.id.riceRecycler);
        lLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lLayout);
        RecyclerViewCustomAdapter rcAdapter = new RecyclerViewCustomAdapter(getContext(), MealsHandler.getRice(), BaseActivity.class);
        recyclerView.setAdapter(rcAdapter);
        return root;
    }
}