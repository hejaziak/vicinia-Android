package com.example.vicinia.services;

import android.util.Log;

import com.example.vicinia.MainActivity;
import com.example.vicinia.activities.SplashActivity;
import com.example.vicinia.utilities.ApiUtilities;
import com.example.vicinia.models.ApiMessage;
import com.example.vicinia.models.Place;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServices {
    private static final String TAG = "VICINIA/ApiServices";

    public static void getWelcome(final SplashActivity splashActivity) {
        ApiUtilities.getClient().getWelcome().enqueue(new Callback<ApiMessage>() {
            @Override
            public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response) {
                if (response.isSuccessful()) {
                    ApiMessage welcomeMessage = response.body();

                    String uuid = welcomeMessage.getUuid();
                    String message = welcomeMessage.getMessage();
                    splashActivity.onLoadingFinish(uuid, message);
                }
            }

            @Override
            public void onFailure(Call<ApiMessage> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public static void getDetails(final MainActivity mainActivity, String uuid, String placeID, double lat, double lng) {
        ApiUtilities.getClient().getDetails(uuid, placeID, lat, lng).enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                if (response.isSuccessful()) {
                    Place place = response.body();

                    String name = place.getName();
                    String distance = place.getDistance();
                    float rating = place.getRating();
                    String type = place.getType();
                    String address = place.getAddress();
                    String mobileNumber = place.getMobileNumber();
                    String link = place.getLink();

                    String message = "";
                    message += "<b>Name: </b>" + name + "<br>";
                    message += "<b>Distance: </b>" + distance + "<br>";
                    message += "<b>Rating: </b>" + rating + "<br>";
                    message += "<b>Type: </b>" + type + "<br>";
                    message += "<b>Address: </b>" + address + "<br>";
                    message += "<b>Mobile Number: </b>" + mobileNumber + "<br/><br/>";
                    message += "<b><font color=\"#1C78C6\"><a href=\"" + link + "\">Open in Google Maps</a></font></b>";

                    mainActivity.onReceiveMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public static void postChat(final MainActivity mainActivity, String uuid, String lat, String lng, String message) {
        ApiMessage chatMessage = new ApiMessage(lat, lng, message);

        ApiUtilities.getClient().postChat(uuid, chatMessage).enqueue(new Callback<ApiMessage>() {
            @Override
            public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response) {
                if (response.isSuccessful()) {
                    ApiMessage message = response.body();

                    if(message.getList() != null){
                        Gson gson = new Gson();
                        message.setMessage(gson.toJson(message.getList()));
                    }

                    System.out.println(message.getMessage());
                    mainActivity.onReceiveMessage(message.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiMessage> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
