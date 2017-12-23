package com.example.vicinia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("list")
    @Expose
    private Place[] list;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    /**
     * No args constructor for use in serialization
     *
     */
    public Message() {
    }

    /**
     *
     * @param message   message from/to server
     * @param uuid      user's unique ID
     */
    public Message(String uuid, String message) {
        this.uuid = uuid;
        this.message = message;
    }

    /**
     *
     * @param message   message from/to server
     */
    public Message(String latitude, String longitude, String message) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }

    public Place[] getList() {
        return list;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}