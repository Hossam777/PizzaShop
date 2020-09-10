package com.example.cvproject1;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseListener {
    public interface MealsInterface {
        public void onDataChange(ArrayList<FoodUnit> meals);
    }
    public interface UserInterface {
        public void onUserReceived(User user);
    }
    public static void getData(String link, final MealsInterface mealsInterface){
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
                mealsInterface.onDataChange(meals);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error:", "Failed to read value.", error.toException());
                mealsInterface.onDataChange(null);
            }
        });
    }
    public static void loadUser(String phone, final UserInterface userInterface){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + phone);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userInterface.onUserReceived(user);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error:", "Failed to read value.", error.toException());
                userInterface.onUserReceived(null);
            }
        });
    }

    public static void addUser(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/" + user.getUserPhone());
        myRef.setValue(user);
    }
}
