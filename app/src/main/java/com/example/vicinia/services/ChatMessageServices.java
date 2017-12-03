package com.example.vicinia.services;

import android.util.Log;

import com.example.vicinia.MainActivity;
import com.example.vicinia.activities.SplashActivity;
import com.example.vicinia.client.ChatMessageClient;
import com.example.vicinia.pojos.HttpRequest;
import com.example.vicinia.utilities.UrlUtilities;

import org.json.*;
import java.net.*;

import static com.example.vicinia.utilities.UrlUtilities.buildChatUrl;
import static com.example.vicinia.utilities.UrlUtilities.buildDetailsUrl;
import static com.example.vicinia.utilities.UrlUtilities.buildWelcomeUrl;

public class ChatMessageServices {
    private static final String TAG = "VICINITA/ChatServices";

    public static void getWelcome(){
        URL requestURL = buildWelcomeUrl();
        UrlUtilities.API_METHODS requestMethod = UrlUtilities.API_METHODS.GET_WELCOME;

        HttpRequest httpRequest = new HttpRequest(requestURL, requestMethod);

        new ChatMessageClient.ApiGetTask().execute(httpRequest);
    }

    public static void onWelcomeResponse(JSONObject response){
        try{
            String uuid = response.getString("uuid");
            String message = response.getString("message");

            Log.v(TAG, "UUID: "+uuid);

            SplashActivity splashActivity = SplashActivity.getInstance();
            splashActivity.onLoadingFinish(uuid, message);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void sendChatMessage(String message, double lattitude, double longitude){
        URL requestURL = buildChatUrl();
        UrlUtilities.API_METHODS requestMethod = UrlUtilities.API_METHODS.POST_CHAT;
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("message", message);
            requestBody.put("latitude", Double.toString(lattitude));
            requestBody.put("longitude", Double.toString(longitude));
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            e.printStackTrace();
        }

        HttpRequest httpRequest = new HttpRequest(requestURL, requestMethod, requestBody);

        new ChatMessageClient.ApiPostTask().execute(httpRequest);
    }

    public static void onChatResponse(JSONObject response){
        try{
            MainActivity mainActivity = MainActivity.getInstance();
            String message = response.getString("message");
            mainActivity.onReceiveMessage(message);

            Log.v(TAG, "Message: "+message);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void getDetails(String placeID, double lat, double lang){
        URL requestURL = buildDetailsUrl(placeID, lat, lang);
        UrlUtilities.API_METHODS requestMethod = UrlUtilities.API_METHODS.GET_DETAILS;

        HttpRequest httpRequest = new HttpRequest(requestURL, requestMethod);

        new ChatMessageClient.ApiGetTask().execute(httpRequest);
    }

    public static void onDetailsResponse(JSONObject response){
        try{
            MainActivity mainActivity = MainActivity.getInstance();

            String name = response.getString("name");
            String distance = response.getString("distance");
            String rating = response.getString("rating");
            String type = response.getString("type");
            String address = response.getString("address");
            String mobileNumber = response.getString("mobile_number");
            String link = response.getString("link");

            String message = "";
            message += name + "<br>";
            message += distance + "<br>";
            message += rating + "<br>";
            message += type + "<br>";
            message += address + "<br>";
            message += mobileNumber + "<br>";
            message += link;

            mainActivity.onReceiveMessage(message);
            Log.v(TAG, "Message: "+message);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

}
