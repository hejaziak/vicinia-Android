package com.example.vicinia.services;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vicinia.MainActivity;
import com.example.vicinia.activities.SplashActivity;
import com.example.vicinia.clients.ChatMessageClient;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.fragments.QuickActionFragment;
import com.example.vicinia.pojos.HttpRequest;
import com.example.vicinia.pojos.HttpResponse;
import com.example.vicinia.utilities.UrlUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import static com.example.vicinia.utilities.UrlUtilities.buildChatUrl;
import static com.example.vicinia.utilities.UrlUtilities.buildDetailsUrl;
import static com.example.vicinia.utilities.UrlUtilities.buildWelcomeUrl;

public class ChatMessageServices {
    private static final String TAG = "VICINITA/ChatServices";

    /**
     * called whenever connection to backend/welcome is required
     *
     * @called_from: {@link SplashActivity#onCreate(Bundle)}
     * @calls: {@link ChatMessageClient.ApiGetTask}
     */
    public static void getWelcome() {
        URL requestURL = buildWelcomeUrl();
        UrlUtilities.API_METHODS requestMethod = UrlUtilities.API_METHODS.GET_WELCOME;

        HttpRequest httpRequest = new HttpRequest(requestURL, requestMethod);

        new ChatMessageClient.ApiGetTask().execute(httpRequest);
    }

    /**
     * called whenever response from backend/welcome is received
     * returns uuid and welcome message
     *
     * @param response json response from backend/welcome
     * @called_from: {@link ChatMessageClient#onGetResponse(HttpResponse)}
     * @calls: {@link SplashActivity#onLoadingFinish(String, String)}
     */
    public static void onWelcomeResponse(JSONObject response) {
        try {
            String uuid = response.getString("uuid");
            String message = response.getString("message");

            Log.v(TAG, "UUID: " + uuid);

            SplashActivity splashActivity = SplashActivity.getInstance();
            splashActivity.onLoadingFinish(uuid, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * called whenever connection to backend/chat is required
     *
     * @called_from: {@link ChatMessageFragment#onChatButton(View)}
     * {@link QuickActionFragment#onQuickActionButton(QuickActionFragment.QUICK_ACTIONS)}
     * @calls: {@link ChatMessageClient.ApiGetTask}
     */
    public static void sendChatMessage(String message, double latitude, double longitude) {
        URL requestURL = buildChatUrl();
        UrlUtilities.API_METHODS requestMethod = UrlUtilities.API_METHODS.POST_CHAT;
        JSONObject requestBody = new JSONObject();

        if (latitude == 0 && longitude == 0)
            MainActivity.getInstance().onGpsError();
        else {
            try {
                requestBody.put("message", message);
                requestBody.put("latitude", Double.toString(latitude));
                requestBody.put("longitude", Double.toString(longitude));
            } catch (JSONException e) {
                Log.e(TAG, "JSONException", e);
                e.printStackTrace();
            }

            HttpRequest httpRequest = new HttpRequest(requestURL, requestMethod, requestBody);

            new ChatMessageClient.ApiPostTask().execute(httpRequest);
        }
    }

    /**
     * called whenever response from backend/chat is received
     * response message
     *
     * @param response json response from backend/chat
     * @called_from: {@link ChatMessageClient#onPostResponse(HttpResponse)}
     * @calls: {@link MainActivity#onReceiveMessage(String)}
     */
    public static void onChatResponse(JSONObject response) {
        try {
            MainActivity mainActivity = MainActivity.getInstance();
            String message = response.getString("message");
            mainActivity.onReceiveMessage(message);

            Log.v(TAG, "Message: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * called whenever connection to backend/placeDetails is required
     *
     * @param placeID   requested place ID
     * @param latitude  current latitude
     * @param longitude current longitude
     * @called_from: {@link ChatMessageFragment#onChatButton(View)}
     * {@link QuickActionFragment#onQuickActionButton(QuickActionFragment.QUICK_ACTIONS)}
     * @calls: {@link ChatMessageClient.ApiGetTask}
     */
    public static void getDetails(String placeID, double latitude, double longitude) {
        if (latitude == 0 && longitude == 0)
            MainActivity.getInstance().onGpsError();
        else {
            URL requestURL = buildDetailsUrl(placeID, latitude, longitude);
            UrlUtilities.API_METHODS requestMethod = UrlUtilities.API_METHODS.GET_DETAILS;

            HttpRequest httpRequest = new HttpRequest(requestURL, requestMethod);

            new ChatMessageClient.ApiGetTask().execute(httpRequest);
        }
    }

    /**
     * called whenever response from backend/placeDetails is received
     * response message
     *
     * @param response json response from backend/placeDetails
     * @called_from: {@link ChatMessageClient#onGetResponse(HttpResponse)}
     * @calls: {@link MainActivity#onReceiveMessage(String)}
     */
    public static void onDetailsResponse(JSONObject response) {
        try {
            MainActivity mainActivity = MainActivity.getInstance();

            String name = response.getString("name");
            String distance = response.getString("distance");
            String rating = response.getString("rating");
            String type = response.getString("type");
            String address = response.getString("address");
            String mobileNumber = response.getString("mobile_number");
            String link = response.getString("link");

            String message = "";
            message += "<b>Name: </b>" + name + "<br>";
            message += "<b>Distance: </b>" + distance + "<br>";
            message += "<b>Rating: </b>" + rating + "<br>";
            message += "<b>Type: </b>" + type + "<br>";
            message += "<b>Address: </b>" + address + "<br>";
            message += "<b>Mobile Number: </b>" + mobileNumber + "<br/><br/>";
            message += "<b><font color=\"#1C78C6\"><a href=\"" + link + "\">Open in Google Maps</a></font></b>";

            mainActivity.onReceiveMessage(message);
            Log.v(TAG, "Message: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
