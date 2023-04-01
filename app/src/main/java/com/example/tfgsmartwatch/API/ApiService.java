package com.example.tfgsmartwatch.API;



import android.content.res.Configuration;

import com.example.tfgsmartwatch.models.Alumno;
import com.example.tfgsmartwatch.models.ConfigurationStudent;
import com.example.tfgsmartwatch.models.Data;
import com.example.tfgsmartwatch.models.DatosClase;
import com.example.tfgsmartwatch.models.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/courses/{course}")
    Call<List<Alumno>> getStudents(@Path("course") int course);

    @GET("api/students/{id}")
    Call<Alumno> getStudent(@Path("id") int id);

    @GET("api/subjects/{id}")
    Call<List<Subject>> getPeriods(@Path("id") int id);

    @GET("api/configurations/{id}")
    Call<ConfigurationStudent> getConfiguration(@Path("id") int id);

    @POST("api/data/configuration")
    Call<Data> postConfigurationData(@Body Data datosNuevos);

}
