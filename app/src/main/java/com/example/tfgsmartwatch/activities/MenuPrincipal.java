package com.example.tfgsmartwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgsmartwatch.API.Api;
import com.example.tfgsmartwatch.API.ApiService;
import com.example.tfgsmartwatch.R;
import com.example.tfgsmartwatch.databinding.ActivityMainBinding;
import com.example.tfgsmartwatch.models.Alumno;
import com.example.tfgsmartwatch.models.Period;
import com.example.tfgsmartwatch.models.Subject;
import com.example.tfgsmartwatch.utils.util;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button button;
    private EditText hora;
    private Time horaActual;
    private TextView tv1;
    private String name;
    private List<Period> periods;
    private List<List<Period>> periodos=new ArrayList<>();
    private List<String> nombres=new ArrayList<>();
    private List<String> horas=new ArrayList<>();
    private List<String> horasAux=new ArrayList<>();
    private List<String> nuevo=new ArrayList<>();
    private List<Integer> numeros=new ArrayList<>();
    private List<String> resultados=new ArrayList<>();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        bindUI();

        //SharedPreferences
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String id1= util.getUserIdPrefs(prefs);
        int id=Integer.parseInt(id1);

        //Retrofit
        ApiService service= Api.getApi().create(ApiService.class);
        Call<Alumno> alumnoCall=service.getStudent(id);
        Call<List<Subject>> periodsCall=service.getPeriods(id);

        /*alumnoCall.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.isSuccessful()){
                    Alumno alumno=response.body();
                   // Toast.makeText(MenuPrincipal.this, "El servidor retornó datos", Toast.LENGTH_SHORT).show();

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
        });*/

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

                    /*for(int i=0;i<subjects.size();i++){
                        nuevo.add(subjects.get(i).getName());

                        for(int u=0;u<6;u++){
                            subjects.get(i).get(u).
                        }
                        for(int j=0;i<periodos.size();j++){
                                horas.add(periodos.get(i).get(j).getTime());
                                horas.add(periodos.get(i).get(j).getTimeFinish());

                        }

                    }*/


                    for(int i=0;i<periodos.size();i++){
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
                            /*horasAux.remove(j);*/
                            w=w+1;
                            if(w%2==0 && j!=(v-1)){
                                resultados.add(nombres.get(i));
                            }

                        }
                        for(int p=0;p<v;p++){
                            horasAux.remove(0);
                        }
                    }

                   for(int i=0;i<resultados.size();i=i+3){
                       if(resultados.get(i).equals("lengua")){
                           /*Intent intent=new Intent(MenuPrincipal.this,Feedback.class);
                           startActivity(intent);*/

                           DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                           try {
                               Date time = dateFormat.parse(resultados.get(i+1));
                               //java.sql.Time timeValue = new java.sql.Time(dateFormat.parse(resultados.get(i+1)).getTime());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }





                       }
                   }







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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuPrincipal.this, course.class);
                startActivity(intent);
            }
        });



    }

    private void bindUI(){
        button=(Button) findViewById(R.id.button);
        tv1=(TextView) findViewById(R.id.textViewBienvenida);
    }

    private void logOut(){
        Intent intent=new Intent(MenuPrincipal.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}