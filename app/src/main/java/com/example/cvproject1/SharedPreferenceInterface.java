package com.example.cvproject1;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceInterface {
    public static final String PIZZA_SHOP_PREFERENCES = "PIZZA_SHOP_PREFERENCES" ;
    public static final String bagKey = "bagKey" ;
    public static final String heartKey = "heartKey" ;
    public static final String userDataKey = "userDataKey";
    public static void writeString(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PIZZA_SHOP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String readString(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PIZZA_SHOP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
    public static void removeString(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PIZZA_SHOP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
