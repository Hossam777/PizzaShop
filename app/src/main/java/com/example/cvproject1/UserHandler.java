package com.example.cvproject1;

import android.content.Context;

import java.util.HashSet;

public class UserHandler {
    static private User loggedInUser;
    static private HashSet<String> userFavourites = new HashSet<>();
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static Boolean loadUserData(Context context){
        String userFavouritesString = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.userFavouritesKey);
        if(userFavouritesString!= null && userFavouritesString.length() > 2){
            for(String id : userFavouritesString.split(";")){
                userFavourites.add(id);
            }
        }
        String userData = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.userDataKey);
        if(userData != null){
            loggedInUser = new User(userData);
            return true;
        }
        return false;
    }
    public static void logIn(Context context, User user){
        loggedInUser = user;
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.userDataKey, user.toString());
    }
    public static void logout(Context context){
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.userDataKey, null);
        loggedInUser = null;
    }
    public static boolean isUserLoggedIn() {
        return (loggedInUser != null);
    }

    public static void updateUserFavouritesInSharedPreference(Context context){
        String userFavouritesString = "";
        for(String id : userFavourites)
            userFavouritesString += id + ";";
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.userFavouritesKey, userFavouritesString);
    }
    public static void addToFavourites(String mealId){
        userFavourites.add(mealId);
    }
    public static void removeFromFavourites(String mealId){
        userFavourites.remove(mealId);
    }
    public static boolean checkInFavourites(String mealId){
        return userFavourites.contains(mealId);
    }
}
