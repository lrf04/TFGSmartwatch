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
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgsmartwatch.API.Api;
import com.example.tfgsmartwatch.API.ApiService;
import com.example.tfgsmartwatch.R;
import com.example.tfgsmartwatch.models.Alumno;
import com.example.tfgsmartwatch.models.ConfigurationStudent;
import com.example.tfgsmartwatch.utils.util;

import org.w3c.dom.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Course extends AppCompatActivity {
    private TextView textViewTime,textViewScore,textViewDifference,textViewCurrent,textViewPrevious,textViewPuntuacion;
    private ProgressBar pb;

    public int puntuacion,umbral;
    public int higherRateCont,lowerRateCont,higherMovementCont,lowerMovementCont,noMovementCont,higherRateMovementCont,lowerRateMovementCont,higherRateLowerMovementCont,lowerRateHigherMovementCont;
    public int totalNervioso,vecesCalmado,vecesNoCalmado,hola;

    private static final int CODE_GPS = 0, CODE_SENSOR = 1;

    private SensorManager sensorManagerHeartRate,sensorManagerAccelerometer;
    private Sensor sensorRitmoCardiaco,sensorAcelerometer;
    private String name,dayName,diaActual;

    public int movementTimeThread;
    private int higherHeartRate,lowerHeartRate,movementTime,movementPercentage,noMovementTime,noMovementPercentage;
    private String higherRateMessage,lowerRateMessage,lowerMovementMessage,higherMovementMessage,higherRateLowerMovementMessage,lowerRateHigherMovementMessage,higherRateMovementMessage,lowerRateMovementMessage,noMovementMessage;
    public float currentHeartRate;
    public double accelerationCurrentValue,accelerationPreviousValue,changeInAcceleration;
    public ArrayList<String> resultados=new ArrayList<>();

    public ArrayList<ArrayList<String>> resultadosNuevos=new ArrayList<>();
    public ArrayList<ArrayList<String>> resultadosNuevosDia=new ArrayList<>();
    public ArrayList<ArrayList<String>> ejemplo=new ArrayList<>();
    public ArrayList<ArrayList<String>> ritmos;
    public ArrayList<ArrayList<Integer>> movimientos;

    public int contador=0,contador1=0;


    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //Recibo datos y los convierto al final en un array de arrays con las asignaturas de ese día ordenadas por hora.
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
           resultados=getIntent().getExtras().getStringArrayList("resultados");

        }


        resultadosNuevos=split(5,resultados);
/*
        for(int i=0;i<resultadosNuevos.size();i++){
            for(int j=0;j<4;j=j+4){
                if(!resultadosNuevos.get(i).get(j).equals("recreo")){
                    diaActual=resultadosNuevos.get(i).get(j+1);
                    LocalTime timeValue = LocalTime.parse(resultadosNuevos.get(i).get(j+2));
                    LocalTime timeValue1 = LocalTime.parse(resultadosNuevos.get(i).get(j+3));
                    LocalTime ahora = LocalTime.now();

                    dayName= LocalDate.now().getDayOfWeek().name();

                    switch (dayName){
                        case "MONDAY":
                            dayName="lunes";
                            break;
                        case "TUESDAY":
                            dayName="martes";
                            break;
                        case "WEDNESDAY":
                            dayName="miercoles";
                            break;
                        case "THURSDAY":
                            dayName="jueves";
                            break;
                        case "FRIDAY":
                            dayName="viernes";
                            break;
                        case "SATURDAY":
                            dayName="sábado";
                            break;
                        case "SUNDAY":
                            dayName="domingo";
                            break;
                    }
                    if(dayName.equals(diaActual)){
                        resultadosNuevosDia.add(resultadosNuevos.get(i));
                    }

                    *//*if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual)){

                        buttonClase.setEnabled(true);

                    }*//*


                }else{
                    LocalTime timeValue = LocalTime.parse(resultadosNuevos.get(i).get(j+2));
                    LocalTime timeValue1 = LocalTime.parse(resultadosNuevos.get(i).get(j+3));
                    LocalTime ahora = LocalTime.now();

                    dayName=LocalDate.now().getDayOfWeek().name();
*//*
                    if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual) ){
                        buttonRecreo.setEnabled(true);


                    }*//*

                }
            }
        }*/
        for(int i=0;i<resultadosNuevos.size();i++){
            for(int j=0;j<5;j=j+5){
                    diaActual=resultadosNuevos.get(i).get(j+2);
                    LocalTime timeValue = LocalTime.parse(resultadosNuevos.get(i).get(j+3));
                    LocalTime timeValue1 = LocalTime.parse(resultadosNuevos.get(i).get(j+4));
                    LocalTime ahora = LocalTime.now();

                    dayName= LocalDate.now().getDayOfWeek().name();

                    switch (dayName){
                        case "MONDAY":
                            dayName="lunes";
                            break;
                        case "TUESDAY":
                            dayName="martes";
                            break;
                        case "WEDNESDAY":
                            dayName="miercoles";
                            break;
                        case "THURSDAY":
                            dayName="jueves";
                            break;
                        case "FRIDAY":
                            dayName="viernes";
                            break;
                        case "SATURDAY":
                            dayName="sábado";
                            break;
                        case "SUNDAY":
                            dayName="domingo";
                            break;
                    }
                    if(dayName.equals(diaActual)){
                        resultadosNuevosDia.add(resultadosNuevos.get(i));
                    }

                    /*if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual)){

                    buttonClase.setEnabled(true);

                }*/



        }
    }



        ejemplo=ordenar(resultadosNuevosDia);



