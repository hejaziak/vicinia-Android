package com.example.vicinia.client;

import android.os.AsyncTask;

import org.json.*;

import java.net.*;

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
            getResponse(response);
        }
    }

    private static String getResponse(String response){
        return response;
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
            postResponse(response);
        }
    }

    private static String postResponse(String response) {
        return response;
    }
}
