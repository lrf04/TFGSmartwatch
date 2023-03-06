package com.example.tfgsmartwatch.API;



import com.example.tfgsmartwatch.models.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/courses/{course}")
    Call<List<Alumno>> getStudents(@Path("course") int course);

    @GET("api/students/{id}")
    Call<Alumno> getStudent(@Path("id") int id);
}
