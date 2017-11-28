package com.example.vicinia.services;

import android.util.Log;

import com.example.vicinia.MainActivity;
import com.example.vicinia.client.ChatMessageClient;

import org.json.*;
import java.net.*;

import static com.example.vicinia.utilities.UrlUtilities.buildChatUrl;
import static com.example.vicinia.utilities.UrlUtilities.buildDetailsUrl;
import static com.example.vicinia.utilities.UrlUtilities.buildWelcomeUrl;

public class ChatMessageServices {
    private static final String TAG = "VICINITA/ChatServices";

    public static void getWelcome(){
        URL url = buildWelcomeUrl();
        new ChatMessageClient.ApiGetTask().execute(url);
    }

    public static void onWelcomeResponse(JSONObject response){
        try{
            MainActivity mainActivity = MainActivity.getInstance();
            mainActivity.uuid = response.getString("uuid");

            Log.v(TAG, "UUID: "+mainActivity.uuid);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void sendChatMessage(String message, double lattitude, double longitude){
        URL url = buildChatUrl();
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("message", message);
            requestBody.put("latitude", lattitude);
            requestBody.put("longitude", longitude);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            e.printStackTrace();
        }

        Object[] postParams = {url, requestBody};
        new ChatMessageClient.ApiPostTask().execute(postParams);
    }

    public static void onChatResponse(JSONObject response){

    }

    public static void getDetails(String placeID){
        URL url = buildDetailsUrl(placeID);
        new ChatMessageClient.ApiGetTask().execute(url);
    }

    public static void onDetailsResponse(JSONObject response){

    }

}
