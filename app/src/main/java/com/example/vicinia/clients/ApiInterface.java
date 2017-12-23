package com.example.vicinia.clients;

import com.example.vicinia.pojos.Message;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {

    @GET("/welcome")
    Call<Message> getWelcome();
}
