package com.example.map;


import android.util.Log;

import com.example.map.DataClasses.ResponseClass;
import com.example.map.DataClasses.ResponseErrorClass;
import com.example.map.DataClasses.UserParameter;
import com.example.map.ServicesData.ServicesDataClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class RetrofitManager {
    static Retrofit retrofit=null;
    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl("http://ec2-18-185-40-115.eu-central-1.compute.amazonaws.com:8800/api/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;

    }

    interface getResponseInterface{
        @POST("auth/signIn")
        Call<ResponseErrorClass> getResponseError(@Body UserParameter parameter);
        @POST("auth/signIn")
        Call<ResponseClass> getResponse(@Body UserParameter parameter);
        @GET("vendor/getServices")
        Call<List<ServicesDataClass>>getServicesResponse(@Header("Authorization") String bearer_token);
    }
}
