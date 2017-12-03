package com.example.vicinia.utilities;

import android.util.Log;

import com.example.vicinia.MainActivity;
import com.example.vicinia.pojos.HttpRequest;
import com.example.vicinia.pojos.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class HttpUtilities {
    private static final String TAG = "VICINITA/HttpUtilities";

    public static HttpResponse httpGET(HttpRequest request){
        URL requestURL = request.getUrl();
        UrlUtilities.API_METHODS requestMethod = request.getMethod();

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();

            MainActivity mainActivity = MainActivity.getInstance();

            if(requestMethod != UrlUtilities.API_METHODS.GET_WELCOME){
                String uuid = mainActivity.uuid;
                if(uuid != null)
                    urlConnection.setRequestProperty("Authorization",uuid);
            }

            urlConnection.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);

            JSONObject responseJSON = new JSONObject(response);
            HttpResponse httpResponse = new HttpResponse(requestURL, requestMethod, responseJSON, urlConnection.getResponseCode());

            return httpResponse;
        } catch (IOException e){
            Log.e(TAG, "IOException", e);
            return new HttpResponse(400);
        } catch (JSONException e){
            Log.e(TAG, "JSONException", e);
            e.printStackTrace();
            return null;
        }

        finally {
            urlConnection.disconnect();
        }
    }

    public static HttpResponse httpPOST(HttpRequest request){
        URL requestURL = request.getUrl();
        UrlUtilities.API_METHODS requestMethod = request.getMethod();
        JSONObject requestBody = request.getJsonObject();

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();

            MainActivity mainActivity = MainActivity.getInstance();
            String uuid = mainActivity.uuid;
            if(uuid != null)
                urlConnection.setRequestProperty("Authorization",uuid);

            urlConnection.setRequestMethod("POST");
            urlConnection.setChunkedStreamingMode(0); //because you don't know the response size

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, requestBody.toString());

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);

            JSONObject responseJSON = new JSONObject(response);
            HttpResponse httpResponse = new HttpResponse(requestURL, requestMethod, responseJSON, urlConnection.getResponseCode());

            return httpResponse;
        } catch (IOException e){
            Log.e(TAG, "IOException", e);
            return new HttpResponse(400);
        } catch (JSONException e){
            Log.e(TAG, "JSONException", e);
            e.printStackTrace();
            return null;
        }

        finally {
            urlConnection.disconnect();
        }
    }


    // https://stackoverflow.com/questions/8376072/whats-the-readstream-method-i-just-can-not-find-it-anywhere
    private static String readStream(InputStream in) {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    }

    // https://stackoverflow.com/questions/31611480/how-do-i-post-in-java-to-a-server
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
