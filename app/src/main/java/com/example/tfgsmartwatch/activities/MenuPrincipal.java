package com.example.tfgsmartwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tfgsmartwatch.R;
import com.example.tfgsmartwatch.databinding.ActivityMainBinding;
import com.example.tfgsmartwatch.utils.util;

import java.sql.Time;
import java.util.Calendar;

public class MenuPrincipal extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences prefs;
    private Button buttonLogOut,button;
    private EditText hora;
    private Time horaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        button=(Button) findViewById(R.id.button);
        buttonLogOut=(Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuPrincipal.this, course.class);
                startActivity(intent);
            }
        });


        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSharedPreferences();
            }
        });
    }

    private void logOut(){
        Intent intent=new Intent(MenuPrincipal.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
    private void removeSharedPreferences(){
        util.removeSharedPreferences(prefs);
        logOut();
    }
}