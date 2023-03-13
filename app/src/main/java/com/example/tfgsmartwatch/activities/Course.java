package com.example.tfgsmartwatch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgsmartwatch.API.Api;
import com.example.tfgsmartwatch.API.ApiService;
import com.example.tfgsmartwatch.R;
import com.example.tfgsmartwatch.models.Alumno;
import com.example.tfgsmartwatch.models.ConfigurationStudent;
import com.example.tfgsmartwatch.utils.util;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Course extends AppCompatActivity {
    private TextView textViewTime,textViewScore,textViewDifference,textViewCurrent,textViewPrevious;
    private ProgressBar pb;

    private int puntuacion=0;
    private static final int CODE_GPS = 0, CODE_SENSOR = 1;

    private SensorManager sensorManagerHeartRate,sensorManagerAccelerometer;
    private Sensor sensorRitmoCardiaco,sensorAcelerometer;

    private int higherHeartRate,lowerHeartRate,movementTime,movementPercentage,noMovementTime,noMovementPercentage;

    private double accelerationCurrentValue,accelerationPreviousValue,changeInAcceleration;

    SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


        bindUI();

        //SharedPreferences
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String id1= util.getUserIdPrefs(prefs);
        int id=Integer.parseInt(id1);

        //Retrofit
        ApiService service= Api.getApi().create(ApiService.class);
        Call<ConfigurationStudent> configurationCall=service.getConfiguration(id);

        configurationCall.enqueue(new Callback<ConfigurationStudent>() {
            @Override
            public void onResponse(Call<ConfigurationStudent> call, Response<ConfigurationStudent> response) {
                if(response.isSuccessful()){
                    ConfigurationStudent configuration=response.body();
                    Toast.makeText(Course.this, "El servidor retornó datos", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Course.this, "El servidor retornó un error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ConfigurationStudent> call, Throwable t) {
                if(t instanceof IOException){
                    Toast.makeText(Course.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }else{
                    Toast.makeText(Course.this, "Problema de conversión", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if(ContextCompat.checkSelfPermission(Course.this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED){
            String[] permissions=new String[1];
            permissions[0]=android.Manifest.permission.BODY_SENSORS;
            requestPermissions(permissions,CODE_SENSOR);

        }else{
            showHeartRate();
            showAcceleration();
        }




    }

    private void bindUI(){
        textViewScore=(TextView) findViewById(R.id.textViewRitmo);
        textViewDifference=(TextView) findViewById(R.id.textViewDifference);
        textViewCurrent=(TextView) findViewById(R.id.textViewCurrent);
        textViewPrevious=(TextView) findViewById(R.id.textViewPrevious);
        pb=(ProgressBar) findViewById(R.id.progressBar);


    }
    private void showHeartRate() {
        sensorManagerHeartRate = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        sensorRitmoCardiaco = sensorManagerHeartRate.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        sensorManagerHeartRate.registerListener(listenerHeartRate, sensorRitmoCardiaco, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void showAcceleration(){
        sensorManagerAccelerometer = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        sensorAcelerometer = sensorManagerAccelerometer.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManagerAccelerometer.registerListener(listenerAcelerometer, sensorAcelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODE_SENSOR) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showHeartRate();
            } else {
                Toast.makeText(Course.this, "PERMISSION FOR HEART RATE SENSOR NOT GRANTED", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManagerHeartRate != null) {
            sensorManagerHeartRate.unregisterListener(listenerHeartRate);
        }
    }

    SensorEventListener listenerHeartRate =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float value = sensorEvent.values[0];
            textViewScore.setText("Current heart rate is " + value);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    SensorEventListener listenerAcelerometer=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerationCurrentValue=Math.sqrt((x*x+y*y+z*z));
            changeInAcceleration= Math.abs(accelerationCurrentValue-accelerationPreviousValue);
            accelerationPreviousValue=accelerationCurrentValue;

            textViewDifference.setText("Diferencia= "+(int)changeInAcceleration);
            textViewCurrent.setText("Actual= "+(int)accelerationCurrentValue);
            textViewPrevious.setText("Previa= "+(int)accelerationPreviousValue);
            pb.setProgress((int)changeInAcceleration);

            if(changeInAcceleration>14){
                textViewDifference.setBackgroundColor(Color.BLUE);
                Intent intent=new Intent(Course.this,Feedback.class);
                startActivity(intent);
            }
            else if(changeInAcceleration>5){
                textViewDifference.setBackgroundColor(Color.RED);
            }
            else{
                textViewDifference.setBackgroundColor(Color.YELLOW);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}