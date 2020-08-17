package com.example.cvproject1;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseListner {
    public interface FirebaseListners{
        public void onDataChange(ArrayList<FoodUnit> meals);
    }
    static void getData(String link, final FirebaseListners firebaseListners){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(link);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FoodUnit> meals = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FoodUnit meal = postSnapshot.getValue(FoodUnit.class);
                    meals.add(meal);
                    Log.d("Data:", "Value is: " + meal.getPrice());
                }
                firebaseListners.onDataChange(meals);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error:", "Failed to read value.", error.toException());
                firebaseListners.onDataChange(null);
            }
        });
    }
}
