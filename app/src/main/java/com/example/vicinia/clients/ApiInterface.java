package com.example.vicinia.clients;

import com.example.vicinia.models.ApiMessage;
import com.example.vicinia.models.Place;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/welcome")
    Call<ApiMessage> getWelcome();

    @GET("/placeDetails/{placeID}/{latitude}/{longitude}")
    Call<Place> getDetails(
            @Header("Authorization") String uuid,
            @Path("placeID") String placeID,
            @Path("latitude") double latitude,
            @Path("longitude") double longitude
    );

    @POST("/chat")
    Call<ApiMessage> postChat(
            @Header("Authorization") String uuid,
            @Body ApiMessage message
    );
}
