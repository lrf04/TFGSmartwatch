package com.example.tfgsmartwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.TextView;

import com.example.tfgsmartwatch.R;

import org.w3c.dom.Text;

public class Feedback extends AppCompatActivity {
    private TextView textViewFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        textViewFeedback=(TextView) findViewById(R.id.textViewFeedback);

        int tiempoTranscurrir = 3000; //1 segundo, 1000 millisegundos.

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String datos = parametros.getString("datos");
            textViewFeedback.setText(datos);
        }
        Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

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