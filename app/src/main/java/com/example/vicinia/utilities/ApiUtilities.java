package com.example.vicinia.utilities;

import com.example.vicinia.clients.ApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

   private final static String BACKEND_BASE_URL = "https://vicinia.herokuapp.com";
    // private final static String BACKEND_BASE_URL = "http://10.0.2.2:8080";

    private static Retrofit retrofit = null;

    public static ApiInterface getClient(){
        return getRetrofitClient().create(ApiInterface.class);
    }

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    private static Retrofit getRetrofitClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BACKEND_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}