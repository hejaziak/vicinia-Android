package com.example.vicinia.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("message")
    @Expose
    private String message;

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
        super();
        this.uuid = uuid;
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }
}