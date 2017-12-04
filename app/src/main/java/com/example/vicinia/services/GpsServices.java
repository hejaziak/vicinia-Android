package com.example.vicinia.services;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vicinia.MainActivity;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.fragments.QuickActionFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * GpsServices: the service responsible to obtaining the device's location
 */
public class GpsServices implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "VICINITA/GpsServices";

    //request location every 1 minute
    private static final int LONGEST_INTERVAL = 60 * 1000;
    //obtain location if any other application requests the location in 10s
    private static final int FASTEST_INTERVAL = 10 * 1000;

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;
    private double latitude;
    private double longitude;

    /**
     * the GpsServices constructor. builds the GoogleApiClient
     *
     * @param context is a reference to the calling activity, used when creating the dialog
     * @called_from: {@link MainActivity#onCreate(Bundle)}
     * @calls: {@link GoogleApiClient.Builder}
     */
    public GpsServices(Context context) {
        mContext = context;

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * connects the client created in {@link #GpsServices(Context)}
     *
     * @called_from: {@link MainActivity#onStart()}
     * @calls: none
     */
    public void onStart() {
        mGoogleApiClient.connect();
    }

    /**
     * disconnects the client if connected
     *
     * @called_from: {@link MainActivity#onStop()}
     * @calls: none
     */
    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * callback function when API is connected
     *
     * @param connectionHint bundle/map containing information about the connection
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest locationRequest = createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        getLastLocation();
    }

    /**
     * sets intervals for the location request
     *
     * @return location request object
     * @called_from: {@link #onConnected(Bundle)} (Location)}
     * @calls: none
     */
    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LONGEST_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }

    /**
     * callback function when API can't connect
     *
     * @param result object containing information about the connection
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    /**
     * callback function when API is suspended
     *
     * @param cause error code
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * callback function whenever the location changes
     *
     * @param location updated location
     */
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        getLastLocation();
    }

    /**
     * updates cached location
     *
     * @return new location
     * @called_from: {@link #onLocationChanged(Location)}
     * @calls: none
     */
    public Location getLastLocation() {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            this.mLastLocation = mLastLocation;

            longitude = mLastLocation.getLongitude();
            latitude = mLastLocation.getLatitude();

            Log.v(TAG, "LOCATION " + latitude + ", " + longitude);
        } else {
            Log.v(TAG, "No location detected, Make sure that location is enabled in the device");
        }

        return this.mLastLocation;
    }

    /**
     * getter for longitude
     *
     * @return cached longitude
     * @called_from: {@link QuickActionFragment#onQuickActionButton(QuickActionFragment.QUICK_ACTIONS)}
     * {@link MainActivity#onGetDetailsButton(String)}
     * {@link ChatMessageFragment#onChatButton(View)}
     * @calls: none
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * getter for latitude
     *
     * @return cached latitude
     * @called_from: {@link QuickActionFragment#onQuickActionButton(QuickActionFragment.QUICK_ACTIONS)}
     * {@link MainActivity#onGetDetailsButton(String)}
     * {@link ChatMessageFragment#onChatButton(View)}
     * @calls: none
     */
    public double getLatitude() {
        return latitude;
    }

    public boolean isGpsConnected() {
        return latitude == 0 && longitude == 0;
    }
}
