package com.example.vicinia.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the Chatbot.
 */
public class UrlUtilities {
    public static enum API_METHODS {GET_WELCOME, GET_DETAILS, POST_CHAT}

    final static String CHATBOT_BASE_URL = "https://vicinia.herokuapp.com";

    final static String DEBUG_URL = "192.168.1.2";
    final static String DEBUG_PORT = "8080";
    final static boolean DEBUG_MODE = true;

    final static String WELCOME_PATH = "welcome";
    final static String CHAT_PATH = "chat";
    final static String DETAILS_PATH = "details";

    final static String ID_PARAM = "id";
    final static String LAT_PARAM = "lat";
    final static String LNG_PARAM = "lng";

    /**
     * Builds the URL used to communicate with API @ /welcome
     *
     * @return The URL to use.
     */
    public static URL buildWelcomeUrl() {
        return buildUrl(API_METHODS.GET_WELCOME, null);
    }

    /**
     * Builds the URL used to communicate with API @ /chat
     *
     * @return The URL to use.
     */
    public static URL buildChatUrl() {
        return buildUrl(API_METHODS.POST_CHAT, null);
    }

    /**
     * Builds the URL used to communicate with API @ /details/{placeID}
     *
     * @param placeID contains placeID if function is details.
     * @return The URL to use.
     */
    public static URL buildDetailsUrl(String placeID, double lat, double lng) {
        String[] queryParams = {placeID, Double.toString(lat), Double.toString(lng)};
        return buildUrl(API_METHODS.GET_DETAILS, queryParams);
    }

    /**
     * Builds the URL used to communicate with API
     *
     * @param function char denoting function.
     * @param queryParams contains a list of query parameters
     * @return The URL to use.
     */
    public static URL buildUrl(API_METHODS function, String[] queryParams) {
        Uri.Builder uriBuilder = Uri.parse(CHATBOT_BASE_URL).buildUpon();

        if (DEBUG_MODE) {
            uriBuilder.encodedAuthority(DEBUG_URL + ":" + DEBUG_PORT);
            uriBuilder.scheme("http");
        }

        switch (function) {
            case GET_WELCOME:
            uriBuilder.appendPath(WELCOME_PATH);
            break;
            case POST_CHAT:
            uriBuilder.appendPath(CHAT_PATH);
            break;
            case GET_DETAILS:
                String placeID = queryParams[0];
                String lat = queryParams[1];
                String lng = queryParams[2];

                uriBuilder.appendPath(DETAILS_PATH)
                        .appendQueryParameter(ID_PARAM, placeID)
                        .appendQueryParameter(LAT_PARAM, lat)
                        .appendQueryParameter(LNG_PARAM, lng);
            break;
        default:
            return null;
        }

        Uri builtUri = uriBuilder.build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}