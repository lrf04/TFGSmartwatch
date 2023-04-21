package com.example.tfgsmartwatch.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
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
import com.example.tfgsmartwatch.models.ConfigurationStudent;
import com.example.tfgsmartwatch.models.Data;
import com.example.tfgsmartwatch.models.DatosClase;
import com.example.tfgsmartwatch.models.DatosRecreo;
import com.example.tfgsmartwatch.utils.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Course extends AppCompatActivity {
    private TextView textViewTime,textViewScore,textViewDifference,textViewCurrent,textViewPrevious,textViewPuntuacion;
    private ProgressBar pb;

    public int puntuacion,umbral;
    public int higherRateCont,lowerRateCont,higherMovementCont,lowerMovementCont,noMovementCont,higherRateMovementCont,lowerRateMovementCont,higherRateLowerMovementCont,lowerRateHigherMovementCont;

    public boolean activoHilo1 =true,variableFinal=false,activoHilo2=true;

    private static final int CODE_GPS = 0, CODE_SENSOR = 1;

    private SensorManager sensorManagerHeartRate,sensorManagerAccelerometer;
    private Sensor sensorRitmoCardiaco,sensorAcelerometer;
    private String name,dayName,diaActual;
    public Boolean variable1=false;
    public Data datosNuevos;

    public int movementTimeThread;
    private int higherHeartRate,lowerHeartRate,movementTime,movementPercentage,noMovementTime,noMovementPercentage;
    public int configuracionId,studentId;
    private String higherRateMessage,lowerRateMessage,lowerMovementMessage,higherMovementMessage,higherRateLowerMovementMessage,lowerRateHigherMovementMessage,higherRateMovementMessage,lowerRateMovementMessage,noMovementMessage;
    public float currentHeartRate;
    public double accelerationCurrentValue,accelerationPreviousValue,changeInAcceleration;
    public ArrayList<String> resultados=new ArrayList<>();

    public ArrayList<ArrayList<String>> resultadosNuevos=new ArrayList<>();
    public ArrayList<ArrayList<String>> resultadosNuevosDia=new ArrayList<>();
    public ArrayList<ArrayList<String>> ejemplo=new ArrayList<>();
    public ArrayList<ArrayList<String>> ritmos;
    public ArrayList<ArrayList<Integer>> movimientos;

    ArrayList<String> recreoAux=new ArrayList<>();

    public int contador=0,contador1=0;

    public int distance = 0;
    public ArrayList<String> recreoFinal = new ArrayList<>();
    private SharedPreferences sh;
    public Boolean variable=false,variableHola=false,variableFin=false;
    private Sensor stepSensor;
    private SensorManager stepSensorManager;
    public int totalSteps =0,previousSteps,currentSteps=0,finalSteps=0;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    public Location currentLocation;
    public Location previousLocation=new Location("");
    public int indiceRecreo,indicePeriodoRecreo;

    public int id;


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
        sh = getSharedPreferences("StepPreferences", MODE_PRIVATE);

        previousSteps = Integer.parseInt(sh.getString("stepsFromPreference","0"));

        previousLocation.setLatitude(0.0d);
        previousLocation.setLongitude(0.0d);

        //Se piden los permisos para el sensor de pasos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    CODE_SENSOR);
            // Permission is not granted
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
                            dayName="sabado";
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
        id=Integer.parseInt(id1);

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
                    configuracionId=configuration.getId();
                    studentId=configuration.getStudentId();
                    higherHeartRate=configuration.getHigherHeartRate();
                    lowerHeartRate=configuration.getLowerHeartRate();


                    //Asignación de mensajes
                    higherRateMessage=configuration.getHigherRate();
                    lowerRateMessage=configuration.getLowerRate();
                    lowerMovementMessage=configuration.getLowerMovement();
                    higherMovementMessage=configuration.getHigherMovement();
                    higherRateMovementMessage=configuration.getHigherRateMovement();
                    lowerRateMovementMessage=configuration.getLowerRateMovement();
                    noMovementMessage=configuration.getNoMovement();


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





        //Hilo que se repite cada 30 segundos.
        /*class Hilo1 extends Thread {
            @Override
            public void run() {
                while (activoHilo1) {
                    try {
                        Thread.sleep(30000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int indice=getIndice(ejemplo);
                                LocalTime ahora = LocalTime.now();
                                LocalTime fin = LocalTime.parse(ejemplo.get(indice).get(4));

                                if(!ejemplo.get(indice).get(0).equals("recreo")){
                                    if (indice == (ejemplo.size() - 1)) {
                                        variable1 = true;
                                    }

                                    //Condicion de finalización
                                    if (variable1 && indice == 0) {
                                        activoHilo1 = false;
                                        if (variableFinal) {
                                            //Se debe hacer el popUp y modificar la variable del while del hilo para que pare
                                            popUpPuntuacion("Tu puntuación ha sido: " + puntuacion);
                                            datosNuevos = saveData(ritmos, movimientos);
                                            datosNuevos.getStudentId();

                                            Call<Data> dataCall = service.postConfigurationData(datosNuevos);
                                            dataCall.enqueue(new Callback<Data>() {
                                                @Override
                                                public void onResponse(Call<Data> call, Response<Data> response) {
                                                    if (response.isSuccessful()) {

                                                        Toast.makeText(Course.this, "El post se hizo correctamente", Toast.LENGTH_SHORT).show();
                                                    *//*Intent intent = new Intent(Course.this,MenuPrincipal.class);
                                                    startActivity(intent);*//*

                                                    } else {

                                                        Toast.makeText(Course.this, "Error:" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                                        Log.d("mensaje", response.errorBody().toString());

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<Data> call, Throwable t) {
                                                    if (t instanceof IOException) {
                                                        Toast.makeText(Course.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                                        Log.d("mensaje", t.getMessage());
                                                        t.printStackTrace();
                                                    } else {
                                                        Toast.makeText(Course.this, "Problema de conversión", Toast.LENGTH_SHORT).show();
                                                        Toast.makeText(Course.this, "ee: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                        Log.d("mensaje", t.getMessage());

                                                    }

                                                }
                                            });
                                        }
                                        variableFinal = true;

                                    }

                                    *//*contador++;*//*

                                    //Aquí añadimos los datos a los arrays
                                    double changeInAcceleration1 = changeInAcceleration;
                                    float currentHeartRate1 = currentHeartRate;

                                *//*if(ejemplo.get(indice).get(1).equals("recreo")){
                                    //Cambiar variable para que el hilo pare.
                                    Intent intent=new Intent(Course.this,Recreo.class);
                                    startActivity(intent);
                                    contador=6;
                                }else{*//*


                                    movimientos.get(indice).add((int) changeInAcceleration1);

                                    if (currentHeartRate1 > lowerHeartRate && currentHeartRate1 < higherHeartRate) {
                                        ritmos.get(indice).add("SI");
                                    } else {
                                        ritmos.get(indice).add("NO");
                                    }
                                    Toast.makeText(Course.this, "AHORA", Toast.LENGTH_SHORT).show();

                                    situaciones(changeInAcceleration1, currentHeartRate1);
                                }
                                //Parte del recreo
                                else{

                                    recreoAux=ejemplo.get(indice);
                                    activoHilo1=false;

                                    if(variable){

                                        Toast.makeText(Course.this, "Va a empezar recreo", Toast.LENGTH_SHORT).show();

                                        new Hilo2().start();
                                    }
                                    variable=true;
                                }
                                *//*}*//*

                            }

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //Hilo recreo
        class Hilo2 extends Thread {
            @Override
            public void run() {
                while (activoHilo2) {
                    try {
                        Thread.sleep(30000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showSteps();
                                obtenerCoordenadas();

                                if(distance>10){
                                    recreoFinal.add("SI");
                                }else{
                                    recreoFinal.add("NO");
                                }

                                Toast.makeText(Course.this, "Hilo4", Toast.LENGTH_SHORT).show();
                                LocalTime ahora = LocalTime.now();
                                LocalTime fin = LocalTime.parse(recreoAux.get(4));

                                if(ahora.compareTo(fin)>0){
                                    activoHilo2=false;
                                    finalSteps=currentSteps;

                                    SharedPreferences.Editor myEdit = sh.edit();
                                    myEdit.putString("stepsFromPreference", String.valueOf(totalSteps));
                                    myEdit.commit();

                                    if(variableHola){
                                        activoHilo1=true;
                                        variableFin=false;
                                        Toast.makeText(Course.this, "recreo acabado", Toast.LENGTH_SHORT).show();
                                        stepSensorManager.unregisterListener(listenerStep);
                                        new Hilo1().start();
                                    }
                                    variableHola=true;
                                }else{
                                    Toast.makeText(Course.this, "recreo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/






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
            Thread hilo2=new Thread(new Hilo2(),"hilo2");

                movimientos=new ArrayList<>();
                ritmos=new ArrayList<>();
                for(int i=0;i<ejemplo.size();i++){
                    movimientos.add(new ArrayList<Integer>());
                    ritmos.add(new ArrayList<String>());
                }




            hilo1.start();




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

    public void detener(){
        activoHilo1 =false;
        Intent intent = new Intent(Course.this,MenuPrincipal.class);
        startActivity(intent);
    }


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

        Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

        TextView tv=popupView.findViewById(R.id.textViewPopUpPuntuacion);
        tv.setText(mensaje);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //***Aquí agregamos el proceso a ejecutar.
                popupWindow.dismiss();

                handler.removeCallbacks(null);
            }
        }, 5000 );//define el tiempo.

        /*popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });*/
    }

    public void popUpPuntuacion(String mensaje){

        View popupView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_puntuacion_final,null,false);
        final PopupWindow popupWindow=new PopupWindow(popupView,500,800,false);
        popupWindow.showAtLocation(textViewScore, Gravity.CENTER,0,0);

        TextView tv=popupView.findViewById(R.id.textViewPopUpPuntuacion);
        tv.setText(mensaje);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //***Aquí agregamos el proceso a ejecutar.
                popupWindow.dismiss();

                handler.removeCallbacks(null);
            }
        }, 10000 );//define el tiempo.


        /*popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });*/
    }

    public Data saveData(ArrayList<ArrayList<String>> ritmo,ArrayList<ArrayList<Integer>> movimiento) {
        int vecesCalmadoMovimiento = 0, vecesNerviosoMovimiento = 0, vecesCalmadoRitmo = 0, vecesNerviosoRitmo = 0;
        int umbral = 5;
        int movimientoSi=0,movimientoNo=0;
        ArrayList<DatosClase> listaDatosClase = new ArrayList<>();
        ArrayList<DatosClase> listaDatosRecreo = new ArrayList<>();
        Data datos = new Data();
        DatosClase datosClase = new DatosClase();
        DatosRecreo datosRecreo = new DatosRecreo();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = date.format(formatter);

        datos.setConfiguracionId(configuracionId);
        datos.setStudentId(studentId);
        datos.setPuntuacion(puntuacion);
        datos.setFecha(formattedString);
        for (int i = 0; i < movimiento.size(); i++) {
            for (int j = 0; j < movimiento.get(i).size(); j++) {
                if (movimiento.get(i).get(j) >= umbral) {
                    vecesNerviosoMovimiento++;
                }
                if(movimiento.get(i).get(j) < umbral){
                    vecesCalmadoMovimiento++;
                }

                if (ritmo.get(i).get(j).equals("SI")) {
                    vecesCalmadoRitmo++;
                }
                if(ritmo.get(i).get(j).equals("NO")){
                    vecesNerviosoRitmo++;
                }



            }
            int periodoId = Integer.parseInt(ejemplo.get(i).get(1));
            datosClase.setPeriodoId(periodoId);
            datosClase.setTotalIntervalosMovimiento(movimiento.get(i).size());
            datosClase.setTotalCalmadoMovimiento(vecesCalmadoMovimiento);
            datosClase.setTotalNerviosoMovimiento(vecesNerviosoMovimiento);

            datosClase.setTotalIntervalosRitmo(ritmo.get(i).size());
            datosClase.setTotalCalmadoRitmo(vecesCalmadoRitmo);
            datosClase.setTotalNerviosoRitmo(vecesNerviosoRitmo);


            vecesCalmadoMovimiento = 0;
            vecesNerviosoMovimiento = 0;
            vecesCalmadoRitmo = 0;
            vecesNerviosoRitmo = 0;


            listaDatosClase.add(datosClase);
            datosClase = new DatosClase();


        }
        for(int i=0;i<recreoFinal.size();i++){
            if(recreoFinal.get(i).equals("SI")){
                movimientoSi++;
            }else{
                movimientoNo++;
            }
        }
        datosRecreo.setSteps(currentSteps);
        datosRecreo.setPeriodoId(indicePeriodoRecreo);
        datosRecreo.setTotalMovimiento(movimientoSi);
        datosRecreo.setTotalNoMovimiento(movimientoNo);

        listaDatosClase.remove(indiceRecreo);
        datos.setDatosClase(listaDatosClase);
        datos.setDatosRecreo(datosRecreo);
        return datos;
    }

    //Hilo recreo
    /*class Hilo2 extends Thread {
        @Override
        public void run() {
            while (activoHilo2) {
                try {
                    Thread.sleep(30000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showSteps();
                            obtenerCoordenadas();

                            if(distance>10){
                                recreoFinal.add("SI");
                            }else{
                                recreoFinal.add("NO");
                            }

                            Toast.makeText(Course.this, "Hilo4", Toast.LENGTH_SHORT).show();
                            LocalTime ahora = LocalTime.now();
                            LocalTime fin = LocalTime.parse(recreoAux.get(4));

                            if(ahora.compareTo(fin)>0){
                                activoHilo2=false;
                                finalSteps=currentSteps;

                                SharedPreferences.Editor myEdit = sh.edit();
                                myEdit.putString("stepsFromPreference", String.valueOf(totalSteps));
                                myEdit.commit();

                                if(variableHola){
                                    activoHilo1=true;
                                    variableFin=false;
                                    Toast.makeText(Course.this, "recreo acabado", Toast.LENGTH_SHORT).show();
                                    stepSensorManager.unregisterListener(listenerStep);
                                    new Hilo1().start();
                                }
                                variableHola=true;
                            }else{
                                Toast.makeText(Course.this, "recreo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    private void showSteps() {
        stepSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        stepSensor = stepSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        stepSensorManager.registerListener(listenerStep, stepSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    SensorEventListener listenerStep = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            totalSteps = (int) sensorEvent.values[0];

            currentSteps=totalSteps-previousSteps;
            textViewPuntuacion.setText("Pasos: "+currentSteps);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void obtenerCoordenadas() {
        locationListener = new Course.MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
           /* Toast.makeText(getBaseContext(), "Cambio de localización", Toast.LENGTH_SHORT).show();
            lat.setText("Longitud funcion:" + location.getLongitude());
            lon.setText("Latitud funcion:" + location.getLatitude());
            currentLocation=location;

            textView.setText("Distancia: "+distance);
            previousLocation=currentLocation;*/

        } else {
            Toast.makeText(this, "sin localizacion", Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }


    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                // Toast.makeText(getBaseContext(), "Location changed : Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
               /* Toast.makeText(Prueba.this, "Cambio de localización", Toast.LENGTH_SHORT).show();
                lat.setText("Longitud onChange:" + loc.getLongitude());
                lon.setText("Latitud onChange:" + loc.getLatitude());*/



                String longitude = "Longitude: " + loc.getLongitude();
                Log.v(TAG, longitude);
                String latitude = "Latitude: " + loc.getLatitude();
                Log.v(TAG, latitude);
                currentLocation=loc;
                distance= (int)currentLocation.distanceTo(previousLocation);
                /*textView.setText("Distancia: "+distance);*/
                previousLocation=currentLocation;
               /* lat.setText("Longitud onChange:" + loc.getLongitude());
                lon.setText("Latitud onChange:" + loc.getLatitude());*/

                /*----------to get City-Name from coordinates ------------- */
                String cityName = null;
                Geocoder gcd = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(loc.getLatitude(), loc
                            .getLongitude(), 1);
                    if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            /*String s = longitude + "\n" + latitude +
                    "\n\nMy Currrent City is: " + cityName;
            textViewCiudad.setText(s);*/
            } else {
                if (ActivityCompat.checkSelfPermission(Course.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Course.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 00, locationListener);

            }


        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }


        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    class Hilo1 extends Thread {
        @Override
        public void run() {
            while (activoHilo1) {
                try {
                    Thread.sleep(30000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int indice=getIndice(ejemplo);
                            LocalTime ahora = LocalTime.now();
                            LocalTime fin = LocalTime.parse(ejemplo.get(indice).get(4));

                            if(!ejemplo.get(indice).get(0).equals("recreo")){
                                if (indice == (ejemplo.size() - 1)) {
                                    variable1 = true;
                                }
                                ApiService service= Api.getApi().create(ApiService.class);

                                //Condicion de finalización
                                if (variable1 && indice == 0){
                                    activoHilo1 = false;
                                    if (variableFinal) {
                                        //Se debe hacer el popUp y modificar la variable del while del hilo para que pare
                                        popUpPuntuacion("Tu puntuación ha sido: " + puntuacion);
                                        datosNuevos = saveData(ritmos, movimientos);
                                        datosNuevos.getStudentId();

                                        Call<Data> dataCall = service.postConfigurationData(datosNuevos);
                                        dataCall.enqueue(new Callback<Data>() {
                                            @Override
                                            public void onResponse(Call<Data> call, Response<Data> response) {
                                                if (response.isSuccessful()) {

                                                    Toast.makeText(Course.this, "El post se hizo correctamente", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Course.this,MenuPrincipal.class);
                                                    startActivity(intent);

                                                } else {

                                                    Toast.makeText(Course.this, "Error:" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                                    Log.d("mensaje", response.errorBody().toString());

                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<Data> call, Throwable t) {
                                                if (t instanceof IOException) {
                                                    Toast.makeText(Course.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                                                    Log.d("mensaje", t.getMessage());
                                                    t.printStackTrace();
                                                } else {
                                                    Toast.makeText(Course.this, "Problema de conversión", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(Course.this, "ee: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Log.d("mensaje", t.getMessage());

                                                }

                                            }
                                        });
                                    }
                                    variableFinal = true;

                                }

                                /*contador++;*/

                                //Aquí añadimos los datos a los arrays
                                double changeInAcceleration1 = changeInAcceleration;
                                float currentHeartRate1 = currentHeartRate;

                                /*if(ejemplo.get(indice).get(1).equals("recreo")){
                                    //Cambiar variable para que el hilo pare.
                                    Intent intent=new Intent(Course.this,Recreo.class);
                                    startActivity(intent);
                                    contador=6;
                                }else{*/


                                movimientos.get(indice).add((int) changeInAcceleration1);

                                if (currentHeartRate1 > lowerHeartRate && currentHeartRate1 < higherHeartRate) {
                                    ritmos.get(indice).add("SI");
                                } else {
                                    ritmos.get(indice).add("NO");
                                }
                                Toast.makeText(Course.this, "AHORA", Toast.LENGTH_SHORT).show();

                                situaciones(changeInAcceleration1, currentHeartRate1);
                            }
                            //Parte del recreo
                            else{
                                indiceRecreo=indice;
                                indicePeriodoRecreo= Integer.parseInt(ejemplo.get(indice).get(1));

                                recreoAux=ejemplo.get(indice);
                                activoHilo1=false;

                                if(variable){

                                    Toast.makeText(Course.this, "Va a empezar recreo", Toast.LENGTH_SHORT).show();

                                    new Hilo2().start();
                                }
                                variable=true;
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

    //Hilo recreo
    class Hilo2 extends Thread {
        @Override
        public void run() {
            while (activoHilo2) {
                try {
                    Thread.sleep(30000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showSteps();
                            obtenerCoordenadas();

                            if(distance>10){
                                recreoFinal.add("SI");
                            }else{
                                recreoFinal.add("NO");
                            }

                            Toast.makeText(Course.this, "Hilo4", Toast.LENGTH_SHORT).show();
                            LocalTime ahora = LocalTime.now();
                            LocalTime fin = LocalTime.parse(recreoAux.get(4));

                            if(ahora.compareTo(fin)>0){
                                activoHilo2=false;
                                finalSteps=currentSteps;

                                SharedPreferences.Editor myEdit = sh.edit();
                                myEdit.putString("stepsFromPreference", String.valueOf(totalSteps));
                                myEdit.commit();

                                if(variableHola){
                                    activoHilo1=true;
                                    variableFin=false;
                                    Toast.makeText(Course.this, "recreo acabado", Toast.LENGTH_SHORT).show();
                                    stepSensorManager.unregisterListener(listenerStep);
                                    new Hilo1().start();
                                }
                                variableHola=true;
                            }else{
                                Toast.makeText(Course.this, "recreo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}