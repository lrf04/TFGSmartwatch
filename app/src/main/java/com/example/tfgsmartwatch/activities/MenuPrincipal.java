package com.example.tfgsmartwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgsmartwatch.API.Api;
import com.example.tfgsmartwatch.API.ApiService;
import com.example.tfgsmartwatch.R;
import com.example.tfgsmartwatch.models.Alumno;
import com.example.tfgsmartwatch.models.Period;
import com.example.tfgsmartwatch.models.Subject;
import com.example.tfgsmartwatch.utils.util;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity {

    private Button buttonClase, buttonRecreo;
    private EditText hora;
    private Time horaActual;
    private TextView tv1;
    private String name,dayName,diaActual;
    private List<Period> periods;
    private List<List<Period>> periodos=new ArrayList<>();
    private List<String> nombres=new ArrayList<>();
    private List<String> horas=new ArrayList<>();
    private List<String> horasAux=new ArrayList<>();
    private List<String> nuevo=new ArrayList<>();
    private List<Integer> numeros=new ArrayList<>();
    public ArrayList<String> resultados=new ArrayList<>();
    public ArrayList<ArrayList<String>> resultadosNuevos=new ArrayList<>();
    public ArrayList<ArrayList<String>> resultadosNuevosDia=new ArrayList<>();
    public ArrayList<ArrayList<String>> ejemplo=new ArrayList<>();
    public ArrayList<ArrayList<String>> aux1=new ArrayList<>();
    public ArrayList<String> aux=new ArrayList<>();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        bindUI();
        buttonClase.setEnabled(false);


        //SharedPreferences
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String id1= util.getUserIdPrefs(prefs);
        int id=Integer.parseInt(id1);

        //Retrofit
        ApiService service= Api.getApi().create(ApiService.class);
        Call<Alumno> alumnoCall=service.getStudent(id);
        Call<List<Subject>> periodsCall=service.getPeriods(id);

        alumnoCall.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno alumno=response.body();
                   /*Toast.makeText(MenuPrincipal.this, "El servidor retornó datos", Toast.LENGTH_SHORT).show();*/

                    tv1.setText("Bienvenido "+alumno.getName());

                }else{
                    Toast.makeText(MenuPrincipal.this, "El servidor retornó un error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                if(t instanceof IOException){
                    Toast.makeText(MenuPrincipal.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }else{
                    Toast.makeText(MenuPrincipal.this, "Problema de conversión", Toast.LENGTH_SHORT).show();
                }

            }
        });

        periodsCall.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if(response.isSuccessful()){
                    List<Subject> subjects=response.body();
                    Toast.makeText(MenuPrincipal.this, "El servidor retornó datos", Toast.LENGTH_SHORT).show();
                    for (int i=0;i<subjects.size();i++) {
                        Log.d("name"+i,String.valueOf(subjects.get(i).getName()));
                        periods=subjects.get(i).getPeriods();
                        periodos.add(periods);
                        nombres.add(subjects.get(i).getName());
                    }



                    //Funciona (versión sin día)
                    /*for(int i=0;i<periodos.size();i++){
                       int z=periodos.get(i).size();
                       numeros.add(z);
                        for(int j=0;j<z;j++){
                            horas.add(periodos.get(i).get(j).getTime());
                            horas.add(periodos.get(i).get(j).getTimeFinish());
                        }
                    }

                    horasAux=horas;
                    for(int i=0;i<nombres.size();i++){
                        resultados.add(nombres.get(i));
                        int z=numeros.get(i);
                        int v=2*z;
                        int w=0;


                        for(int j=0;j<v;j++){
                            resultados.add(horasAux.get(j));

                            w=w+1;
                            if(w%2==0 && j!=(v-1)){
                                resultados.add(nombres.get(i));
                            }

                        }
                        for(int p=0;p<v;p++){
                            horasAux.remove(0);
                        }
                    }*/

                    for(int i=0;i<periodos.size();i++){
                        int z=periodos.get(i).size();
                        numeros.add(z);
                        for(int j=0;j<z;j++){
                            //cambio
                            int valor=periodos.get(i).get(j).getId();
                            String variable=String.valueOf(valor);
                            horas.add(variable);

                            horas.add(periodos.get(i).get(j).getDay());
                            horas.add(periodos.get(i).get(j).getTime());
                            horas.add(periodos.get(i).get(j).getTimeFinish());
                        }
                    }

                    horasAux=horas;
                    for(int i=0;i<nombres.size();i++){
                        resultados.add(nombres.get(i));
                        int z=numeros.get(i);
                        //Cambio
                        int v=4*z;
                        /*int v=3*z;*/
                        int w=0;


                        for(int j=0;j<v;j++){
                            resultados.add(horasAux.get(j));

                            w=w+1;
                            //cambio
                            if(w%4==0 && j!=(v-1)){
                                resultados.add(nombres.get(i));
                            }
                            /*if(w%3==0 && j!=(v-1)){
                                resultados.add(nombres.get(i));
                            }*/

                        }
                        for(int p=0;p<v;p++){
                            horasAux.remove(0);
                        }
                    }

                    if(resultados.isEmpty()){

                    }




                    resultadosNuevos=split(5,resultados);

                    /*ejemplo=ordenar(resultadosNuevos);
                    if(ejemplo.isEmpty()){

                    }*/




                    for(int i=0;i<resultadosNuevos.size();i++){
                        for(int j=0;j<5;j=j+5){
                            if(!resultadosNuevos.get(i).get(j).equals("recreo")){
                                diaActual=resultadosNuevos.get(i).get(j+2);
                                LocalTime timeValue = LocalTime.parse(resultadosNuevos.get(i).get(j+3));
                                LocalTime timeValue1 = LocalTime.parse(resultadosNuevos.get(i).get(j+4));
                                LocalTime ahora = LocalTime.now();

                                dayName=LocalDate.now().getDayOfWeek().name();

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

                                if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual)){

                                    buttonClase.setEnabled(true);

                                }


                            }else{
                                LocalTime timeValue = LocalTime.parse(resultadosNuevos.get(i).get(j+3));
                                LocalTime timeValue1 = LocalTime.parse(resultadosNuevos.get(i).get(j+4));
                                LocalTime ahora = LocalTime.now();

                                dayName=LocalDate.now().getDayOfWeek().name();

                                if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual) ){



                                }

                            }
                        }
                    }





                    //Para array de string
                   /*for(int i=0;i<resultados.size();i=i+4){
                       if(!resultados.get(i).equals("recreo")){

                           diaActual=resultados.get(i+1);
                           LocalTime timeValue = LocalTime.parse(resultados.get(i+2));
                           LocalTime timeValue1 = LocalTime.parse(resultados.get(i+3));
                           LocalTime ahora = LocalTime.now();

                           *//*LocalDate date = LocalDate.now();
                           DayOfWeek dow = date.getDayOfWeek();
                           String dayName = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);*//*
                           dayName=LocalDate.now().getDayOfWeek().name();

                           switch (dayName){
                               case "MONDAY":
                                   dayName="lunes";
                                   break;
                               case "TUESDAY":
                                   dayName="martes";
                                   break;
                               case "WEDNESDAY":
                                   dayName="miércoles";
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

                           if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual)){
                               buttonClase.setEnabled(true);






                           }


                       }else{
                           LocalTime timeValue = LocalTime.parse(resultados.get(i+2));
                           LocalTime timeValue1 = LocalTime.parse(resultados.get(i+3));
                           LocalTime ahora = LocalTime.now();
                           if(ahora.compareTo(timeValue)>0 && ahora.compareTo(timeValue1)<0 && dayName.equals(diaActual) ){
                               buttonRecreo.setEnabled(true);


                           }

                       }
                   }*/







                }else{
                    Toast.makeText(MenuPrincipal.this, "El servidor retornó un error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                if(t instanceof IOException){
                    Toast.makeText(MenuPrincipal.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }else{
                    Toast.makeText(MenuPrincipal.this, "Problema de conversión", Toast.LENGTH_SHORT).show();
                }

            }
        });



        //Prueba-> Botón para ir a la actividad de clase
        buttonClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MenuPrincipal.this, Course.class);
                intent.putStringArrayListExtra("resultados",resultados);
                startActivity(intent);

            }
        });



    }

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

        LocalTime timeValue = LocalTime.parse(menor.get(2));
        /*for(int i=0;i<resultadosNuevos.size();i++){*/
        for(int i=0;i<array.size();i++){
            for(int j=0;j<4;j=j+4) {
                LocalTime timeValue1 = LocalTime.parse(array.get(i).get(j+2));
                LocalTime ahora = LocalTime.now();


                    if(timeValue1.compareTo(timeValue)<0){
                        posicion=i;
                        timeValue = LocalTime.parse(array.get(i).get(j+2));

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



    public static Timestamp convertStringToTimestamp(String str_date, String pattern) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date = formatter.parse(str_date);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;

        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    private void bindUI(){
        buttonClase =(Button) findViewById(R.id.buttonClase);
        tv1=(TextView) findViewById(R.id.textViewBienvenida);
    }

    private void logOut(){
        Intent intent=new Intent(MenuPrincipal.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void popUp(String mensaje){

        View popupView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_validar,null,false);
        final PopupWindow popupWindow=new PopupWindow(popupView,500,800,false);
        popupWindow.showAtLocation(buttonClase, Gravity.CENTER,0,0);

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
        }, 2000 );//define el tiempo.

        /*popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });*/
    }

}