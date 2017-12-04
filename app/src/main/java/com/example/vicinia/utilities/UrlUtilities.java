package com.example.vicinia.utilities;

import android.net.Uri;

import com.example.vicinia.services.ChatMessageServices;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * These utilities will be used to communicate with the Backend.
 */
public class UrlUtilities {
    private static final String TAG = "VICINITA/UrlUtilities";

    //enum specifying all routs + methods
    public enum API_METHODS {GET_WELCOME, GET_DETAILS, POST_CHAT}

    //deployed backend details
    private final static String BACKEND_BASE_URL = "https://vicinia.herokuapp.com";
    //localhost details
    private final static String DEBUG_URL = "192.168.1.2";
    private final static String DEBUG_PORT = "8080";
    private final static boolean DEBUG_MODE = false;
    //routes to consider
    private final static String WELCOME_PATH = "welcome";
    private final static String CHAT_PATH = "chat";
    private final static String DETAILS_PATH = "placeDetails";
    //query params for placeDetails url
    private final static String ID_PARAM = "place_id";
    private final static String LAT_PARAM = "latitude";
    private final static String LNG_PARAM = "longitude";

    /**
     * Builds the URL used to communicate with API @ /welcome
     * @return The URL to use.
     *
     * @called_from:   {@link ChatMessageServices#getWelcome()}
     * @calls          {@link #buildUrl(API_METHODS, String[])}
     */
    public static URL buildWelcomeUrl() {
        return buildUrl(API_METHODS.GET_WELCOME, null);
    }

    /**
     * Builds the URL used to communicate with API @ /chat
     * @return The URL to use.
     *
     * @called_from:   {@link ChatMessageServices#sendChatMessage(String, double, double)}
     * @calls          {@link #buildUrl(API_METHODS, String[])}
     */
    public static URL buildChatUrl() {
        return buildUrl(API_METHODS.POST_CHAT, null);
    }

    /**
     * Builds the URL used to communicate with API @ /placeDetails?placed_id= &latitude= &longitude =
     *
     * @param placeID contains placeID if function is details.
     * @return The URL to use.
     *
     * @called_from:   {@link ChatMessageServices#getDetails(String, double, double)}
     * @calls          {@link #buildUrl(API_METHODS, String[])}
     */
    public static URL buildDetailsUrl(String placeID, double lat, double lng) {
        String[] queryParams = {placeID, Double.toString(lat), Double.toString(lng)};
        return buildUrl(API_METHODS.GET_DETAILS, queryParams);
    }

    /**
     * handles the actual url building
     * Builds the URL used to communicate with API
     * @param function enum{@link API_METHODS} denoting function.
     * @param queryParams contains a list of query parameters
     * @return The URL to use.
     *
     * @called_from:    {@link #buildWelcomeUrl()}
     *                  {@link #buildUrl(API_METHODS, String[])}
     *                  {@link #buildChatUrl()}
     * @calls none
     */
    private static URL buildUrl(API_METHODS function, String[] queryParams) {
        Uri.Builder uriBuilder = Uri.parse(BACKEND_BASE_URL).buildUpon();

        //used to connect to local backend in case of debugging
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
}