//***********************************************************************************************************************************
// ***********************************************************************************************************************************
        bindUI();

        /*textViewPuntuacion.setText("Puntuacion= "+puntuacion);*/

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
                    /*Toast.makeText(Course.this, "El servidor retornó datos", Toast.LENGTH_SHORT).show();*/

                    //Asignación de parámetros
                    higherHeartRate=configuration.getHigherHeartRate();
                    lowerHeartRate=configuration.getLowerHeartRate();
                    movementTime=configuration.getMovementMonitoringTime();
                    movementPercentage=configuration.getMovementPercentage();
                    noMovementTime=configuration.getNoMovementMonitoringTime();
                    noMovementPercentage=configuration.getNoMovementPercentage();

                    //Asignación de mensajes
                    higherRateMessage=configuration.getHigherRate();
                    lowerRateMessage=configuration.getLowerRate();
                    lowerMovementMessage=configuration.getLowerMovement();
                    higherMovementMessage=configuration.getHigherMovement();
                    higherRateMovementMessage=configuration.getHigherRateMovement();
                    lowerRateMovementMessage=configuration.getLowerRateMovement();
                    noMovementMessage=configuration.getNoMovement();
                    higherRateLowerMovementMessage=configuration.getHigherRateLowerMovement();
                    lowerRateHigherMovementMessage=configuration.getLowerRateHigherMovement();

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

