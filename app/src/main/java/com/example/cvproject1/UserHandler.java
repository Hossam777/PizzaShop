package com.example.cvproject1;

import android.content.Context;

public class UserHandler {
    static private User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static Boolean loadUserDataIfLoggedIn(Context context){
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
}
