package com.example.tfgsmartwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;

import com.example.tfgsmartwatch.R;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        int tiempoTranscurrir = 10000; //1 segundo, 1000 millisegundos.

        Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //***Aquí agregamos el proceso a ejecutar.

                Intent intent=new Intent(Feedback.this, Course.class);
                startActivity(intent);

                handler.removeCallbacks(null);
            }
        }, tiempoTranscurrir );//define el tiempo.







    }
}