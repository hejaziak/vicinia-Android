package com.example.vicinia.pojos;

import com.example.vicinia.clients.ChatMessageClient;
import com.example.vicinia.utilities.HttpUtilities;
import com.example.vicinia.utilities.UrlUtilities;

import org.json.JSONObject;

import java.net.URL;

/**
 * used to model the Http request send to the Async task since it only allows only one argument to
 * be passed
 */
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

    /**
     * getter for json
     *
     * @return returns content
     * @called_from: {@link ChatMessageClient#onGetResponse(HttpResponse)}
     * {@link ChatMessageClient#onPostResponse(HttpResponse)}
     * {@link HttpUtilities#httpPOST(HttpRequest)}
     * @calls: none
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * getter for url
     *
     * @return returns content
     * @called_from: {@link HttpUtilities#httpPOST(HttpRequest)}
     * {@link HttpUtilities#httpGET(HttpRequest)}
     * @calls: none
     */
    public URL getUrl() {
        return url;
    }

    /**
     * getter for method
     *
     * @return returns content
     * @called_from: {@link ChatMessageClient#onGetResponse(HttpResponse)}
     * {@link ChatMessageClient#onPostResponse(HttpResponse)}
     * {@link HttpUtilities#httpPOST(HttpRequest)}
     * {@link HttpUtilities#httpGET(HttpRequest)}
     * @calls: none
     */
    public UrlUtilities.API_METHODS getMethod() {
        return method;
    }

}
