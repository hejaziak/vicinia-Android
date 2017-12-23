package com.example.vicinia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("rating")
    @Expose
    private float rating;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;

    @SerializedName("link")
    @Expose
    private String link;

    /**
     * No args constructor for use in serialization
     *
     */
    public Place() {
    }

    /**
     *
     * @param id            id of place
     * @param name          name
     * @param distance      distance from user
     * @param address       address
     * @param link          google maps link
     * @param rating        rating
     * @param mobileNumber  mobile number
     * @param type          (cinema, restaurant, etc...)
     */
    public Place(String name, String distance, String id, float rating, String type, String address, String mobileNumber, String link) {
        this.name = name;
        this.distance = distance;
        this.id = id;
        this.rating = rating;
        this.type = type;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getLink() {
        return link;
    }
}