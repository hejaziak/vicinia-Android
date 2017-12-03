package com.example.vicinia.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.vicinia.MainActivity;

import static com.example.vicinia.services.ChatMessageServices.getWelcome;
import static com.example.vicinia.utilities.ConnectivityUtitlities.isConnectedToInternet;
import static com.example.vicinia.utilities.DialogUtilities.internetErrorDialogBuider;

public class SplashActivity extends Activity {
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 33;
    static SplashActivity instance;

    String uuid;
    String welcomeMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if(isConnectedToInternet(this))
            getWelcome();
        else
            internetErrorDialogBuider(this);
    }

    public void onLoadingFinish(String uuid, String message){
        this.uuid = uuid;
        this.welcomeMessage = message;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        if(isConnectedToInternet(this)){
            MainActivity mainActivity = MainActivity.getInstance();
            mainActivity.uuid = uuid;
            mainActivity.onReceiveMessage(welcomeMessage);
        }
        super.onStop();
    }

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

    public static SplashActivity getInstance() {
        return instance;
    }
}