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

    final static String CHATBOT_BASE_URL = "https://vicinia.herokuapp.com";

    final static String WELCOME_PATH = "welcome";
    final static String CHAT_PATH = "chat";
    final static String DETAILS_PATH = "details";

    /**
     * Builds the URL used to communicate with API @ /welcome
     *
     * @return The URL to use.
     */
    public static URL buildWelcomeUrl() {
        return buildUrl('w', null);
    }

    /**
     * Builds the URL used to communicate with API @ /chat
     *
     * @return The URL to use.
     */
    public static URL buildChatUrl() {
        return buildUrl('c', null);
    }

    /**
     * Builds the URL used to communicate with API @ /details/{placeID}
     *
     * @param placeID contains placeID if function is details.
     * @return The URL to use.
     */
    public static URL buildDetailsUrl(String placeID) {
        return buildUrl('d', placeID);
    }

    /**
     * Builds the URL used to communicate with API
     *
     * @param function char denoting function.
     * @param placeID contains placeID if function is details.
     * @return The URL to use.
     */
    public static URL buildUrl(char function, String placeID) {
        Uri.Builder uriBuilder = Uri.parse(CHATBOT_BASE_URL).buildUpon();

        switch (function) {
        case 'w':
            uriBuilder.appendPath(WELCOME_PATH);
            break;
        case 'c':
            uriBuilder.appendPath(CHAT_PATH);
            break;
        case 'd':
            uriBuilder.appendPath(DETAILS_PATH).appendPath(placeID);
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