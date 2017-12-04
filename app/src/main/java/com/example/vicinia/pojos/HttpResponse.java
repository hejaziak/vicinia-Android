package com.example.vicinia.pojos;

import com.example.vicinia.clients.ChatMessageClient;
import com.example.vicinia.utilities.UrlUtilities;

import org.json.JSONObject;

import java.net.URL;

public class HttpResponse extends HttpRequest {
    private int statusCode;

    public HttpResponse(URL url, UrlUtilities.API_METHODS method, JSONObject jsonObject, int statusCode) {
        super(url, method, jsonObject);
        this.statusCode = statusCode;
    }

    public HttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * getter for status code
     *
     * @return returns content
     * @called_from: {@link ChatMessageClient#onGetResponse(HttpResponse)}
     * {@link ChatMessageClient#onPostResponse(HttpResponse)}
     * @calls: none
     */
    public int getStatusCode() {
        return statusCode;
    }
}
