package com.example.tfgsmartwatch.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.tfgsmartwatch.activities.LoginActivity;
import com.example.tfgsmartwatch.activities.MenuPrincipal;
import com.example.tfgsmartwatch.utils.util;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);*/

        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Intent intentLogin=new Intent(SplashActivity.this, LoginActivity.class);
        Intent intentMain=new Intent(SplashActivity.this, MenuPrincipal.class);
        if(!TextUtils.isEmpty(util.getUserEmailPrefs(prefs)) && !TextUtils.isEmpty(util.getUserPasswordPrefs(prefs))){
            startActivity(intentMain);

        }else{
            startActivity(intentLogin);
        }
        finish();
    }
}