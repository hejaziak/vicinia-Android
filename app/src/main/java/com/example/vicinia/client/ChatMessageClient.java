package com.example.vicinia.client;

import android.os.AsyncTask;

import com.example.vicinia.MainActivity;

import org.json.*;

import java.net.*;

import static com.example.vicinia.services.ChatMessageServices.onChatResponse;
import static com.example.vicinia.services.ChatMessageServices.onDetailsResponse;
import static com.example.vicinia.services.ChatMessageServices.onWelcomeResponse;
import static com.example.vicinia.utilities.HttpUtilities.httpGET;
import static com.example.vicinia.utilities.HttpUtilities.httpPOST;

public class ChatMessageClient {
    public static class ApiGetTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL requestURL = params[0];
            return httpGET(requestURL);
        }

        @Override
        protected void onPostExecute(String response) {
            onGetResponse(response);
        }
    }

    private static void onGetResponse(String response){
        try{
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.length() < 3)   //WELCOME RESPONSE
                onWelcomeResponse(jsonResponse);
            else                            //DETAILS RESPONSE
                onDetailsResponse(jsonResponse);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static class ApiPostTask extends AsyncTask<Object[], Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object[]... params) {
            Object[] objs = params[0];

            URL requestURL = (objs[1] instanceof URL ? (URL) objs[0] : null);
            JSONObject requestBody = (objs[1] instanceof JSONObject ? (JSONObject) objs[1] : null);

            if (requestURL != null && requestBody != null)
                return httpPOST(requestURL, requestBody);
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            onPostResponse(response);
        }
    }

    private static void onPostResponse(String response) {
        try{
            JSONObject jsonResponse = new JSONObject(response);
            onChatResponse(jsonResponse);
        }
        catch (JSONException e){
            e.printStackTrace();
        }    }
}
