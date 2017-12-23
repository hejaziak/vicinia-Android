package com.example.vicinia.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vicinia.MainActivity;
import com.example.vicinia.clients.ApiClient;
import com.example.vicinia.clients.ApiInterface;
import com.example.vicinia.pojos.Message;
import com.example.vicinia.services.ChatMessageServices;
import com.example.vicinia.utilities.DialogUtilities;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.vicinia.services.ChatMessageServices.getWelcome;
import static com.example.vicinia.utilities.ConnectivityUtilities.isConnectedToInternet;
import static com.example.vicinia.utilities.DialogUtilities.internetErrorDialogBuider;

public class SplashActivity extends Activity {
    private static final String TAG = "VICINIA/SplashActivity";

    //request identifier
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 33;

    static SplashActivity instance;

    private ApiInterface mClient;
    
    /**
     * callback function when activity is being created
     *
     * @param savedInstanceState information about state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        mClient = ApiClient.getClient();

        //check if permissions are granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if (isConnectedToInternet(this))
            getWelcome();
        else
            internetErrorDialogBuider(this);
    }

    public void getWelcome() {
        mClient.getWelcome().enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Message welcomeMessage = response.body();

                    String uuid = welcomeMessage.getUuid();
                    String message = welcomeMessage.getMessage();
                    onLoadingFinish(uuid, message);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    /**
     * called when /welcome response then starts the main activity
     *
     * @param uuid    uuid from /welcome response
     * @param message message from /welcome response
     *
     * @called_from: {@link ChatMessageServices#onWelcomeResponse(Message)}
     * @calls: {@link #onStop()} indirectly
     */
    public void onLoadingFinish(String uuid, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UUID", uuid);
        intent.putExtra("WELCOME_MESSAGE", message);
        startActivity(intent);
        finish();
    }

    /**
     * callback function when the user responds to permission request
     *
     * @param requestCode  the identifier we declared PERMISSION_ACCESS_COARSE_LOCATION
     * @param permissions  permission in question
     * @param grantResults the users response
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    /**
     * @return instance of {@link SplashActivity}
     *
     * @called_from: {@link ChatMessageServices#onWelcomeResponse(JSONObject)}
     * {@link DialogUtilities#internetErrorDialogBuider(Context)}
     * @calls: none
     */
    public static SplashActivity getInstance() {
        return instance;
    }
}