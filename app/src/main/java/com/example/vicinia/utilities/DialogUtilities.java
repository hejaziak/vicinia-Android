package com.example.vicinia.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.activities.SplashActivity;

/**
 * home of the Dialog builders
 */
public class DialogUtilities {
    private static final String TAG = "VICINITA/DialogUtilities";


    /**
     * used to build Help dialog when the help button is pressed
     *
     * @param context is a reference to the calling activity, used when creating the dialog
     * @called_from: {@link MainActivity#onOptionsItemSelected(MenuItem)}
     * @calls: none
     */
    public static void helpDialogBuilder(Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Hello!");
        alertDialog.setMessage("I am Vicinia , I am here to help you to find any nearby place you " +
                "are looking for! Just tell me where you want to go \uD83D\uDE0A.");
        alertDialog.setIcon(R.mipmap.vicinia);

        // just dismiss the dialog
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    /**
     * used to build error dialog when there is no internet
     *
     * @param activity is a reference to the calling activity, used when creating the dialog
     * @called_from: {@link MainActivity#onInternetError()}
     * {@link SplashActivity#onCreate(Bundle)}
     * {@link com.example.vicinia.clients.ChatMessageClient#onGetResponse(HttpResponse)}
     * {@link com.example.vicinia.clients.ChatMessageClient#onPostResponse(HttpResponse)}
     * @calls: none
     */
    public static void internetErrorDialogBuider(final Activity activity){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Check your internet connectivity");
        alertDialog.setMessage("I couldn't connect to the internet 😔, please make sure there's an active connection");
        alertDialog.setIcon(R.drawable.ic_warning_grey_24dp);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {activity.finish();
                    }
                });
        alertDialog.show();
    }

    /**
     * used to build error dialog when there is a gps error
     * @param context is a reference to the calling activity, used when creating the dialog
     *
     * @called_from:    {@link MainActivity#onGpsError()} ()}
     * @calls: none
     */
    public static void gpsErrorBuilder(Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Couldn't find your location");
        alertDialog.setMessage("Check your that your location is enabled");
        alertDialog.setIcon(R.drawable.ic_warning_grey_24dp);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
