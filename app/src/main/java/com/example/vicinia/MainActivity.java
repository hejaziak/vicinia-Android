package com.example.vicinia;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.fragments.QuickActionFragment;
import com.example.vicinia.services.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private static final String TAG = "GOOGLE API";

    static MainActivity instance;
    FragmentManager fragmentManager;

    public String uuid;

    public GPSTracker gps;

    private ChatMessageFragment fChatMessage;
    private ChatHistoryFragment fChatHistory;
    private QuickActionFragment fQuickAction;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        fragmentManager = getSupportFragmentManager();
        buildGoogleApiClient();


        fChatMessage = (ChatMessageFragment) fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = (ChatHistoryFragment) fragmentManager.findFragmentById(R.id.chat_history_fragment);
        fQuickAction = (QuickActionFragment) fragmentManager.findFragmentById(R.id.quick_actions_fragment);

        //hide keyboard at startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            fChatHistory.onSendMessage(String.valueOf(mLastLocation.getLatitude())+", "+String.valueOf(mLastLocation.getLongitude()));
        } else {
            Toast.makeText(this,"No location detected, Make sure that location is enabled in the device", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId){
            case R.id.btn_help:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSendMessage(String message) {
        fChatHistory.onSendMessage(message);
    }

    public void onReceiveMessage(String message) { fChatHistory.onReceiveMessage(message); }

    public void onChatButton(View v) { fChatMessage.onChatButton(v);  }

    public void onCinemaButton(View v) { fQuickAction.onCinemaButton(); }

    public void onGasStationButton(View v) { fQuickAction.onGasStationButton(); }

    public void onHospitalButton(View v) { fQuickAction.onHospitalButton(); }

    public void onRestaurantButton(View v) { fQuickAction.onRestaurantButton(); }

    public static MainActivity getInstance() { return instance; }
}
