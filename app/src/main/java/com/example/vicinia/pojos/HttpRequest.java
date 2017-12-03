package com.example.vicinia.pojos;

import com.example.vicinia.utilities.UrlUtilities;

import org.json.JSONObject;

import java.net.URL;

public class HttpRequest {
    private URL url;
    private UrlUtilities.API_METHODS method;
    private JSONObject jsonObject;

    public HttpRequest() {

    }

    public HttpRequest(URL url, UrlUtilities.API_METHODS method) {
        this.url = url;
        this.method = method;
    }

    public HttpRequest(URL url, UrlUtilities.API_METHODS method, JSONObject jsonObject) {
        this(url, method);
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public UrlUtilities.API_METHODS getMethod() {
        return method;
    }

    public void setMethod(UrlUtilities.API_METHODS method) {
        this.method = method;
    }
}
