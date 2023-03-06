package com.example.tfgsmartwatch.utils;


import android.content.SharedPreferences;

public class util {
    public static String getUserNamePrefs(SharedPreferences preferences){
        return preferences.getString("name","");
    }
    public static String getUserPasswordPrefs(SharedPreferences preferences){
        return preferences.getString("password","");
    }
    public static void removeSharedPreferences(SharedPreferences preferences){
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove("name");
        editor.remove("password");
        editor.apply();

    }
}
