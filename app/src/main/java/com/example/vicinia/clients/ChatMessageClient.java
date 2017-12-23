package com.example.vicinia.clients;

import android.os.AsyncTask;

import com.example.vicinia.MainActivity;
import com.example.vicinia.pojos.HttpRequest;
import com.example.vicinia.pojos.HttpResponse;
import com.example.vicinia.pojos.Message;
import com.example.vicinia.pojos.Place;
import com.example.vicinia.services.ChatMessageServices;
import com.example.vicinia.utilities.DialogUtilities;
import com.example.vicinia.utilities.UrlUtilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import static com.example.vicinia.services.ChatMessageServices.onChatResponse;
import static com.example.vicinia.services.ChatMessageServices.onDetailsResponse;
import static com.example.vicinia.services.ChatMessageServices.onWelcomeResponse;
import static com.example.vicinia.utilities.HttpUtilities.httpGET;
import static com.example.vicinia.utilities.HttpUtilities.httpPOST;

public class ChatMessageClient {

    /**
     * independent task to perform get requests
     */
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

    /**
     * called whenever a get response is received
     * @param response result from get request
     *
     * @called_from: {@link ApiGetTask#onPostExecute(HttpResponse)}
     * @calls: {@link ChatMessageServices#onDetailsResponse(JSONObject)}
     *         {@link ChatMessageServices#onWelcomeResponse(Message)}
     */
    private static void onGetResponse(HttpResponse response) {
        int responseStatus = response.getStatusCode();

        if (responseStatus >= HttpURLConnection.HTTP_OK && responseStatus < HttpURLConnection.HTTP_MULT_CHOICE) {
            UrlUtilities.API_METHODS responseMethod = response.getMethod();
            JSONObject responseBody = response.getJsonObject();

            Gson gson = new GsonBuilder().create();
            switch (responseMethod) {
                case GET_WELCOME:
                    Message uuidMessage = gson.fromJson(responseBody.toString(), Message.class);
                    onWelcomeResponse(uuidMessage);
                    break;
                case GET_DETAILS:
                    Place detailsMessage = gson.fromJson(responseBody.toString(), Place.class);
                    onDetailsResponse(detailsMessage);
                    break;
                default:
            }
        } else {
            DialogUtilities.internetErrorDialogBuider(MainActivity.getInstance());
        }
    }

    /**
     * independent task to perform get requests
     */
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

    /**
     * called whenever a post response is received
     * @param response result from post request
     *
     * @called_from: {@link ApiPostTask#onPostExecute(HttpResponse)}
     * @calls: {@link ChatMessageServices#onChatResponse(JSONObject)}
     */
    private static void onPostResponse(HttpResponse response) {
        int responseStatus = response.getStatusCode();

        if (responseStatus >= HttpURLConnection.HTTP_OK && responseStatus < HttpURLConnection.HTTP_MULT_CHOICE) {
            UrlUtilities.API_METHODS responseMethod = response.getMethod();
            JSONObject responseBody = response.getJsonObject();
            switch (responseMethod) {
                case POST_CHAT:
                    onChatResponse(responseBody);
                    break;
                default:
            }
        } else {
            DialogUtilities.internetErrorDialogBuider(MainActivity.getInstance());
        }
    }
}