//********************************************************************************************************************************
//********************************************************************************************************************************

        class Hilo2 extends Thread {
            @Override
            public void run() {
                while (contador1<3) {
                    try {
                        Thread.sleep(10000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contador++;
                                /*if(movimientos.isEmpty() && ritmos.isEmpty()){

                                }*/
                                Toast.makeText(Course.this, "ADIOS", Toast.LENGTH_SHORT).show();

                            }

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }



        //Hilo que se repite cada 30 segundos.
        class Hilo1 extends Thread {
            @Override
            public void run() {
                while (contador<23) {
                    try {
                        Thread.sleep(10000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int indice=getIndice(ejemplo);
                                LocalTime ahora = LocalTime.now();
                                LocalTime fin = LocalTime.parse(ejemplo.get(indice).get(4));
                                //Condicion de finalización
                                if(ahora.compareTo(fin)>0 && indice==(ejemplo.size()-1)){
                                    //Se debe hacer el popUp y modificar la variable del while del hilo para que pare
                                }

                                contador++;



                                //Aquí añadimos los datos a los arrays
                                double changeInAcceleration1=changeInAcceleration;
                                float currentHeartRate1=currentHeartRate;

                                /*if(ejemplo.get(indice).get(1).equals("recreo")){
                                    //Cambiar variable para que el hilo pare.
                                    Intent intent=new Intent(Course.this,Recreo.class);
                                    startActivity(intent);
                                    contador=6;
                                }else{*/


                                    movimientos.get(indice).add((int)changeInAcceleration1);

                                    if(currentHeartRate1>lowerHeartRate && currentHeartRate1<higherHeartRate){
                                        ritmos.get(indice).add("SI");
                                    }else{
                                        ritmos.get(indice).add("NO");
                                    }
                                    Toast.makeText(Course.this, "AHORA", Toast.LENGTH_SHORT).show();

                                    situaciones(changeInAcceleration1,currentHeartRate1);
                                    if(contador==20){
                                        if (movimientos.isEmpty() && ritmos.isEmpty() && puntuacion>0) {

                                        }
                                    }

                                /*}*/

                            }

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        /*movementTimeThread=movementTime*60000;*/
        movementTimeThread=20000;

//********************************************************************************************************************************
//********************************************************************************************************************************
        //Permisos
        if(ContextCompat.checkSelfPermission(Course.this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED){
            String[] permissions=new String[1];
            permissions[0]=android.Manifest.permission.BODY_SENSORS;
            requestPermissions(permissions,CODE_SENSOR);

        }else{
            showHeartRate();
            showAcceleration();

            /*hilo.start();*/
            Thread hilo1=new Thread(new Hilo1(),"hilo1");
            Thread hilo2=new Thread(new Hilo1(),"hilo2");

                movimientos=new ArrayList<>();
                ritmos=new ArrayList<>();
                for(int i=0;i<ejemplo.size();i++){
                    movimientos.add(new ArrayList<Integer>());
                    ritmos.add(new ArrayList<String>());
                }




            hilo1.start();
            /*try {
                hilo1.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            hilo2.start();*/



           /* new Hilo1().start();*/


        }
    }//Fin onCreate

//********************************************************************************************************************************
//********************************************************************************************************************************

    public static ArrayList<ArrayList<String>> split(int numberOfElements, ArrayList<String> sentences) {
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        int index = 0;
        for (String sentence : sentences) {
            if (index % numberOfElements == 0) {
                lists.add(new ArrayList<String>());
            }
            lists.get(index / numberOfElements).add(sentences.get(index));
            index++;
        }
        return lists;
    }

    public int menor(ArrayList<ArrayList<String>> array){
        int posicion=0;
        ArrayList<String> menor;
        menor=array.get(0);

        LocalTime timeValue = LocalTime.parse(menor.get(3));
        /*for(int i=0;i<resultadosNuevos.size();i++){*/
        for(int i=0;i<array.size();i++){
            for(int j=0;j<5;j=j+5) {
                LocalTime timeValue1 = LocalTime.parse(array.get(i).get(j+3));
                LocalTime ahora = LocalTime.now();


                if(timeValue1.compareTo(timeValue)<0){
                    posicion=i;
                    timeValue = LocalTime.parse(array.get(i).get(j+3));

                }

            }

        }
        return posicion;
    }


    public ArrayList<ArrayList<String>> ordenar(ArrayList<ArrayList<String>> array){
        ArrayList<ArrayList<String>> aux= new ArrayList<>(array);
        ArrayList<ArrayList<String>> aux1= new ArrayList<>();
        int posicion;

        for(int i=0;i<aux.size();i++){
            posicion=menor(array);
            aux1.add(array.get(posicion));
            array.remove(posicion);
        }
        return aux1;
    }

    public int getIndice(ArrayList<ArrayList<String>> array){
        LocalTime ahora = LocalTime.now();
        int indice=0;

        for(int i=0;i<array.size();i++){
            for(int j=0;j<5;j=j+5){
                LocalTime timeValue = LocalTime.parse(array.get(i).get(j+3));
                LocalTime timeValue1 = LocalTime.parse(array.get(i).get(j+4));

                if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0){
                    indice=i;
                }
            }
        }
        return indice;
    }


    private void bindUI(){
        textViewScore=(TextView) findViewById(R.id.textViewRitmo);
        textViewDifference=(TextView) findViewById(R.id.textViewDifference);
        textViewCurrent=(TextView) findViewById(R.id.textViewCurrent);
        textViewPrevious=(TextView) findViewById(R.id.textViewPrevious);
        textViewPuntuacion=(TextView) findViewById(R.id.textViewPuntuacion);
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
                /*showHeartRate();*/
                /*hilo.start();*/

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

    //Sensor ritmo cardiaco
    SensorEventListener listenerHeartRate = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            currentHeartRate = sensorEvent.values[0];
            textViewScore.setText("Actual: "+currentHeartRate);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    //Acelerómetro
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


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



    public boolean tranquilo(float currentHeartRate,int higherHeartRate,int lowerHeartRate,double changeInAcceleration){
        if(/*currentHeartRate>lowerHeartRate && */currentHeartRate<higherHeartRate &&changeInAcceleration<14){
            return true;
        }else{
            return false;
        }
    }



    /*public void comprobar(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //***Aquí agregamos el proceso a ejecutar.
                if(tranquilo(currentHeartRate,higherHeartRate,lowerHeartRate,changeInAcceleration)){
                    vecesCalmado=vecesCalmado+1;
                    puntuacion=puntuacion+1;

                }else {
                    vecesNoCalmado = vecesNoCalmado + 1;
                }
                handler.removeCallbacks(null);
            }
        }, 10000 );//define el tiempo.
    }*/

    /*class Hilo2 extends Thread {
        @Override
        public void run() {
                try {
                    Thread.sleep(10000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(tranquilo(currentHeartRate,higherHeartRate,lowerHeartRate,changeInAcceleration)){
                                vecesCalmado=vecesCalmado+1;
                                puntuacion=puntuacion+1;

                            }else {
                                vecesNoCalmado = vecesNoCalmado + 1;
                            }


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }*/


    public void situaciones(double movement,float rate){

            //1.Ritmo alto y movimiento alto
            if(rate>higherHeartRate && movement>5){
                popUp(higherRateMovementMessage);
            }

            //2.Ritmo alto y movimiento bajo
            else if(rate>higherHeartRate && movement<5){

                popUp(higherRateLowerMovementMessage);

            }


            //3.Ritmo alto
            else if(rate>higherHeartRate){
                popUp(higherRateMessage);

            }

            //4.Movimiento alto
            else if(movement>5){
                /*Toast.makeText(this, higherMovementMessage, Toast.LENGTH_SHORT).show();*/
                popUp(higherMovementMessage);

            }


            else{
                puntuacion=puntuacion+1;
            }

    }

    public void popUp(String mensaje){

        View popupView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_validar,null,false);
        final PopupWindow popupWindow=new PopupWindow(popupView,500,800,false);
        popupWindow.showAtLocation(textViewScore, Gravity.CENTER,0,0);

        TextView tv=popupView.findViewById(R.id.textViewPopUp);
        tv.setText(mensaje);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //***Aquí agregamos el proceso a ejecutar.
                popupWindow.dismiss();

                handler.removeCallbacks(null);
            }
        }, 2000 );//define el tiempo.

        /*popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });*/
    }


}