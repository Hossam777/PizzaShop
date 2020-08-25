package com.example.cvproject1;

import android.content.Context;

public class User {
    static String userName;
    static String userMail;
    static String userPhone;
    static boolean userLoggedIn = false;

    public static void readUserData(Context context){
        userName = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.nameKey);
        if(!userName.equals("") && userName != null){
            userMail = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.emailKey);
            userPhone = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.phoneKey);
            userLoggedIn = true;
        }
    }

    public static void logIn(Context context, String userName, String userMail, String userPhone){
        User.userName = userName;
        User.userMail = userMail;
        User.userPhone = userPhone;
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.nameKey, userName);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.emailKey, userMail);
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.phoneKey, userPhone);
        userLoggedIn = true;
    }

    public static void logout(){
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
