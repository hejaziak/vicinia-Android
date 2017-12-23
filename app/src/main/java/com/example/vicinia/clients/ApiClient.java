package com.example.vicinia.clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private final static String BACKEND_BASE_URL = "https://vicinia.herokuapp.com";
    private static Retrofit retrofit = null;

    public static ApiInterface getClient(){
        return getRetrofitClient().create(ApiInterface.class);
    }

    private static Retrofit getRetrofitClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BACKEND_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}