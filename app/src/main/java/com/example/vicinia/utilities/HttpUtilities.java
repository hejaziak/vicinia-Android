package com.example.vicinia.utilities;

import android.util.Log;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class HttpUtilities {
    private static final String TAG = "VICINITA/HttpUtilities";

    public static String httpGET(URL requestURL){
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();

            urlConnection.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);

            return response;
        } catch (IOException e){
            Log.e(TAG, "IOException", e);
            e.printStackTrace();
            return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public static String httpPOST(URL requestURL, JSONObject requestBody){
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, requestBody.toString());

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);

            return response;
        } catch (IOException e){
            Log.e(TAG, "IOException", e);
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
            OutputStreamWriter outWriter = new OutputStreamWriter(out);
            outWriter.write(data);
            out.flush();
        }
        catch (IOException e){
            Log.e(TAG, "IOException", e);
            e.printStackTrace();
        }
    }
}
