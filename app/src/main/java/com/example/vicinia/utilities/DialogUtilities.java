package com.example.vicinia.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.activities.SplashActivity;

public class DialogUtilities {
    public static void helpDialogBuilder(Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Hello!");
        alertDialog.setMessage("I am Vicinia , I will help you to find your desired nearby places. Waiting for you messages !");
        alertDialog.setIcon(R.mipmap.vicinia);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void internetErrorDialogBuider(Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Check your internet connectivity");
        alertDialog.setMessage("I couldn't connect to the internet, please make sure there's an active connection");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            MainActivity.getInstance().finish();
                        }
                        catch (NullPointerException e){
                            SplashActivity.getInstance().finish();
                        }
                    }
                });
        alertDialog.show();
    }

    public static void gpsErrorBuilder(Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Hello!");
        alertDialog.setMessage("I am Vicinia , I will help you to find your desired nearby places. Waiting for you messages !");
        alertDialog.setIcon(R.mipmap.vicinia);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
