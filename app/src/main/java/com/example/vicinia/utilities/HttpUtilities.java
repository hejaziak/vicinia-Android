package com.example.vicinia.utilities;

import android.util.Log;

import com.example.vicinia.MainActivity;
import com.example.vicinia.pojos.HttpRequest;
import com.example.vicinia.pojos.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpUtilities {
    private static final String TAG = "VICINITA/HttpUtilities";

    /**
     * opens a url connection, setting the Method type to GET
     *
     * @param request contains all details about the requested connection
     * @return HttpResponse resulted from connection
     * @called_from: {@link com.example.vicinia.clients.ChatMessageClient.ApiGetTask#doInBackground(HttpRequest...)}
     * @calls: {@link #readStream(InputStream)}
     */
    public static HttpResponse httpGET(HttpRequest request){
        //get all information about connection from HttpRequest object
        URL requestURL = request.getUrl();
        UrlUtilities.API_METHODS requestMethod = request.getMethod();

        HttpURLConnection urlConnection = null;

        try {
            //try to open connection. Throws IOException handled in catch block
            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //have to get an Instance from MainActivity to obtainUUID
            MainActivity mainActivity = MainActivity.getInstance();

            //GET_Welcome is where the UUID is set
            if(requestMethod != UrlUtilities.API_METHODS.GET_WELCOME){
                String uuid = mainActivity.getUuid();
                if(uuid != null)
                    urlConnection.setRequestProperty("Authorization",uuid);
            }

            //set request Method to GET
            urlConnection.setRequestMethod("GET");

            //read response from connection
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);

            //convert body to json
            JSONObject responseJSON = new JSONObject(response);

            //create httpResponse object to encapsulate all information
            HttpResponse httpResponse = new HttpResponse(requestURL, requestMethod, responseJSON, urlConnection.getResponseCode());

            urlConnection.disconnect();

            return httpResponse;
        } catch (IOException e){
            Log.e(TAG, "IOException", e);

            //if error occurred specify the error code
            return new HttpResponse(HttpURLConnection.HTTP_BAD_REQUEST);
        } catch (JSONException e){
            Log.e(TAG, "JSONException", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * opens a url connection, setting the Method type to POST
     * @param request contains all details about the requested connection
     * @return HttpResponse resulted from connection
     *
     * @called_from:    {@link com.example.vicinia.clients.ChatMessageClient.ApiPostTask#doInBackground(HttpRequest...)}
     * @calls:          {@link #readStream(InputStream)}
     *                  {@link #writeStream(OutputStream, String)}
     */
    public static HttpResponse httpPOST(HttpRequest request){
        //get all information about connection from HttpRequest object
        URL requestURL = request.getUrl();
        UrlUtilities.API_METHODS requestMethod = request.getMethod();
        JSONObject requestBody = request.getJsonObject();

        HttpURLConnection urlConnection = null;

        try {
            //try to open connection. Throws IOException handled in catch block
            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //have to get an Instance from MainActivity to obtainUUID
            MainActivity mainActivity = MainActivity.getInstance();
            String uuid = mainActivity.getUuid();
            if(uuid != null)
                urlConnection.setRequestProperty("Authorization",uuid);

            //set request Method to POST
            urlConnection.setRequestMethod("POST");

            //body length is known, Otherwise HttpURLConnection will be forced to buffer the
            // complete request body in memory before it is transmitted, increasing latency.
            urlConnection.setChunkedStreamingMode(0);

            //send body to connection
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, requestBody.toString());

            //read response from connection
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);

            //convert body to json
            JSONObject responseJSON = new JSONObject(response);

            //create httpResponse object to encapsulate all information
            HttpResponse httpResponse = new HttpResponse(requestURL, requestMethod, responseJSON, urlConnection.getResponseCode());

            urlConnection.disconnect();

            return httpResponse;
        } catch (IOException e){
            Log.e(TAG, "IOException", e);

            //if error occurred specify the error code
            return new HttpResponse(HttpURLConnection.HTTP_BAD_REQUEST);
        } catch (JSONException e){
            Log.e(TAG, "JSONException", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * helper to obtain data from connection
     * @param in the connection's inputSteam
     * @return the data received
     *
     * @called_from:    {@link #httpPOST(HttpRequest)}
     *                  {@link #httpGET(HttpRequest)}
     * @calls: none
     */
    private static String readStream(InputStream in) {
        //scanner is used here to handle internals for us
        //converts UTF-8 (http) to UTF-16 (android)
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    }

    /**
     * helper to send data to connection
     * @param out the connection's outputStream
     * @param data  the data to be send
     *
     * @called_from:    {@link #httpPOST(HttpRequest)}
     * @calls: none
     */
    private static void writeStream(OutputStream out, String data){
        try{
            DataOutputStream wr = new DataOutputStream(out);
            wr.writeBytes(data);
            out.flush();
        }
        catch (IOException e){
            Log.e(TAG, "IOException", e);
            e.printStackTrace();
        }
    }
}
