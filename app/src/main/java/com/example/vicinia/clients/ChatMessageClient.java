package com.example.vicinia.clients;

import android.os.AsyncTask;

import com.example.vicinia.MainActivity;
import com.example.vicinia.pojos.HttpRequest;
import com.example.vicinia.pojos.HttpResponse;
import com.example.vicinia.utilities.DialogUtilities;
import com.example.vicinia.utilities.UrlUtilities;

import org.json.*;

import static com.example.vicinia.services.ChatMessageServices.onChatResponse;
import static com.example.vicinia.services.ChatMessageServices.onDetailsResponse;
import static com.example.vicinia.services.ChatMessageServices.onWelcomeResponse;
import static com.example.vicinia.utilities.HttpUtilities.httpGET;
import static com.example.vicinia.utilities.HttpUtilities.httpPOST;

public class ChatMessageClient {
    public static class ApiGetTask extends AsyncTask<HttpRequest, Void, HttpResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected HttpResponse doInBackground(HttpRequest... params) {
            HttpRequest httpRequest = params[0];
            return httpGET(httpRequest);
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            onGetResponse(response);
        }
    }

    private static void onGetResponse(HttpResponse response){
        int responseStatus = response.getStatusCode();

        if(responseStatus == 200){
            UrlUtilities.API_METHODS responseMethod = response.getMethod();
            JSONObject responseBody = response.getJsonObject();

            switch (responseMethod){
                case GET_WELCOME:
                    onWelcomeResponse(responseBody);
                    break;
                case GET_DETAILS:
                    onDetailsResponse(responseBody);
                    break;
                default:
            }
        }else{
            DialogUtilities.internetErrorDialogBuider(MainActivity.getInstance());
        }
    }

    public static class ApiPostTask extends AsyncTask<HttpRequest, Void, HttpResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected HttpResponse doInBackground(HttpRequest... params) {
            HttpRequest httpRequest = params[0];
            return httpPOST(httpRequest);
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            onPostResponse(response);
        }
    }

    private static void onPostResponse(HttpResponse response) {
        int responseStatus = response.getStatusCode();
        if(responseStatus == 200){
            UrlUtilities.API_METHODS responseMethod = response.getMethod();
            JSONObject responseBody = response.getJsonObject();
            switch (responseMethod){
                case POST_CHAT:
                    onChatResponse(responseBody);
                    break;
                default:
            }
        }else{
            DialogUtilities.internetErrorDialogBuider(MainActivity.getInstance());
        }
    }
}
