package com.example.cvproject1;

import android.content.Context;

public class User {
    final static String LOGGED_IN_TRUE = "TRUE";
    final static String LOGGED_IN_FALSE = "FALSE";
    static String userName;
    static String userMail;
    static String userPass;
    static String userPhone;
    static String userLoggedStatusInPreferences;
    static boolean userLoggedIn = false;

    public static Boolean loadUserDataIfLoggedIn(Context context){
        userLoggedStatusInPreferences = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.loggedInKey);
        if(userLoggedStatusInPreferences != null && userLoggedStatusInPreferences.equals(LOGGED_IN_TRUE)){
            userName = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.nameKey);
            userMail = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.emailKey);
            userPhone = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.phoneKey);
            userLoggedIn = true;
            return true;
        }
        return false;
    }

    public static void logIn(Context context, String userName, String userMail, String userPass, String userPhone){
        User.userName = userName;
        User.userMail = userMail;
        User.userPass = userPass;
        User.userPhone = userPhone;
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.nameKey, userName);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.emailKey, userMail);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.phoneKey, userPhone);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.passKey, userPass);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.loggedInKey, LOGGED_IN_TRUE);
        userLoggedIn = true;
    }

    public static void logout(Context context){
        userLoggedIn = false;
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.loggedInKey, LOGGED_IN_FALSE);
    }
    public static void deleteUserData(Context context){
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.nameKey, null);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.emailKey, null);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.phoneKey, null);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.passKey, null);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.loggedInKey, null);
        userLoggedIn = false;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserMail() {
        return userMail;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static boolean isUserLoggedIn() {
        return userLoggedIn;
    }
}
