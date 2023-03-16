package com.example.tfgsmartwatch.utils;


import android.content.SharedPreferences;

public class util {
    public static String getUserEmailPrefs(SharedPreferences preferences){
        return preferences.getString("email","");
    }
    public static String getUserPasswordPrefs(SharedPreferences preferences){
        return preferences.getString("password","");
    }
    public static String getUserIdPrefs(SharedPreferences preferences){
        return preferences.getString("id","0");
    }
    public static void removeSharedPreferences(SharedPreferences preferences){
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.remove("id");
        editor.apply();

    }
}
