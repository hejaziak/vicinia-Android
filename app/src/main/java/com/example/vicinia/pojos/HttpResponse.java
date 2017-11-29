package com.example.vicinia.pojos;

import com.example.vicinia.utilities.UrlUtilities;

import org.json.JSONObject;

import java.net.URL;

public class HttpResponse extends  HttpRequest{
    private int statusCode;

    public HttpResponse(URL url, UrlUtilities.API_METHODS method, JSONObject jsonObject, int statusCode) {
        super(url, method, jsonObject);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
