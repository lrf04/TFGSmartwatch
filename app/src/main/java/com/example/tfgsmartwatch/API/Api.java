package com.example.tfgsmartwatch.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    //public static final String BASE_URL="http://tfg_test/";
    public static final String BASE_URL="http://192.168.68.108/"; //192.168.68.108   10.0.2.2
    private static Retrofit retrofit = null;

    public static Retrofit getApi(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
