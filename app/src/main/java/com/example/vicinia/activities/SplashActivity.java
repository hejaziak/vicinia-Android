package com.example.vicinia.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.vicinia.MainActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}