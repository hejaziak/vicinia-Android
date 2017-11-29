package com.example.vicinia.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.vicinia.MainActivity;

import static com.example.vicinia.services.ChatMessageServices.getWelcome;

public class SplashActivity extends Activity {
    static SplashActivity instance;

    String uuid;
    String welcomeMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        getWelcome();
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
        MainActivity mainActivity = MainActivity.getInstance();
        mainActivity.uuid = uuid;
        mainActivity.onReceiveMessage(welcomeMessage);

        super.onStop();
    }

    public static SplashActivity getInstance() {
        return instance;
    }
}